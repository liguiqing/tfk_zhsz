/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import com.tfk.commons.spring.SpringContextUtil;
import com.tfk.ts.application.school.SchoolQueryService;
import com.tfk.ts.domain.model.school.common.Configuation;
import com.tfk.ts.domain.model.school.common.SchoolConfig;
import com.tfk.ts.infrastructure.strategy.GradeNameOneToNineAndG1ToG3GenerateStrategy;
import com.tfk.ts.infrastructure.strategy.GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy;
import com.tfk.ts.infrastructure.strategy.GradeNameOneToTwelveGenerateStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringContextUtil.class)
public class GradeNameGenerateStrategyFactoryTest {

    @Test
    public void testLookup() throws Exception{
        GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy gradeNameGen1 =
                new GradeNameOneToSixAndC1ToC3AndG1ToG3GenerateStrategy();
        GradeNameOneToNineAndG1ToG3GenerateStrategy gradeNameGen2 =
                new GradeNameOneToNineAndG1ToG3GenerateStrategy();
        GradeNameOneToTwelveGenerateStrategy gradeNameGen3 =
                new GradeNameOneToTwelveGenerateStrategy();

        SchoolId schoolId = new SchoolId("SchoolId-12345678");

        List<SchoolConfig> configs1 = new ArrayList<>();
        configs1.add(new SchoolConfig(schoolId, new Configuation(GradeNameGenerateStrategy.configName,
                gradeNameGen1.getClass().getName())));

        List<SchoolConfig> configs2 = new ArrayList<>();
        configs2.add(new SchoolConfig(schoolId, new Configuation(GradeNameGenerateStrategy.configName,
                gradeNameGen2.getClass().getName())));

        List<SchoolConfig> configs3 = new ArrayList<>();
        configs3.add(new SchoolConfig(schoolId, new Configuation(GradeNameGenerateStrategy.configName,
                gradeNameGen3.getClass().getName())));

        PowerMockito.mockStatic(SpringContextUtil.class);
        SchoolQueryService queryService = PowerMockito.mock(SchoolQueryService.class);
        PowerMockito.when(queryService.schoolConfigs(schoolId.id()))
                .thenReturn(configs1).thenReturn(configs2).thenReturn(configs3).thenReturn(null);
        PowerMockito.when(SpringContextUtil.getBean(SchoolQueryService.class)).thenReturn(queryService);

        GradeNameGenerateStrategy nameGenerateStrategy = GradeNameGenerateStrategyFactory.lookup(schoolId);
        assertNotNull(nameGenerateStrategy);
        assertEquals(nameGenerateStrategy,gradeNameGen1);

        nameGenerateStrategy = GradeNameGenerateStrategyFactory.lookup(schoolId);
        assertNotNull(nameGenerateStrategy);
        assertEquals(nameGenerateStrategy,gradeNameGen2);

        nameGenerateStrategy = GradeNameGenerateStrategyFactory.lookup(schoolId);
        assertNotNull(nameGenerateStrategy);
        assertEquals(nameGenerateStrategy,gradeNameGen3);

        nameGenerateStrategy = GradeNameGenerateStrategyFactory.lookup(schoolId);
        assertNotNull(nameGenerateStrategy);
        assertEquals(nameGenerateStrategy,gradeNameGen2);
    }

}