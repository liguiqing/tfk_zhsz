package com.zhezhu.sm.domain.model.school;

import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.sm.SmTestConfiguration;
import com.zhezhu.sm.config.SmApplicationConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

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
public class SchoolRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void findSchoolByNameEquals(){
        School school = schoolRepository.findSchoolByNameEquals("Test");
        assertNull(school);
    }

    @Test
    public void loadOf(){
        SchoolId schoolId = schoolRepository.nextIdentity();
        School school = new School(schoolId, "Test School", SchoolScope.Primary);
        schoolRepository.save(school);
        School school_ = schoolRepository.loadOf(schoolId);
        assertNotNull(school_);
        school_ = schoolRepository.loadOf(schoolId);
        school_ = schoolRepository.loadOf(schoolId);
        school_ = schoolRepository.loadOf(new SchoolId());
        school_ = schoolRepository.loadOf(new SchoolId());
    }

    @Test
    public void delete(){
        School school = new School(new SchoolId("SCH123456781"), "Test School", SchoolScope.Primary);
        schoolRepository.save(school);
        School school2 = new School(new SchoolId("SCH123456782"), "Test School", SchoolScope.Primary);
        schoolRepository.save(school2);
        schoolRepository.delete(new SchoolId("SCH123456781").id());
        School school1 = schoolRepository.loadOf(new SchoolId("SCH123456781"));
        School school3 = schoolRepository.loadOf(new SchoolId("SCH123456782"));
        assertNull(school1);
        assertNotNull(school3);
        school3 = schoolRepository.loadOf(new SchoolId("SCH123456782"));
        new SchoolId("SCH123456781").getId();
    }

    @Test
    public void save(){
        School school = new School(new SchoolId("SCH123456781"), "Test School", SchoolScope.Primary);
        schoolRepository.save(school);
        for(int i=0;i<100;i++)
            schoolRepository.save(new School(schoolRepository.nextIdentity(), "Test School "+i, SchoolScope.Middle));

        List<School> schools = schoolRepository.findByLimit(1, 100);
        assertEquals(100, schools.size());
        assertEquals(school,schools.get(0));
    }
}