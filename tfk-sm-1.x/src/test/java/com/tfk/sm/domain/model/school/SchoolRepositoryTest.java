package com.tfk.sm.domain.model.school;

import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.SchoolScope;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Liguiqing
 * @since V3.0
 */
@ContextHierarchy({
    @ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-sm-app.xml",
        "classpath:applicationContext-test-cache.xml",
        "classpath:applicationContext-sm-test-ds.xml",
        "classpath:applicationContext-test-jndi.xml",
        "classpath:applicationContext-sm-test-data.xml"}
)})
@Transactional
@Rollback
public class SchoolRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CacheMock cacheMock;

    @Test
    public void testInit(){
        assertNotNull(schoolRepository);
        assertNotNull(cacheMock);
        SchoolId schoolId = schoolRepository.nextIdentity();
        cacheMock.getValue(schoolId);
        cacheMock.getValue(schoolId);
        cacheMock.getValue(schoolId);
    }

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
    }
}