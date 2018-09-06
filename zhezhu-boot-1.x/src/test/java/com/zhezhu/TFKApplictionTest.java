package com.zhezhu;

/**
 * @author Liguiqing
 * @since V3.0
 */

import com.zhezhu.commons.lang.Throwables;
import com.zhezhu.zhezhu.repository.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

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
            log.error(Throwables.toString(ex));
        }catch (SQLException ex){
            log.error(Throwables.toString(ex));
        }
    }

    @Test
    public void contextLoads() throws Exception {
    }

}