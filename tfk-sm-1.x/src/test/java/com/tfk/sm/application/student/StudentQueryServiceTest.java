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
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    public void test()throws Exception{
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

        students.sort((s1,s2)->(Collator.getInstance(java.util.Locale.CHINA).compare(s1.getName(),s2.getName())));
        assertEquals(names[10],students.get(size-1).getName());
        String fn = StringUtils.left(converterToFirstSpell(students.get(size-1).getName()),1);
        assertEquals("Z",fn);
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
                names[i] = "张麻子";
                continue;
            }

            if(i%9 == 0) {
                names[i] = "伍同学" + i;
                continue;
            }

            if(i%8 == 0) {
                names[i] = "程同学" + i;
                continue;
            }

            if(i%7 == 0) {
                names[i] = "陈同学" + i;
                continue;
            }

            if(i%5 == 0) {
                names[i] = "刘同学" + i;
                continue;
            }

            if(i%5 == 0) {
                names[i] = "胡同学" + i;
                continue;
            }

            if(i%4 == 0) {
                names[i] = "王同学" + i;
                continue;
            }

            if(i%3 == 0) {
                names[i] = "罗同学" + i;
                continue;
            }

            if(i%2 == 0) {
                names[i] = "黄同学" + i;
                continue;
            }
            names[i] = "欧阳同学" + i;
        }
        return names;
    }

    private String converterToFirstSpell(String chines) throws Exception{
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            String s = String.valueOf(nameChar[i]);
            if (s.matches("[\\u4e00-\\u9fa5]")) {
                try {
                    String[] mPinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    pinyinName += mPinyinArray[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

}