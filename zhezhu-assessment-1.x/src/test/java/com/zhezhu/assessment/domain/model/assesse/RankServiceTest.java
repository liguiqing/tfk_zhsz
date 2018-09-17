package com.zhezhu.assessment.domain.model.assesse;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Term;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class RankServiceTest {
    @Mock
    private AssessRepository assessRepository;

    @Mock
    private AssesseeRepository assesseeRepository;

    @Mock
    private AssessRankRepository assessRankRepository;

    @Mock
    private AssessTeamRepository teamRepository;

    @Mock
    private RankStrategy rankStrategy;

    @Mock
    private SchoolService schoolService;

    @Mock
    private RankCategoryService rankCategoryService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private RankService getService(){
        Optional<RankStrategy> strategyOptional = Optional.empty();
        RankService service = new RankService(assessRepository,assesseeRepository,assessRankRepository,teamRepository,strategyOptional,schoolService,rankCategoryService);
        return spy(service);
    }

    @Test
    public void rank(){
        RankService service = getService();
        ClazzId clazzId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        AssessTeam team = AssessTeam.builder().assessTeamId(new AssessTeamId()).teamId(clazzId.id()).build();
        ClazzData clazz = ClazzData.builder().schoolId(schoolId.id()).build();

        AssessorId assessorId = new AssessorId();
        List<Assess> assesses = Lists.newArrayList();
        for(int i=0;i<10;i++){
            AssesseeId assesseeId = new AssesseeId();
            for(int j=0;j<5;j++){
                assesses.add(Assess.builder().assessTeamId(team.getAssessTeamId()).assessorId(assessorId).assesseeId(assesseeId).indexId(new IndexId()).score(i).build());
            }
            Assessee assessee = Assessee.builder().role("Student").personId(new PersonId()).assesseeId(assesseeId).build();
            when(assesseeRepository.loadOf(eq(assesseeId))).thenReturn(assessee);
        }

        when(teamRepository.findByTeamId(eq(clazzId.id()))).thenReturn(team);
        when(teamRepository.loadOf(any(AssessTeamId.class))).thenReturn(team);
        when(schoolService.getClazz(eq(clazzId))).thenReturn(clazz);
        when(schoolService.getSchoolTermOfNow(any(SchoolId.class))).thenReturn(Term.First());
        when(assessRepository.findByAssessTeamIdAndDoneDateBetween(any(AssessTeamId.class), any(Date.class), any(Date.class))).thenReturn(assesses);
        when(rankCategoryService.from(any(RankCategory.class))).thenReturn(new Date());
        when(rankCategoryService.to(any(RankCategory.class))).thenReturn(new Date());
        when(rankCategoryService.node(any(RankCategory.class)))
                .thenReturn(LocalDate.now().toString())
                .thenReturn(LocalDate.now().get(WeekFields.of(Locale.CHINA).weekOfYear())+"");
        List<AssessRank> ranks = service.rank(clazzId.id(), RankCategory.Day, RankScope.Clazz);
        assertEquals(10,ranks.size());
        assertEquals(0,ranks.get(0).getPromote());
        assertEquals(1,ranks.get(0).getRank());
        assertEquals(10,ranks.get(9).getRank());
        assertEquals(assesses.get(0).getAssesseeId(),ranks.get(9).getAssesseeId());
        assertEquals(assesses.get(46).getAssesseeId(),ranks.get(0).getAssesseeId());
        assertEquals(LocalDate.now().toString(),ranks.get(9).getRankNode());

        ranks = service.rank(clazzId.id(), RankCategory.Weekend, RankScope.Clazz);
        assertEquals(10,ranks.size());
        assertEquals(0,ranks.get(0).getPromote());
        assertEquals(1,ranks.get(0).getRank());
        assertEquals(10,ranks.get(9).getRank());
        assertEquals(assesses.get(0).getAssesseeId(),ranks.get(9).getAssesseeId());
        assertEquals(assesses.get(46).getAssesseeId(),ranks.get(0).getAssesseeId());
        assertEquals(DateUtilWrapper.weekOfYear(DateUtilWrapper.now())+"",ranks.get(9).getRankNode());

        ranks = service.rank(clazzId.id(), RankCategory.Month, RankScope.Clazz);
        assertEquals(10,ranks.size());
        assertEquals(0,ranks.get(0).getPromote());
        assertEquals(1,ranks.get(0).getRank());
        assertEquals(10,ranks.get(9).getRank());
        assertEquals(assesses.get(0).getAssesseeId(),ranks.get(9).getAssesseeId());
        assertEquals(assesses.get(46).getAssesseeId(),ranks.get(0).getAssesseeId());

        ranks = service.rank(clazzId.id(), RankCategory.Term, RankScope.Clazz);
        assertEquals(10,ranks.size());
        assertEquals(0,ranks.get(0).getPromote());
        assertEquals(1,ranks.get(0).getRank());
        assertEquals(10,ranks.get(9).getRank());
        assertEquals(assesses.get(0).getAssesseeId(),ranks.get(9).getAssesseeId());
        assertEquals(assesses.get(46).getAssesseeId(),ranks.get(0).getAssesseeId());

        ranks = service.rank(clazzId.id(), RankCategory.Year, RankScope.Clazz);
        assertEquals(10,ranks.size());
        assertEquals(0,ranks.get(0).getPromote());
        assertEquals(1,ranks.get(0).getRank());
        assertEquals(10,ranks.get(9).getRank());
        assertEquals(assesses.get(0).getAssesseeId(),ranks.get(9).getAssesseeId());
        assertEquals(assesses.get(46).getAssesseeId(),ranks.get(0).getAssesseeId());

        ranks = service.rank(clazzId.id(), RankCategory.Weekend, RankScope.School);
        assertEquals(10,ranks.size());
        assertEquals(0,ranks.get(0).getPromote());
        assertEquals(1,ranks.get(0).getRank());
        assertEquals(10,ranks.get(9).getRank());
        assertEquals(assesses.get(0).getAssesseeId(),ranks.get(9).getAssesseeId());
        assertEquals(assesses.get(46).getAssesseeId(),ranks.get(0).getAssesseeId());
    }
}