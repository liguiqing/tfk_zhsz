package com.tfk.sm.application.student;

import com.google.common.collect.Lists;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.application.clazz.ClazzApplicationService;
import com.tfk.sm.application.data.StudentData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.UnitedClazz;
import com.tfk.sm.domain.model.student.Student;
import com.tfk.sm.domain.model.student.StudentRepository;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class StudentQueryServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClazzApplicationService clazzApplicationService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    private StudentQueryService getStudentQueryService()throws Exception{
        StudentQueryService studentQueryService = new StudentQueryService();
        FieldUtils.writeField(studentQueryService,"studentRepository",studentRepository,true);
        FieldUtils.writeField(studentQueryService,"clazzApplicationService",clazzApplicationService,true);
        return spy(studentQueryService);
    }

    @Test
    public void findStudentInOfNow() throws Exception{
        StudentQueryService studentQueryService = getStudentQueryService();
        ClazzId clazzId = new ClazzId(Identities.genIdNone(IdPrefixes.ClazzIdPrefix));
        SchoolId schoolId = new SchoolId(Identities.genIdNone(IdPrefixes.SchoolIdPrefix));
        Clazz clazz = mock(UnitedClazz.class);
        Grade grade = Grade.G1();

        List<Student> students = Lists.newArrayList();
        for(int i=0;i<5;i++){
            PersonId personId = new PersonId(Identities.genIdNone(IdPrefixes.PersonIdPrefix));
            StudentId studentId = new StudentId(Identities.genIdNone(IdPrefixes.StudentIdPrefix));
            Student student = mock(Student.class);
            when(student.currentManagedClazz()).thenReturn(clazzId);
            when(student.currentStudyClazz()).thenReturn(clazzId);
            when(student.getSchoolId()).thenReturn(schoolId);
            when(student.getPersonId()).thenReturn(personId);
            when(student.getStudentId()).thenReturn(studentId);
            students.add(student);
        }

        when(clazz.currentGrade()).thenReturn(grade);
        when(clazzApplicationService.getClazz(any(String.class))).thenReturn(clazz);
        when(clazz.canBeManagedAt()).thenReturn(true).thenReturn(false);
        when(studentRepository.findByManageds(any(SchoolId.class), any(ClazzId.class), any(Grade.class))).thenReturn(students);
        when(studentRepository.findByStudies(any(SchoolId.class), any(ClazzId.class), any(Grade.class))).thenReturn(students);
        when(clazzApplicationService.getClazz(any(String.class))).thenReturn(clazz);

        List<StudentData> studentDatas = studentQueryService.findStudentInOfNow(schoolId.id(),clazzId.id());
        assertNotNull(studentDatas);
        assertEquals(5, studentDatas.size());

        studentDatas = studentQueryService.findStudentInOfNow(schoolId.id(),clazzId.id());
        assertNotNull(studentDatas);
        assertEquals(5, studentDatas.size());

        verify(clazzApplicationService, times(2)).getClazz(any(String.class));
        verify(studentRepository,times(1)).findByManageds(any(SchoolId.class),any(ClazzId.class),any(Grade.class));
        verify(studentRepository,times(1)).findByStudies(any(SchoolId.class),any(ClazzId.class),any(Grade.class));
    }
}