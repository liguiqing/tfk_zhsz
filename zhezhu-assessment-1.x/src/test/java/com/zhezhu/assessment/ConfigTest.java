package com.zhezhu.assessment;

import com.zhezhu.assessment.config.AssessmentApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static junit.framework.TestCase.assertNotNull;


/**
 * @author Liguiqing
 * @since V3.0
 */


@ContextConfiguration(
    classes = {
            AssessmentTestConfiguration.class,
            CommonsConfiguration.class,
            AssessmentApplicationConfiguration.class
    }
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
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).weekOfYear();
        System.out.println(now.minusWeeks(1).get(fieldISO));
        System.out.println(now.getMonth().minus(1).getValue());
        System.out.println(now.minusDays(1).toString());
    }
}