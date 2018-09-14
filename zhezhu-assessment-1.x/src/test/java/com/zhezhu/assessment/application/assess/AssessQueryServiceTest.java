package com.zhezhu.assessment.application.assess;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.index.IndexScore;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class AssessQueryServiceTest {

    @Mock
    private IndexRepository indexRepository;

    @Mock
    private AssessRepository assessRepository;

    @Mock
    private AssessRankRepository rankRepository;

    @Mock
    private SchoolService schoolService;

    private AssesseeRepository assesseeRepository;



    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    private AssessQueryService getService(){
        AssessQueryService service = new AssessQueryService(indexRepository,assesseeRepository,assessRepository,rankRepository,schoolService);
        return spy(service);
    }

    @Test
    public void getRanks(){

    }

    @Test
    public void getSchoolRanks() {
        AssessQueryService service = getService();
        SchoolId schoolId = new SchoolId();
        PersonId personId = new PersonId();

        List<AssessRank> assessRanks = Lists.newArrayList();
        assessRanks.add(AssessRank.builder()
                .personId(personId)
                .rankCategory(RankCategory.Weekend)
                .rankScope(RankScope.Clazz)
                .rank(10)
                .yearStarts(2018)
                .yearEnds(2019)
                .rankDate(DateUtilWrapper.toDate("2018-09-03","yyyy-MM-dd"))
                .promote(2)
                .build());
        assessRanks.add(AssessRank.builder()
                .personId(personId)
                .rankCategory(RankCategory.Weekend)
                .rankScope(RankScope.Clazz)
                .yearStarts(2018)
                .yearEnds(2019)
                .rankDate(DateUtilWrapper.toDate("2018-09-05","yyyy-MM-dd"))
                .rank(12)
                .promote(-2)
                .build());

        Date from  = DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd");
        Date to  = DateUtilWrapper.toDate("2018-09-30","yyyy-MM-dd");
        StudentData student = mock(StudentData.class);
        when(student.getManagedClazzId()).thenReturn(new ClazzId().id());
        when(student.getSchoolId()).thenReturn(schoolId.id());
        when(schoolService.getStudentBy(any(PersonId.class))).thenReturn(student);
        when(rankRepository.findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(any(String.class)
                , any(PersonId.class), any(RankCategory.class), any(RankScope.class), eq(from), eq(to))).thenReturn(assessRanks);

        List<SchoolAssessRankData> ranks = service.getPersonalRanks(schoolId.id(), personId.id(), RankCategory.Weekend,
                RankScope.Clazz, from, to);
        assertEquals(assessRanks.size(),ranks.size());
        assertEquals(assessRanks.get(0).getRankDate(),ranks.get(1).getRankDate());
        verify(rankRepository,times(1)).findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(any(String.class)
                , any(PersonId.class), any(RankCategory.class), any(RankScope.class), eq(from), eq(to));

        when(rankRepository.findAllByAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndRankDateBetween(any(String.class)
                ,any(RankCategory.class), any(RankScope.class),any(String.class), eq(from), eq(to))).thenReturn(assessRanks);
        ranks = service.getTeamRanks(schoolId.id(), RankCategory.Weekend,
                RankScope.Clazz,"", from, to);
        assertEquals(assessRanks.size(),ranks.size());
        assertEquals(assessRanks.get(0).getRankDate(),ranks.get(1).getRankDate());
    }

    @Test
    public void getAssessOf() {
        AssessQueryService service = getService();

        List<Assess> assesses = Lists.newArrayList();
        IndexId indexId = new IndexId();
        assesses.add(Assess.builder().indexId(indexId).score(1).doneDate(DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd")).build());
        assesses.add(Assess.builder().indexId(indexId).score(2).doneDate(DateUtilWrapper.toDate("2018-09-07","yyyy-MM-dd")).build());
        assesses.add(Assess.builder().indexId(indexId).score(3).doneDate(DateUtilWrapper.toDate("2018-09-03","yyyy-MM-dd")).build());
        assesses.add(Assess.builder().indexId(indexId).score(4).doneDate(DateUtilWrapper.toDate("2018-09-05","yyyy-MM-dd")).build());
        assesses.add(Assess.builder().indexId(indexId).score(5).doneDate(DateUtilWrapper.toDate("2018-09-02","yyyy-MM-dd")).build());
        assesses.add(Assess.builder().indexId(indexId).score(6).doneDate(DateUtilWrapper.toDate("2018-09-04","yyyy-MM-dd")).build());

        Index index = mock(Index.class);
        when(index.getName()).thenReturn("I1");
        when(index.getScore()).thenReturn(new IndexScore(10,0));
        when(indexRepository.loadOf(any(IndexId.class))).thenReturn(index);
        when(assessRepository.findByAssesseeIdAndDoneDateBetween(any(AssesseeId.class), any(Date.class), any(Date.class))).thenReturn(null).thenReturn(assesses);

        List<AssessData> data = service.getAssessBetween(new AssesseeId().id(),null,null);
        assertEquals(0,data.size());
        data = service.getAssessBetween(new AssesseeId().id(),null,null);
        assertEquals(6,data.size());
        assertEquals(data.get(0).getDoneDate(),assesses.get(1).getDoneDate());
        assertEquals(data.get(3).getDoneDate(),assesses.get(2).getDoneDate());
        assertEquals(data.get(5).getDoneDate(),assesses.get(0).getDoneDate());
        data = service.getAssessBetween(new AssesseeId().id(),DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd"),DateUtilWrapper.toDate("2018-09-10","yyyy-MM-dd"));
        assertEquals(data.get(0).getDoneDate(),assesses.get(1).getDoneDate());
        assertEquals(data.get(3).getDoneDate(),assesses.get(2).getDoneDate());
        assertEquals(data.get(5).getDoneDate(),assesses.get(0).getDoneDate());
    }
}