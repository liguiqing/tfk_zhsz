/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Liguiqing
 * @since V3.0
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({GradeNameGenerateStrategyFactory.class,GradeCourseableFactory.class})
public class SchoolTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstract()throws  Exception{
        School s1 = new School(new TenantId("tid-12345678"),new SchoolId("scid-12345678"),"School1",SchoolType.All);
        assertTrue(s1.name().equals("School1"));
        School s2 = new School(new TenantId("tid-12345678"),new SchoolId("scid-12345678"),"School2",SchoolType.Primary);
        assertTrue(s2.type() == SchoolType.Primary);

    }

    @Test
    public void testGrades()throws Exception{
        GradeNameGenerateStrategy nameGenerateStrategy = PowerMockito.mock(GradeNameGenerateStrategy.class);
        SchoolId sid = new SchoolId("scid-12345678");
        PowerMockito.mockStatic(GradeNameGenerateStrategyFactory.class);
        PowerMockito.when(GradeNameGenerateStrategyFactory.lookup(sid)).thenReturn(nameGenerateStrategy);

        GradeCourseable mockGc =  PowerMockito.mock(GradeCourseable.class);
        PowerMockito.mockStatic(GradeCourseableFactory.class);
        PowerMockito.when(GradeCourseableFactory.lookup(sid)).thenReturn(mockGc);


        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.One)).thenReturn("一年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Two)).thenReturn("二年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Three)).thenReturn("三年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Four)).thenReturn("四年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Five)).thenReturn("五年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Six)).thenReturn("六年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Seven)).thenReturn("七年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Eight)).thenReturn("八年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Nine)).thenReturn("九年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Ten)).thenReturn("十年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Eleven)).thenReturn("十一年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Twelve)).thenReturn("十二年级");

        School s1 = new School(new TenantId("tid-12345678"),sid,"School1",SchoolType.All);
        List<Grade> grades = s1.grades();
        assertTrue(grades.size() == 12);
        Grade g1 = grades.get(0);
        assertEquals(g1.name(),"一年级");

        Grade g2 = grades.get(1);
        assertEquals(g2.name(),"二年级");

        Grade g11 = grades.get(10);
        assertEquals(g11.name(),"十一年级");

        Grade g12 = grades.get(11);
        assertEquals(g12.name(),"十二年级");

        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Seven)).thenReturn("七年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Eight)).thenReturn("八年级");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Nine)).thenReturn("九年级");

        School s2 = new School(new TenantId("tid-12345678"),sid,"School1",SchoolType.Middle);
        List<Grade> grades2 = s2.grades();
        assertTrue(grades2.size() == 3);
        g1 = grades2.get(0);
        assertEquals(g1.name(),"七年级");

        g2 = grades2.get(1);
        assertEquals(g2.name(),"八年级");

        g11 = grades2.get(2);
        assertEquals(g11.name(),"九年级");

        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Ten)).thenReturn("高一");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Eleven)).thenReturn("高二");
        PowerMockito.when(nameGenerateStrategy.genGradeName(GradeLevel.Twelve)).thenReturn("高三");

        School s3 = new School(new TenantId("tid-12345678"),sid,"School1",SchoolType.High);
        List<Grade> grades3 = s3.grades();
        assertTrue(grades3.size() == 3);
        g1 = grades3.get(0);
        assertEquals(g1.name(),"高一");

        g2 = grades3.get(1);
        assertEquals(g2.name(),"高二");

        g11 = grades3.get(2);
        assertEquals(g11.name(),"高三");
    }

}