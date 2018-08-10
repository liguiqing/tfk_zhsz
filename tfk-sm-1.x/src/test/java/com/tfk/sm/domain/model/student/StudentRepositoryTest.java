package com.tfk.sm.domain.model.student;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.SubjectId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.contact.QQ;
import com.tfk.share.domain.person.contact.Weixin;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.UnitedClazz;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
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
public class StudentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void save(){
        SchoolId schoolId = new SchoolId("SCH12345678");
        PersonId personId = new PersonId("PER12345678");
        ClazzId clazzId = new ClazzId("CLA12345678");
        StudentId studentId = new StudentId("STU12345678");

        ContactValidations contactValidations = mock(ContactValidations.class);
        when(contactValidations.validate(any(Contact.class))).thenReturn(true).thenReturn(true).thenReturn(true);
        Student student = new Student(studentId,schoolId,personId,"Test");
        student.addContact(contactValidations,new QQ(123564+""));
        student.addContact(contactValidations,new QQ(123567+""));
        student.addContact(contactValidations,new Weixin("123456@123.com"));

        Course yw = new Course("语文",new SubjectId("SUB123456781"));
        ClazzId clazzId1 = new ClazzId("CLA123456781");
        Clazz c1 = new UnitedClazz(clazzId1,schoolId);

        Date dateStarts1 = DateUtilWrapper.toDate("2018-02-25", "yyyy-MM-dd");
        Date dateEnds1 = DateUtilWrapper.toDate("2018-06-25", "yyyy-MM-dd");
        Period p1 = new Period(dateStarts1, dateEnds1);

        student.studyAt(p1,Grade.G1(),c1,yw);
        student.managedAt(p1,Grade.G1(),c1);

        studentRepository.save(student);

        Student student1 = studentRepository.loadOf(studentId);
        assertEquals(student,student1);

        Set<Contact> contacts = student1.contacts();
        assertTrue(contacts.contains(new QQ(123564+"")));
    }
}