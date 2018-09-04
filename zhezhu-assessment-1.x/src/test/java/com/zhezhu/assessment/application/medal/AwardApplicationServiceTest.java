package com.zhezhu.assessment.application.medal;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.medal.*;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.medal.MedalId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/
public class AwardApplicationServiceTest {

    @Mock
    private SchoolService schoolService;

    @Mock
    private AwardRepository awardRepository;

    @Mock
    private MedalRepository medalRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void promotionAllSchool()throws Exception{
        AwardApplicationService awardApplicationService = getAwardApplicationService();
        doNothing().when(awardApplicationService).promotion(any(PersonId.class),any(SchoolId.class));
        List<PersonId> personIds = Lists.newArrayList();
        when(schoolService.getAllStudentPersonIds(any(SchoolId.class))).thenReturn(personIds);
        awardApplicationService.promotion(new SchoolId());
    }

    @Test
    public void promotion() throws Exception{
        AwardApplicationService awardApplicationService = getAwardApplicationService();
        MedalLevel l1 = new MedalLevel(1, "B");
        PersonId personId = new PersonId();
        SchoolId schoolId = new SchoolId();
        Period period = new Period(DateUtilWrapper.toDate("2018-09-01", "yyyy-MM-dd"),
                DateUtilWrapper.toDate("2019-01-31", "yyyy-MM-dd"));
        Medal medal = Medal.builder()
                .level(l1)
                .name("C1")
                .upLeast(3)
                .schoolId(schoolId)
                .build();
        List<Medal> medals = Arrays.asList(medal, medal, medal);
        when(medalRepository.findMedalsBySchoolId(any(SchoolId.class))).thenReturn(medals);
        when(schoolService.getSchoolTermPeriod(any(SchoolId.class))).thenReturn(period);
        doNothing().when(awardApplicationService).promotionAMedal(any(Medal.class),any(PersonId.class),any(Period.class));
        awardApplicationService.promotion(personId,schoolId);

        verify(medalRepository, times(1)).findMedalsBySchoolId(any(SchoolId.class));
        verify(awardApplicationService,times(3)).promotionAMedal(any(Medal.class),any(PersonId.class),any(Period.class));
    }

    @Test
    public void promotionAMedal() throws Exception{
        AwardApplicationService awardApplicationService = getAwardApplicationService();
        PersonId personId = new PersonId();
        SchoolId schoolId = new SchoolId();
        Period period = new Period(DateUtilWrapper.toDate("2018-09-01", "yyyy-MM-dd"),
                DateUtilWrapper.toDate("2019-01-31", "yyyy-MM-dd"));
        MedalId medalId = new MedalId();
        Medal high = mock(Medal.class);
        when(high.gt(any(Medal.class))).thenReturn(true);
        MedalLevel l1 = new MedalLevel(1, "B");
        Medal medal = Medal.builder()
                .level(l1)
                .name("C1")
                .upLeast(3)
                .medalId(medalId)
                .schoolId(schoolId)
                .high(high)
                .build();
        Award award = mock(Award.class);
        List<Award> awards = Arrays.asList(award,award,award);
        when(awardRepository.findAwardsByPossessorIdAndMedalIdEqualsAndWinDateBetweenAndRiseToIsNull(any(PersonId.class),any(MedalId.class),any(Date.class),any(Date.class))).thenReturn(awards);
        doNothing().when(awardRepository).save(any(Award.class));
        awardApplicationService.promotionAMedal(medal,personId,period);
        verify(awardRepository,times(4)).save(any(Award.class));
    }

    private AwardApplicationService getAwardApplicationService()throws Exception{
        AwardApplicationService awardApplicationService = new AwardApplicationService();
        FieldUtils.writeField(awardApplicationService,"schoolService",schoolService,true);
        FieldUtils.writeField(awardApplicationService,"awardRepository",awardRepository,true);
        FieldUtils.writeField(awardApplicationService,"medalRepository",medalRepository,true);
        return spy(awardApplicationService);
    }
}