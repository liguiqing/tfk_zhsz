package com.zhezhu;

/**
 * @author Liguiqing
 * @since V3.0
 */

import com.zhezhu.commons.spring.SpringContextUtil;
import com.zhezhu.zhezhu.repository.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TFKAppliction.class)
public class TFKApplictionTest {

    @BeforeClass
    public static void before(){
        newJndiDataSource();
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
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(SpringContextUtil.getBean("dataSource"));
        Context context = new InitialContext();
        context.list("java:comp/env/jdbc/testJndiDs");
        Map attr = context.getEnvironment();
        log.debug("{}",attr);
    }

}