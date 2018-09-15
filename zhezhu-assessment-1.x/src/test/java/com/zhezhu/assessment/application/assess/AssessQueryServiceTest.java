package com.zhezhu.assessment.application.assess;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.index.IndexScore;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Term;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
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

    @Mock
    private AssesseeRepository assesseeRepository;

    @Mock
    private RankCategoryService rankCategoryService;


    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    private AssessQueryService getService(){
        AssessQueryService service = new AssessQueryService(indexRepository,assesseeRepository,assessRepository,rankRepository,schoolService);
        return spy(service);
    }

    private List<AssessRank> getAssessRanks(RankCategory category,RankScope scope,
                                    PersonId personId,String assessTeamId,
                                    AssesseeId assesseeId,int size){

        List<AssessRank> ranks = Lists.newArrayList();
        Period term = Term.defaultPeriodOfThisTerm();
        LocalDate day = LocalDate.now().minusDays(size+1);
        for(int i=1;i<=size;i++){
            AssessRank rank = AssessRank.builder()
                    .assessTeamId(assessTeamId)
                    .assesseeId(assesseeId)
                    .personId(personId)
                    .rankCategory(category)
                    .rankScope(scope)
                    .rank(i)
                    .yearStarts(term.yearStarts())
                    .yearEnds(term.yearEnds())
                    .rankDate(DateUtilWrapper.fromLocalDate(day.plusDays(i)))
                    .promote(i)
                    .rankNode(rankCategoryService.node(category))
                    .promoteScore(i)
                    .score(i)
                    .build();
            ranks.add(rank);
        }
        return ranks;
    }

    @Test
    public void getTeamRanks(){
        RankCategory category = RankCategory.Day;
        RankScope scope = RankScope.Clazz;
        PersonId personId = new PersonId();
        AssesseeId assesseeId = new AssesseeId();
        ClazzId teamId = new ClazzId();
        LocalDate now = LocalDate.now();
        when(rankCategoryService.node(eq(category)))
                .thenReturn(now.minusDays(1).toString())
                .thenReturn(now.minusDays(2).toString())
                .thenReturn(now.minusDays(3).toString())
                .thenReturn(now.minusDays(4).toString())
                .thenReturn(now.minusDays(5).toString())
                .thenReturn(now.minusDays(6).toString())
                .thenReturn(now.minusDays(7).toString())
                .thenReturn(now.minusDays(8).toString())
                .thenReturn(now.minusDays(9).toString())
                .thenReturn(now.minusDays(10).toString());

        StudentData student = mock(StudentData.class);
        when(student.getSchoolId()).thenReturn(new SchoolId().id());
        when(student.getManagedClazzId()).thenReturn(teamId.id());
        when(student.getName()).thenReturn("S1");
        when(schoolService.getStudentBy(any(PersonId.class))).thenReturn(student);

        List<AssessRank> ranks = getAssessRanks(category,scope,personId,teamId.id(),assesseeId,10);
        AssessQueryService service = getService();
        Date from = DateUtilWrapper.fromLocalDate(now.minusDays(11));
        Date to = DateUtilWrapper.now();
        when(rankRepository.findAllByAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndRankDateBetween(
                eq(teamId.id()),eq(category),eq(scope),eq(now.minusDays(1).toString()),eq(from),eq(to))).thenReturn(ranks);
        List<SchoolAssessRankData> rankData = service.getTeamRanks(teamId.id(), category, scope, now.minusDays(1).toString(), from, to);
        assertEquals(ranks.size(),rankData.size());
        assertEquals(ranks.get(0).getRankDate(), rankData.get(9).getRankDate());
        assertEquals(ranks.get(4).getRankDate(), rankData.get(5).getRankDate());
        assertEquals(ranks.get(9).getRankDate(), rankData.get(0).getRankDate());

        when(rankRepository.findByAssessTeamIdAndRankCategoryAndRankScopeAndRankNode(
                eq(teamId.id()), eq(category), eq(scope), eq(now.minusDays(1).toString()))).thenReturn(null).thenReturn(ranks.get(5));


        rankData = service.getTeamLastRanks(teamId.id(), category, scope, now.minusDays(5).toString());
        assertEquals(0,rankData.size());

    }

    @Test
    public void getPersonalRanks(){
        RankCategory category = RankCategory.Day;
        RankScope scope = RankScope.Clazz;
        PersonId personId = new PersonId();
        AssesseeId assesseeId = new AssesseeId();
        ClazzId teamId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        LocalDate now = LocalDate.now();
        when(rankCategoryService.node(eq(category)))
                .thenReturn(now.minusDays(1).toString())
                .thenReturn(now.minusDays(2).toString())
                .thenReturn(now.minusDays(3).toString())
                .thenReturn(now.minusDays(4).toString())
                .thenReturn(now.minusDays(5).toString())
                .thenReturn(now.minusDays(6).toString())
                .thenReturn(now.minusDays(7).toString())
                .thenReturn(now.minusDays(8).toString())
                .thenReturn(now.minusDays(9).toString())
                .thenReturn(now.minusDays(10).toString());
        List<AssessRank> ranks = getAssessRanks(category,scope,personId,teamId.id(),assesseeId,10);
        AssessQueryService service = getService();
        Date from = DateUtilWrapper.fromLocalDate(now.minusDays(11));
        Date to = DateUtilWrapper.now();
        StudentData student = mock(StudentData.class);
        when(student.getSchoolId()).thenReturn(schoolId.id());
        when(student.getManagedClazzId()).thenReturn(teamId.id());
        when(student.getName()).thenReturn("S1");
        when(schoolService.getStudentBy(eq(personId))).thenReturn(student);
        when(rankRepository.findAllByAssessTeamIdAndPersonIdAndRankCategoryAndRankScopeAndRankDateBetween(
                eq(teamId.id()),eq(personId),eq(category),eq(scope),eq(from),eq(to))
        ).thenReturn(ranks);

        List<SchoolAssessRankData> rankData = service.getPersonalRanks(teamId.id(),personId.id(),category,scope,from,to);
        assertEquals(ranks.size(),rankData.size());
        assertEquals(ranks.get(0).getRankDate(), rankData.get(9).getRankDate());
        assertEquals(ranks.get(4).getRankDate(), rankData.get(5).getRankDate());
        assertEquals(ranks.get(9).getRankDate(), rankData.get(0).getRankDate());
    }

    @Test
    public void getPersonalLastRanksThisYear(){
        PersonId personId = new PersonId();
        ClazzId teamId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        AssesseeId assesseeId = new AssesseeId();
        LocalDate day = LocalDate.now().minusDays(11);
        List<Assess> lastAssessList = Lists.newArrayList();
        for(int i=1;i<=10;i++){
            Date doneDay = DateUtilWrapper.fromLocalDate(day.plusDays(i));
            lastAssessList.add(Assess.builder().doneDate(doneDay).build());
        }
        AssessRank lastRank = AssessRank.builder()
                .rankCategory(RankCategory.Day)
                .rankScope(RankScope.Clazz)
                .rank(2)
                .rankDate(DateUtilWrapper.fromLocalDate(day.plusDays(10)))
                .promote(3)
                .rankNode(day.plusDays(10).toString())
                .promoteScore(8)
                .score(81)
                .build();

        StudentData student = mock(StudentData.class);
        when(student.getSchoolId()).thenReturn(schoolId.id());
        when(student.getManagedClazzId()).thenReturn(teamId.id());
        when(student.getPersonId()).thenReturn(personId.id());
        when(student.getName()).thenReturn("S1");
        when(schoolService.getStudentBy(eq(personId))).thenReturn(student);

        Assessee assessee = mock(Assessee.class);
        when(assessee.getAssesseeId()).thenReturn(assesseeId);
        when(assesseeRepository.findByPersonIdAndSchoolId(eq(personId), eq(schoolId))).thenReturn(null).thenReturn(assessee);
        when(assessRepository.findByAssesseeIdAndDoneDateBetween(eq(assesseeId),any(Date.class),any(Date.class))).thenReturn(null).thenReturn(lastAssessList);
        when(rankRepository.findByPersonIdAndRankNodeAndRankCategoryAndRankScopeAndYearStartsAndYearEnds(
                eq(personId),any(String.class),eq(RankCategory.Day),eq(RankScope.Clazz),any(Integer.class),any(Integer.class)
        )).thenReturn(lastRank);
        AssessQueryService service = getService();
        SchoolAssessRankData rankData = service.getPersonalLastRanksThisYear(personId.id());
        assertNull(rankData);
        rankData = service.getPersonalLastRanksThisYear(personId.id());
        assertNull(rankData);
        rankData = service.getPersonalLastRanksThisYear(personId.id());
        assertNotNull(rankData);
        assertEquals(personId.id(),rankData.getPersonId());
        assertEquals(lastRank.getRankDate(),rankData.getRankDate());
        assertEquals(0,lastRank.getPromoteScore(),rankData.getPromoteScore());
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
        when(student.getName()).thenReturn("S1");
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
    public void getAssessBetween() {
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