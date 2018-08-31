package com.tfk.sm.application.clazz;

import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.application.data.ClazzData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzRepository;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/
public class ClazzQueryServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private ClazzRepository clazzRepository;
    
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void findSchoolGradeClazzesCanBeManagedOfNow() throws Exception{
        Grade grade = Grade.G1();

        ClazzQueryService service = getClazzQueryService();
        SchoolId schoolId = new SchoolId();
        School school = mock(School.class);
        when(schoolRepository.loadOf(any(SchoolId.class))).thenReturn(null).thenReturn(school).thenReturn(school);
        when(school.hasGrade(any(Grade.class))).thenReturn(false).thenReturn(true).thenReturn(true);
        when(school.getSchoolId()).thenReturn(schoolId);

        Clazz c1 = mock(Clazz.class);
        Clazz c2 = mock(Clazz.class);
        Clazz c11 = mock(Clazz.class);
        Clazz c3 = mock(Clazz.class);
        Clazz c4 = mock(Clazz.class);
        Clazz c5 = mock(Clazz.class);

        ClazzId id1 = new ClazzId();
        ClazzId id2 = new ClazzId();
        ClazzId id3 = new ClazzId();
        ClazzId id4 = new ClazzId();
        ClazzId id5 = new ClazzId();
        when(c1.getClazzId()).thenReturn(id1).thenReturn(id1);
        when(c11.getClazzId()).thenReturn(id1).thenReturn(id1);
        when(c2.getClazzId()).thenReturn(id2).thenReturn(id2);
        when(c3.getClazzId()).thenReturn(id3).thenReturn(id3);
        when(c4.getClazzId()).thenReturn(id4).thenReturn(id4);
        when(c5.getClazzId()).thenReturn(id5).thenReturn(id5);

        List<Clazz> clazzes = Arrays.asList(c1, c2, c11, c3, c4, c5);
        when(clazzRepository.findClazzCanBeManagedOf(any(SchoolId.class), eq(grade))).thenReturn(null).thenReturn(clazzes).thenReturn(clazzes);
        when(clazzRepository.loadOf(any(ClazzId.class))).thenReturn(c1).thenReturn(c2).thenReturn(null).thenReturn(c3).thenReturn(c4).thenReturn(c5);


        when(c1.getGradeFullName(any(Grade.class))).thenReturn(grade.getName()+"1班");
        when(c2.getGradeFullName(any(Grade.class))).thenReturn(grade.getName()+"2班");
        when(c3.getGradeFullName(any(Grade.class))).thenReturn(grade.getName()+"3班");
        when(c4.getGradeFullName(any(Grade.class))).thenReturn(grade.getName()+"4班");
        when(c5.getGradeFullName(any(Grade.class))).thenReturn(grade.getName()+"5班");


        List<ClazzData> datas = service.findSchoolGradeClazzesCanBeManagedOfNow(schoolId.id(),grade.getLevel());
        assertEquals(0,datas.size());
        datas = service.findSchoolGradeClazzesCanBeManagedOfNow(schoolId.id(),grade.getLevel());
        assertEquals(0,datas.size());
        datas = service.findSchoolGradeClazzesCanBeManagedOfNow(schoolId.id(),grade.getLevel());
        assertEquals(0,datas.size());
        datas = service.findSchoolGradeClazzesCanBeManagedOfNow(schoolId.id(),grade.getLevel());
        assertEquals(5,datas.size());
        assertEquals(grade.getName()+"1班",datas.get(0).getName());
        assertEquals(grade.getName()+"5班",datas.get(4).getName());

        verify(schoolRepository,times(4)).loadOf(any(SchoolId.class));
        verify(clazzRepository,times(6)).loadOf(any(ClazzId.class));
        verify(clazzRepository,times(2)).findClazzCanBeManagedOf(any(SchoolId.class), eq(grade));
    }

    private ClazzQueryService getClazzQueryService()throws Exception{
        ClazzQueryService service = new ClazzQueryService();
        List<ClazzRepository> clazzRepositories = Arrays.asList(clazzRepository);
        FieldUtils.writeField(service,"schoolRepository",schoolRepository,true);
        FieldUtils.writeField(service,"clazzRepositories",clazzRepositories,true);
        return spy(service);
    }
}