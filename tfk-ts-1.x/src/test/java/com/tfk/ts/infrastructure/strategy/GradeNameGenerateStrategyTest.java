/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.infrastructure.strategy;

import com.tfk.ts.domain.model.school.GradeLevel;
import com.tfk.ts.domain.model.school.GradeNameGenerateStrategy;
import com.tfk.ts.domain.model.school.common.Configable;
import com.tfk.ts.domain.model.school.common.ConfiguationFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class GradeNameGenerateStrategyTest {

    @Test
    public void testGenGradeName1() throws Exception {
        GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy gradeNameGen =
                new GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy();
        assertEquals("一年级", gradeNameGen.genGradeName(GradeLevel.One));
        assertEquals("二年级", gradeNameGen.genGradeName(GradeLevel.Two));
        assertEquals("三年级", gradeNameGen.genGradeName(GradeLevel.Three));
        assertEquals("四年级", gradeNameGen.genGradeName(GradeLevel.Four));
        assertEquals("五年级", gradeNameGen.genGradeName(GradeLevel.Five));
        assertEquals("六年级", gradeNameGen.genGradeName(GradeLevel.Six));
        assertEquals("初一", gradeNameGen.genGradeName(GradeLevel.Seven));
        assertEquals("初二", gradeNameGen.genGradeName(GradeLevel.Eight));
        assertEquals("初三", gradeNameGen.genGradeName(GradeLevel.Nine));
        assertEquals("高一", gradeNameGen.genGradeName(GradeLevel.Ten));
        assertEquals("高二", gradeNameGen.genGradeName(GradeLevel.Eleven));
        assertEquals("高三", gradeNameGen.genGradeName(GradeLevel.Twelve));

        Configable configable = ConfiguationFactory.getConfigablesByNameAndValue(GradeNameGenerateStrategy.configName,
                gradeNameGen.getClass().getName());
        assertNotNull(configable);
        assertEquals(configable, gradeNameGen);
    }

    @Test
    public void testGenGradeName2() throws Exception {
        GradeNameOneToNineAndG1ToG3GenerateStrategy gradeNameGen =
                new GradeNameOneToNineAndG1ToG3GenerateStrategy();
        assertEquals("一年级", gradeNameGen.genGradeName(GradeLevel.One));
        assertEquals("二年级", gradeNameGen.genGradeName(GradeLevel.Two));
        assertEquals("三年级", gradeNameGen.genGradeName(GradeLevel.Three));
        assertEquals("四年级", gradeNameGen.genGradeName(GradeLevel.Four));
        assertEquals("五年级", gradeNameGen.genGradeName(GradeLevel.Five));
        assertEquals("六年级", gradeNameGen.genGradeName(GradeLevel.Six));
        assertEquals("七年级", gradeNameGen.genGradeName(GradeLevel.Seven));
        assertEquals("八年级", gradeNameGen.genGradeName(GradeLevel.Eight));
        assertEquals("九年级", gradeNameGen.genGradeName(GradeLevel.Nine));
        assertEquals("高一", gradeNameGen.genGradeName(GradeLevel.Ten));
        assertEquals("高二", gradeNameGen.genGradeName(GradeLevel.Eleven));
        assertEquals("高三", gradeNameGen.genGradeName(GradeLevel.Twelve));

        Configable configable = ConfiguationFactory.getConfigablesByNameAndValue(GradeNameGenerateStrategy.configName,
                gradeNameGen.getClass().getName());
        assertNotNull(configable);
        assertEquals(configable, gradeNameGen);
    }

    @Test
    public void testGenGradeName3() throws Exception {
        GradeNameOneToTwelveGenerateStrategy gradeNameGen =
                new GradeNameOneToTwelveGenerateStrategy();
        assertEquals("一年级", gradeNameGen.genGradeName(GradeLevel.One));
        assertEquals("二年级", gradeNameGen.genGradeName(GradeLevel.Two));
        assertEquals("三年级", gradeNameGen.genGradeName(GradeLevel.Three));
        assertEquals("四年级", gradeNameGen.genGradeName(GradeLevel.Four));
        assertEquals("五年级", gradeNameGen.genGradeName(GradeLevel.Five));
        assertEquals("六年级", gradeNameGen.genGradeName(GradeLevel.Six));
        assertEquals("七年级", gradeNameGen.genGradeName(GradeLevel.Seven));
        assertEquals("八年级", gradeNameGen.genGradeName(GradeLevel.Eight));
        assertEquals("九年级", gradeNameGen.genGradeName(GradeLevel.Nine));
        assertEquals("十年级", gradeNameGen.genGradeName(GradeLevel.Ten));
        assertEquals("十一年级", gradeNameGen.genGradeName(GradeLevel.Eleven));
        assertEquals("十二年级", gradeNameGen.genGradeName(GradeLevel.Twelve));

        Configable configable = ConfiguationFactory.getConfigablesByNameAndValue(GradeNameGenerateStrategy.configName,
                gradeNameGen.getClass().getName());
        assertNotNull(configable);
        assertEquals(configable, gradeNameGen);
    }

}