package com.tfk.sm.domain.model.clazz;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        "classpath:applicationContext-sm-test-ds.xml",
        "classpath:applicationContext-test-jndi.xml",
        "classpath:applicationContext-sm-test-data.xml"}
)
@Transactional
@Rollback
public class ClazzRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired()
    @Qualifier("UnitedClazzRepository")
    private ClazzRepository clazzRepository1;

    @Test
    public void save(){
        assertNotNull(clazzRepository1);

        SchoolId schoolId = new SchoolId("12345678");
        ClazzId clazzId = clazzRepository1.nextIdentity();
        UnitedClazz clazz = new UnitedClazz(clazzId,schoolId,DateUtilWrapper.now());
        clazzRepository1.save(clazz);
        Grade grade = Grade.G1();
        StudyYear studyYear = StudyYear.now();
        ClazzHistory history = new ClazzHistory(clazzId,grade,studyYear,"一班");
        clazz.addHistory(history);
        clazzRepository1.save(clazz);
    }

    @Test
    public void loadOf(){
        assertNotNull(clazzRepository1);
        Clazz clazz = clazzRepository1.loadOf(new ClazzId());
        assertNull(clazz);
    }
}