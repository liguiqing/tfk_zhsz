package com.zhezhu.assessment.domain.model.assesse;

import com.google.common.collect.Lists;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class AssessTeamServiceTest {

    @Test
    public void teamOfSchool() {
        SchoolId schoolId = new SchoolId();
        SchoolService schoolService = mock(SchoolService.class);
        SchoolData school = SchoolData.builder().name("S1").schoolId(schoolId.id()).scope(SchoolScope.Primary.name()).build();
        List<ClazzData> clazzData = Lists.newArrayList();
        Date now = DateUtilWrapper.now();
        int year = DateUtilWrapper.year(now);
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G1").clazzName("C1").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G1").clazzName("C2").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G2").clazzName("C3").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G2").clazzName("C4").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G3").clazzName("C5").openedTime(now).build());

        when(schoolService.getClazzs(eq(schoolId))).thenReturn(clazzData);
        when(schoolService.getSchool(any(SchoolId.class))).thenReturn(school);

        AssessTeamService assessTeamService = new AssessTeamService();
        List<AssessTeam> teams = assessTeamService.teamOfSchool(schoolId, schoolService);
        assertEquals(8, teams.size());
        assertEquals("S1-"+year+"级",teams.get(0).getTeamName());
        assertNull(teams.get(0).getParentAssessTeamId());
        assertEquals(teams.get(0).getAssessTeamId(),teams.get(1).getParentAssessTeamId());
        assertEquals(clazzData.get(0).getClazzName(),teams.get(1).getTeamName());
        assertEquals(clazzData.get(1).getClazzName(),teams.get(2).getTeamName());
        assertEquals("S1-"+year+"级",teams.get(3).getTeamName());
        assertEquals(teams.get(3).getAssessTeamId(),teams.get(4).getParentAssessTeamId());
        assertEquals(teams.get(3).getAssessTeamId(),teams.get(5).getParentAssessTeamId());
        assertEquals(clazzData.get(2).getClazzName(),teams.get(4).getTeamName());
        assertEquals(clazzData.get(3).getClazzName(),teams.get(5).getTeamName());
        assertEquals("S1-"+year+"级",teams.get(6).getTeamName());
        assertEquals(clazzData.get(4).getClazzName(),teams.get(7).getTeamName());
    }
}