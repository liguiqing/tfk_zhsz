package com.tfk.sm;

import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.SchoolScope;
import com.tfk.sm.application.clazz.ClazzApplicationService;
import com.tfk.sm.application.school.SchoolApplicationService;
import com.tfk.sm.application.student.StudentApplicationService;
import com.tfk.sm.application.teacher.TeacherApplicationService;
import com.tfk.sm.config.SmApplicationConfiguration;
import com.tfk.sm.domain.model.clazz.*;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * @author Liguiqing
 * @since V3.0
 */
@ContextConfiguration(
        classes = {
                SmTestConfiguration.class,
                CommonsConfiguration.class,
                SmApplicationConfiguration.class
        }
)
@Transactional
@Rollback
public class ConfigTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private LearningClazzRepository learningClazzRepository;

    @Autowired
    private SuperviseClazzRepository superviseClazzRepository;

    @Autowired
    private UnitedClazzRepository unitedClazzRepository;

    @Autowired
    private SchoolApplicationService achoolApplicationService;

    @Autowired
    private ClazzApplicationService clazzApplicationService;

    @Autowired
    private TeacherApplicationService teacherApplicationService;

    @Autowired
    private StudentApplicationService studentApplicationService;


    @Test
    public void test()throws Exception{
        assertNotNull(jdbcTemplate);
        jdbcTemplate.query("select 1 as r from dual where 1=?", (rs,rowNum)->rs.getInt("r"),1);
        assertNotNull(schoolRepository);
        assertNotNull(schoolRepository.nextIdentity());
        School school = new School(new SchoolId("12345678"), "name", "alias", SchoolScope.Middle);
        schoolRepository.save(school);
        School school2 = schoolRepository.loadOf(new SchoolId("12345678"));
        assertTrue(school.equals(school2));
        schoolRepository.delete("12345678");

        assertNotNull(learningClazzRepository);
        Date openTime = DateUtilWrapper.now();
        ClazzId clazzId1 = learningClazzRepository.nextIdentity();
        LearningClazz clazz1 = new LearningClazz(clazzId1,school.schoolId(),openTime);
        learningClazzRepository.save(clazz1);
        LearningClazz clazz1_ = learningClazzRepository.loadOf(clazzId1);
        assertNotNull(clazz1_);
        assertTrue(clazz1.equals(clazz1_));

        assertNotNull(superviseClazzRepository);
        ClazzId clazzId2 = superviseClazzRepository.nextIdentity();
        SuperviseClazz clazz2 = new SuperviseClazz(clazzId2,school.schoolId(),openTime);
        superviseClazzRepository.save(clazz2);
        SuperviseClazz clazz2_ = superviseClazzRepository.loadOf(clazzId2);
        assertNotNull(clazz2_);
        assertTrue(clazz2.equals(clazz2_));
        superviseClazzRepository.delete(clazzId2.id());

        assertNotNull(unitedClazzRepository);
        ClazzId clazzId3 = unitedClazzRepository.nextIdentity();
        UnitedClazz clazz3 = new UnitedClazz(clazzId2,school.schoolId(),openTime);
        unitedClazzRepository.save(clazz3);
        UnitedClazz clazz3_ = unitedClazzRepository.loadOf(clazzId2);
        assertNotNull(clazz3_);
        assertTrue(clazz3.equals(clazz3_));
        unitedClazzRepository.delete(clazzId3.id());


        assertNotNull(achoolApplicationService);
        assertNotNull(clazzApplicationService);
        assertNotNull(teacherApplicationService);
        assertNotNull(studentApplicationService);
    }
}