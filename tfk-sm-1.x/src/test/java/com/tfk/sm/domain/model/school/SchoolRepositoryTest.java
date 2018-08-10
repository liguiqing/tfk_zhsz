package com.tfk.sm.domain.model.school;

import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.SchoolScope;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Liguiqing
 * @since V3.0
 */
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-sm-app.xml",
        "classpath:applicationContext-test-cache.xml",
        "classpath:applicationContext-sm-test-ds.xml",
        "classpath:applicationContext-test-jndi.xml",
        "classpath:applicationContext-sm-test-data.xml"}
)
@Transactional
@Rollback
public class SchoolRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void testInit(){
        assertNotNull(schoolRepository);
    }

    @Test
    public void findSchoolByNameEquals(){
        School school = schoolRepository.findSchoolByNameEquals("Test");
        assertNull(school);
    }

    @Test
    public void loadOf(){
        School school = schoolRepository.loadOf(new SchoolId());
        assertNull(school);
    }

    @Test
    public void delete(){
        schoolRepository.delete(new SchoolId().id());
    }

    @Test
    public void save(){
        School school = new School(new SchoolId("SCH123456781"), "Test School", SchoolScope.Primary);
        schoolRepository.save(school);
    }
}