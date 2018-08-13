package com.tfk.assessment.application.medal;

import com.tfk.assessment.domain.model.medal.Award;
import com.tfk.assessment.domain.model.medal.AwardRepository;
import com.tfk.assessment.domain.model.medal.Medal;
import com.tfk.assessment.domain.model.medal.MedalRepository;
import com.tfk.assessment.infrastructure.school.SchoolService;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
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
    public void promotion() throws Exception{
        AwardApplicationService awardApplicationService = getAwardApplicationService();
        PersonId personId = new PersonId();
        SchoolId schoolId = new SchoolId();
        Period period = new Period(DateUtilWrapper.toDate("2018-09-01", "yyyy-MM-dd"),
                DateUtilWrapper.toDate("2019-01-31", "yyyy-MM-dd"));
        Medal medal = Medal.builder()
                .category("c1")
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
        Medal medal = Medal.builder()
                .category("c1")
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