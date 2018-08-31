package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.SubjectId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.contact.QQ;
import com.tfk.share.domain.person.contact.Weixin;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;
import com.tfk.sm.SmTestConfiguration;
import com.tfk.sm.config.SmApplicationConfiguration;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.UnitedClazz;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
public class TeacherRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void test(){
        assertNotNull(teacherRepository);
        SchoolId schoolId = new SchoolId("SCH12345678");
        PersonId personId = new PersonId("PER12345678");
        TeacherId teacherId = new TeacherId("TEA12345678");
        Teacher teacher = new Teacher(teacherId,personId,schoolId,"Test Teacher");
        ContactValidations contactValidations = mock(ContactValidations.class);
        when(contactValidations.validate(any(Contact.class))).thenReturn(true).thenReturn(true).thenReturn(true);
        teacher.addContact(contactValidations,new QQ(123564+""));
        teacher.addContact(contactValidations,new QQ(123567+""));
        teacher.addContact(contactValidations,new Weixin("123456@123.com"));

        Course yw = new Course("语文",new SubjectId("SUB123456781"));

        teacher.addCourse(Grade.G1(),yw);
        teacher.addCourse(Grade.G2(),yw);
        teacher.addCourse(Grade.G3(),yw);
        teacher.addCourse(Grade.G4(),yw);
        teacher.addCourse(Grade.G5(),yw);
        teacher.addCourse(Grade.G6(),yw);

        ClazzId clazzId1 = new ClazzId("CLA123456781");
        ClazzId clazzId2 = new ClazzId("CLA123456782");
        Clazz c1 = new UnitedClazz(clazzId1,schoolId);
        Clazz c2 = new UnitedClazz(clazzId2,schoolId);
        Date dateStarts1 = DateUtilWrapper.toDate("2018-02-25", "yyyy-MM-dd");
        Date dateEnds1 = DateUtilWrapper.toDate("2018-06-25", "yyyy-MM-dd");
        Period p1 = new Period(dateStarts1, dateEnds1);

        teacher.teachingAt(p1,Grade.G1(),c1,yw);
        teacher.teachingAt(p1,Grade.G1(),c2,yw);
        teacher.managementAt(p1,Grade.G1(),c2);
        teacherRepository.save(teacher);

        Teacher teacher1 =  teacherRepository.loadOf(teacherId);
        assertEquals(teacher,teacher1);
        Set<Contact> contacts = teacher1.getContacts();
        assertTrue(contacts.contains(new QQ(123564+"")));
        for(int i = 0;i<10000;i++){
            teacher1 =  teacherRepository.loadOf(teacherId);
        }
        assertNotNull(teacher1);
        teacherRepository.delete(teacherId.id());
        teacher1 =  teacherRepository.loadOf(teacherId);
        assertNull(teacher1);

        List<Teacher> teachers = teacherRepository.findAll();
    }
}