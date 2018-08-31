package com.tfk.sm.application.student;

import com.tfk.commons.domain.Identities;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.IdPrefixes;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.Gender;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;
import com.tfk.sm.application.data.Contacts;
import com.tfk.sm.application.data.StudyData;
import com.tfk.sm.domain.model.clazz.*;
import com.tfk.sm.domain.model.student.Student;
import com.tfk.sm.domain.model.student.StudentRepository;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/
public class StudentApplicationServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LearningClazzRepository learningClazzRepository;

    @Mock
    private SuperviseClazzRepository superviseClazzRepository;

    @Mock
    private UnitedClazzRepository unitedClazzRepository;

    @Mock
    private ContactValidations contactValidations;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private StudentApplicationService getStudentApplicationService()throws Exception{
        StudentApplicationService studentApplicationService = new StudentApplicationService();
        FieldUtils.writeField(studentApplicationService,"studentRepository",studentRepository,true);
        FieldUtils.writeField(studentApplicationService,"learningClazzRepository",learningClazzRepository,true);
        FieldUtils.writeField(studentApplicationService,"superviseClazzRepository",superviseClazzRepository,true);
        FieldUtils.writeField(studentApplicationService,"unitedClazzRepository",unitedClazzRepository,true);
        FieldUtils.writeField(studentApplicationService,"contactValidations",contactValidations,true);

        return spy(studentApplicationService);
    }

    @Test
    public void newStudent() throws Exception{
        StudentApplicationService studentApplicationService = getStudentApplicationService();
        Date joinDate = DateUtilWrapper.now();
        Contacts[] contacts = new Contacts[5];
        contacts[0] = new Contacts("QQ", "123456");
        contacts[2] = new Contacts("Email", "123456@aa.com");
        contacts[3] = new Contacts("Mobile", "85623321");
        contacts[4] = new Contacts("QQ", "1234567");
        contacts[1] = new Contacts("Weixin", "123456@aa.com");
        String schoolId = Identities.genIdNone(IdPrefixes.SchoolIdPrefix);

        NewStudentCommand command = new NewStudentCommand(schoolId,joinDate,null,
                "Test Student",null,Gender.Male,contacts);

        when(contactValidations.validate(any(Contact.class))).thenReturn(true).thenReturn(true)
                .thenReturn(true).thenReturn(true).thenReturn(true);
        when(studentRepository.nextIdentity()).thenReturn(new StudentId(Identities.genIdNone(IdPrefixes.StudentIdPrefix)));

        String studentId = studentApplicationService.newStudent(command);
        assertNotNull(studentId);
        assertTrue(studentId.startsWith(IdPrefixes.StudentIdPrefix));
        verify(contactValidations,times(5)).validate(any(Contact.class));
        verify(studentRepository,times(1)).save(any(Student.class));
        verify(studentRepository,times(1)).nextIdentity();
    }


    @Test
    public void arrangingClazz()throws Exception{
        StudentApplicationService studentApplicationService = getStudentApplicationService();
        StudentId studentId = new StudentId(Identities.genIdNone(IdPrefixes.StudentIdPrefix));

        ClazzId clazzId = new ClazzId(Identities.genIdNone(IdPrefixes.ClazzIdPrefix));
        String subjectId1 = Identities.genIdNone(IdPrefixes.SubjectIdPrefix);
        String subjectId2 = Identities.genIdNone(IdPrefixes.SubjectIdPrefix);
        String subjectId3 = Identities.genIdNone(IdPrefixes.SubjectIdPrefix);
        StudyData[] studyDatas = new StudyData[3];
        studyDatas[0] = new StudyData(clazzId.id(),"语文",subjectId1,6);
        studyDatas[1] = new StudyData(clazzId.id(),"数学",subjectId2,6);
        studyDatas[2] = new StudyData(clazzId.id(),"英语",subjectId3,6);

        Date dateStarts = DateUtilWrapper.toDate("2018-02-25", "yyyy-MM-dd");
        Date dateEnds = DateUtilWrapper.toDate("2018-06-25", "yyyy-MM-dd");

        Clazz clazz = mock(UnitedClazz.class);

        Student student = mock(Student.class);

        ArrangeStudentCommand command = mock(ArrangeStudentCommand.class);
        when(command.getStudentId()).thenReturn(studentId.id());
        when(command.getDateStarts()).thenReturn(dateStarts);
        when(command.getDateEnds()).thenReturn(dateEnds);
        when(command.getManagedClazzId()).thenReturn(clazzId.id());
        when(command.getCourses()).thenReturn(studyDatas);
        when(student.getStudentId()).thenReturn(studentId);
        when(studentRepository.loadOf(any(StudentId.class))).thenReturn(student);
        when(superviseClazzRepository.loadOf(any(ClazzId.class))).thenReturn(null);
        when(unitedClazzRepository.loadOf(any(ClazzId.class))).thenReturn((UnitedClazz)clazz);

        //doNothing().when(studentApplicationService).arrangingStudies(any(ArrangeStudentCommand.class),any(Student.class));
        studentApplicationService.arrangingClazz(command);
    }
}