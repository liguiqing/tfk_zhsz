package com.zhezhu.access;

import com.zhezhu.access.config.AccessApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertNotNull;


/**
 * @author Liguiqing
 * @since V3.0
 */

@ContextHierarchy({
        @ContextConfiguration(
                classes = {AccessTestConfiguration.class,CommonsConfiguration.class,AccessApplicationConfiguration.class}
        )
})

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
