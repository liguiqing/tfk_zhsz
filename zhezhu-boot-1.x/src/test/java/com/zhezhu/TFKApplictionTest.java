package com.zhezhu;

/**
 * @author Liguiqing
 * @since V3.0
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhezhu.commons.lang.Throwables;
import com.zhezhu.zhezhu.repository.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.util.ThreadState;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.text.normalizer.ICUBinary;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TFKAppliction.class)
public class TFKApplictionTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private static ThreadState subjectThreadState;

    private Subject subject;

    private MockHttpSession mockSession;

    private String sessionId = UUID.randomUUID().toString();

    @BeforeClass
    public static void beforeClass(){
        newJndiDataSource();
    }

    @AfterClass
    public static void afterClazz(){
        doClearSubject();
        SecurityManager securityManager = getSecurityManager();
        LifecycleUtils.destroy(securityManager);
        setSecurityManager(null);
    }

    private static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager);
    }

    private static void doClearSubject() {
        if (subjectThreadState != null) {
            subjectThreadState.clear();
            subjectThreadState = null;
        }
    }

    private void setSubject(Subject subject) {
        clearSubject();
        subjectThreadState = createThreadState(subject);
        subjectThreadState.bind();
    }

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    private void clearSubject() {
        doClearSubject();
    }

    private ThreadState createThreadState(Subject subject) {
        return new SubjectThreadState(subject);
    }

    private static SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

    @Before
    public void before() throws Exception{
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        SecurityManager securityManger = mock(SecurityManager.class);
        Subject mockSubject = mock(Subject.class);
        Session session = mock(Session.class);
        when(mockSubject.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(sessionId);
        when(securityManger.createSubject(any(SubjectContext.class))).thenReturn(mockSubject);
        ThreadContext.bind(securityManger);

        subject =   new Subject.Builder(getSecurityManager()).buildSubject();//Mockito.mock(Subject.class);
        AuthenticationToken token = mock(AuthenticationToken.class);
        subject.login(token);
        ShiroFilterFactoryBean bean;
        when(subject.isAuthenticated()).thenReturn(true).thenReturn(false);
        mockSession = new MockHttpSession(wac.getServletContext(),
                subject.getSession().getId().toString());
        setSubject(subject);
    }

    private String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }

    private static void newJndiDataSource() {
        try {
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setUrl("jdbc:mysql://192.168.1.251:3306/easytnt_sm_testing?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true&amp;rewriteBatchedStatements=true");
            dataSourceProperty.setUsername("root");
            dataSourceProperty.setPassword("newa_newc");
            DataSource dataSource = dataSourceProperty.getDataSource();
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.bind("java:comp/env/jdbc/testJndiDs", dataSource);
            builder.activate();
        } catch (NamingException ex) {
            log.error(Throwables.toString(ex));
        }catch (SQLException ex){
            log.error(Throwables.toString(ex));
        }
    }

    @Test
    public void contextLoads() throws Exception {
        testMvc();
    }

    public void testMvc()throws Exception{

        this.mvc.perform(get("/index").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("/index"))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));

        this.mvc.perform(get("/authorized").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("/index"))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }
}