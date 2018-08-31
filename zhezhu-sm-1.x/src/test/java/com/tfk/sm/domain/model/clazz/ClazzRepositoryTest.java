package com.tfk.sm.domain.model.clazz;

import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import com.tfk.sm.SmTestConfiguration;
import com.tfk.sm.config.SmApplicationConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class ClazzRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired()
    @Qualifier("UnitedClazzRepository")
    private ClazzRepository clazzRepository1;

    @Test
    public void test(){
        assertNotNull(clazzRepository1);

        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = clazzRepository1.nextIdentity();
        UnitedClazz clazz = new UnitedClazz(clazzId,schoolId,DateUtilWrapper.now());
        clazzRepository1.save(clazz);
        Grade grade = Grade.G1();
        StudyYear studyYear = StudyYear.now();
        ClazzHistory history = new ClazzHistory(clazzId,grade,"一班");
        clazz.addHistory(history);
        clazzRepository1.save(clazz);

        Clazz clazz1 = clazzRepository1.loadOf(clazzId);

        int i=0;
        while (i++<100000){
            clazzRepository1.loadOf(clazzId);
        }

        assertNotNull(clazz1);

        i=1;
        while (i++<=10){
            ClazzId clazzId_ = clazzRepository1.nextIdentity();
            UnitedClazz clazz_ = new UnitedClazz(clazzId_,schoolId,DateUtilWrapper.now());
            clazz_.addHistory(new ClazzHistory(clazzId_,grade,i+"班"));
            clazzRepository1.save(clazz_);
        }

        List<Clazz> clazzes =  clazzRepository1.findClazzCanBeManagedOf(schoolId,grade);
        assertEquals(12, clazzes.size());

        clazzes = clazzRepository1.findAllBySchoolId(schoolId);
    }

}