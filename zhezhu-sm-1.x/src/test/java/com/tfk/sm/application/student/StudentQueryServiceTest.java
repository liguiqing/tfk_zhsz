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
import com.tfk.sm.application.data.StudentNameGroupData;
import com.tfk.sm.application.data.StudentNameSortedData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.UnitedClazz;
import com.tfk.sm.domain.model.student.Student;
import com.tfk.sm.domain.model.student.StudentRepository;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
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

    @Test
    public void findStudentInOfNowAndSortByName()throws Exception{
        StudentQueryService studentQueryService = getStudentQueryService();
        List<StudentData> students = students();

        doReturn(students).when(studentQueryService).findStudentInOfNow(any(String.class), any(String.class));

        ClazzId clazzId = new ClazzId(Identities.genIdNone(IdPrefixes.ClazzIdPrefix));
        SchoolId schoolId = new SchoolId(Identities.genIdNone(IdPrefixes.SchoolIdPrefix));
        StudentNameSortedData sortedData = studentQueryService.findStudentInOfNowAndSortByName(schoolId.id(), clazzId.id());

        assertNotNull(sortedData);
        assertEquals(6,sortedData.size());
        List<StudentNameGroupData> groupData = sortedData.getGroups();
        assertEquals("C",groupData.get(0).getLetter());
        assertEquals("Z",groupData.get(5).getLetter());
    }

    public List<StudentData> students(){
        ClazzId clazzId = new ClazzId(Identities.genIdNone(IdPrefixes.ClazzIdPrefix));
        SchoolId schoolId = new SchoolId(Identities.genIdNone(IdPrefixes.SchoolIdPrefix));
        Clazz clazz = mock(UnitedClazz.class);
        Grade grade = Grade.G1();
        when(clazz.currentGrade()).thenReturn(grade);
        when(clazz.canBeManagedAt()).thenReturn(true);

        int size = 100;
        String[] names = names(size);

        List<StudentData> students = Lists.newArrayList();
        for(int i=0;i<size;i++){
            PersonId personId = new PersonId(Identities.genIdNone(IdPrefixes.PersonIdPrefix));
            StudentId studentId = new StudentId(Identities.genIdNone(IdPrefixes.StudentIdPrefix));
            Student student = mock(Student.class);
            when(student.currentManagedClazz()).thenReturn(clazzId);
            when(student.currentStudyClazz()).thenReturn(clazzId);
            when(student.getSchoolId()).thenReturn(schoolId);
            when(student.getPersonId()).thenReturn(personId);
            when(student.getStudentId()).thenReturn(studentId);
            students.add(toStudentData(student, clazz,names[i]));
        }
        return students;
    }


    private StudentData toStudentData(Student student,Clazz clazz,String name){
        ClazzId clazzId = clazz.canBeManagedAt()?student.currentManagedClazz():student.currentStudyClazz();
        Grade grade = clazz.currentGrade();
        return StudentData.builder()
                .clazzId(clazzId.id())
                .clazzName(clazz.getGradeFullName(grade))
                .gradeName(grade.getName())
                .gradeLevel(grade.getLevel())
                .schoolId(student.getSchoolId().id())
                .personId(student.getPersonId().id())
                .studentId(student.getStudentId().id())
                .name(name)
                .build();
    }

    private String[] names(int size){
        String[] names = new String[size];
        for(int i=0;i<size;i++){
            if(i%10 == 0) {
                names[i] = "张麻子";//z
                continue;
            }

            if(i%9 == 0) {
                names[i] = "伍同学" + i;//w
                continue;
            }

            if(i%8 == 0) {
                names[i] = "程同学" + i;//c
                continue;
            }

            if(i%7 == 0) {
                names[i] = "陈同学" + i;//c
                continue;
            }

            if(i%6 == 0) {
                names[i] = "刘同学" + i;//l
                continue;
            }

            if(i%5 == 0) {
                names[i] = "胡同学" + i;//h
                continue;
            }

            if(i%4 == 0) {
                names[i] = "王同学" + i;//w
                continue;
            }

            if(i%3 == 0) {
                names[i] = "罗同学" + i;//l
                continue;
            }

            if(i%2 == 0) {
                names[i] = "黄同学" + i;//h
                continue;
            }
            names[i] = "欧阳同学" + i;//o
        }
        return names;
    }

}