package com.zhezhu.sm.domain.model.clazz;

import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.sm.SmTestConfiguration;
import com.zhezhu.sm.config.SmApplicationConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        Grade grade = Grade.G1();
        clazz.addHistory(grade,"一班");
        clazzRepository1.save(clazz);

        StudyYear studyYear = StudyYear.now();

        Clazz clazz1 = clazzRepository1.loadOf(clazzId);
        clazz1.addHistory(grade,"一班");
        clazz1.getHistories().forEach(h->assertTrue(h.sameYearOf(studyYear)));
        int i=0;
        while (i++<100000){
            clazzRepository1.loadOf(clazzId);
        }

        assertNotNull(clazz1);

        i=1;
        while (i++<=10){
            ClazzId clazzId_ = clazzRepository1.nextIdentity();
            UnitedClazz clazz_ = new UnitedClazz(clazzId_,schoolId,DateUtilWrapper.now());
            clazz_.addHistory(grade,i+"班");
            clazzRepository1.save(clazz_);
        }

        List<Clazz> clazzes =  clazzRepository1.findClazzCanBeManagedOf(schoolId,grade);
        assertEquals(11, clazzes.size());

        clazzes = clazzRepository1.findAllBySchoolId(schoolId);
        assertEquals(11, clazzes.size());
    }

}