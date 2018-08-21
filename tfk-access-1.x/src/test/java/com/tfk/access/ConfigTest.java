package com.tfk.access;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertNotNull;


/**
 * @author Liguiqing
 * @since V3.0
 */


@ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-access-app.xml",
        "classpath:applicationContext-access-test-app.xml",
        "classpath:applicationContext-test-jndi.xml",
        "classpath:applicationContext-access-test-data.xml"}
)
@Transactional
@Rollback
public class ConfigTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test()throws Exception{
        assertNotNull(jdbcTemplate);
        jdbcTemplate.query("select 1 as r from dual where 1=?", (rs,rowNum)->rs.getInt("r"),1);
    }
}
