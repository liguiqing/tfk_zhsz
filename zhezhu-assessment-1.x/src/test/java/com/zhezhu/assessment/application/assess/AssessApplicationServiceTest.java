package com.zhezhu.assessment.application.assess;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.collaborator.AssessorRepository;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.medal.AwardRepository;
import com.zhezhu.commons.message.MessageListener;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class AssessApplicationServiceTest {
    @Mock
    private AssessService assesseService;

    @Mock
    private AssessTeamRepository assessTeamRepository;

    @Mock
    private IndexRepository indexRepository;

    @Mock
    private AssessRepository assessRepository;

    @Mock
    private AssessorRepository assessorRepository;

    @Mock
    private AssesseeRepository assesseeRepository;

    @Mock
    private AwardRepository awardRepository;

    @Mock
    private MessageListener messageListener;

    @Mock
    private SchoolService schoolService;

    @Mock
    private RankService rankService;

    @Mock
    private AssessRankRepository rankRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private AssessApplicationService getService()throws Exception{
        AssessApplicationService service = new AssessApplicationService();

        FieldUtils.writeField(service,"indexRepository",indexRepository,true);
        FieldUtils.writeField(service,"assessRepository",assessRepository,true);
        FieldUtils.writeField(service,"assesseService",assesseService,true);
        FieldUtils.writeField(service,"assessorRepository",assessorRepository,true);
        FieldUtils.writeField(service,"assesseeRepository",assesseeRepository,true);
        FieldUtils.writeField(service,"awardRepository",awardRepository,true);
        FieldUtils.writeField(service,"messageListener",messageListener,true);
        FieldUtils.writeField(service,"schoolService",schoolService,true);
        FieldUtils.writeField(service,"assessTeamRepository",assessTeamRepository,true);
        FieldUtils.writeField(service,"rankService",rankService,true);
        FieldUtils.writeField(service,"rankRepository",rankRepository,true);
        return spy(service);
    }

    @Test
    public void genAsseeTeamsOf()throws Exception{
        SchoolId schoolId = new SchoolId();

        SchoolData school = SchoolData.builder().name("S1").schoolId(schoolId.id()).scope(SchoolScope.Primary.name()).build();
        List<ClazzData> clazzData = Lists.newArrayList();
        Date now = DateUtilWrapper.now();
        int year = DateUtilWrapper.year(now);
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G1").clazzName("C1").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G1").clazzName("C2").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G2").clazzName("C3").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G2").clazzName("C4").openedTime(now).build());
        clazzData.add(ClazzData.builder().clazzId(new ClazzId().id()).schoolId(schoolId.id()).gradeName("G3").clazzName("C5").openedTime(now).build());
        AssessTeamId teamId = new AssessTeamId();
        AssessTeam team_  = mock(AssessTeam.class);
        when(team_.getTeamId()).thenReturn(schoolId.id());
        when(team_.getAssessTeamId()).thenReturn(teamId);
        when(schoolService.getClazzs(eq(schoolId))).thenReturn(clazzData);
        when(schoolService.getSchool(any(SchoolId.class))).thenReturn(school);
        when(assessTeamRepository.findByTeamId(any(String.class))).thenReturn(team_).thenReturn(null);

        AssessApplicationService service = getService();
        service.genAssessTeamsOf(schoolId.id());
        verify(team_,times(2)).getAssessTeamId();
    }

    @Test
    public void assess() throws Exception{
        AssessApplicationService service = getService();
        NewAssessCommand command1 = NewAssessCommand.builder()
                .indexId(new IndexId().id())
                .assesseeId(new AssesseeId().id())
                .assessorId(new AssessorId().id())
                .score(5)
                .build();
        Index index = Index.builder().indexId(new IndexId()).build();
        when(indexRepository.loadOf(any(IndexId.class))).thenReturn(index);

        Assessor assessor = Assessor.builder().build();
        when(assessorRepository.loadOf(any(AssessorId.class))).thenReturn(assessor);

        Assessee assessee = Assessee.builder().schoolId(new SchoolId()).personId(new PersonId()).role("Student").build();
        when(assesseeRepository.loadOf(any(AssesseeId.class))).thenReturn(assessee);

        List<Assess> assesses = Lists.newArrayList();
        assesses.add(Assess.builder().indexId(index.getIndexId()).assesseeId(assessee.getAssesseeId()).assessorId(assessor.getAssessorId()).build());
        when(assesseService.newAssesses(eq(index), eq(assessor), eq(assessee), eq(command1.getScore()))).thenReturn(assesses);
        doNothing().when(messageListener).addEvent(any());

        StudentData student = spy(StudentData.builder().personId(new PersonId().id()).build());
        when(student.getManagedClazzId()).thenReturn(new ClazzId().id());
        when(schoolService.getStudentBy(any(PersonId.class))).thenReturn(student);
        AssessTeam team = AssessTeam.builder().assessTeamId(new AssessTeamId()).build();
        when(assessTeamRepository.findByTeamId(any(String.class))).thenReturn(team);

        service.assess(command1);
        verify(assessRepository,times(1)).save(any(Assess.class));
        verify(messageListener,times(1)).addEvent(any());
        verify(student,times(1)).getManagedClazzId();
    }

    @Test
    public void assesses() throws Exception{
        AssessApplicationService service = getService();
        NewAssessCommand command1 = NewAssessCommand.builder()
                .indexId(new IndexId().id())
                .assesseeId(new AssesseeId().id())
                .assessorId(new AssessorId().id())
                .score(5)
                .build();
        doNothing().when(service).assess(any(NewAssessCommand.class));
        service.assesses(null);
        service.assesses(new NewAssessCommand[]{});
        service.assesses(new NewAssessCommand[]{command1});
        service.assesses(new NewAssessCommand[]{command1,command1});

        verify(service,times(3)).assess(any(NewAssessCommand.class));
    }

    @Test
    public void teacherAssessStudent() throws Exception{
        AssessApplicationService service = getService();

        List<IndexAssess> indexAssesses = Lists.newArrayList();
        Index index = mock(Index.class);
        when(index.getCategory())
                .thenReturn(IndexCategory.Esthetics.name())
                .thenReturn(IndexCategory.Sports.name())
                .thenReturn(IndexCategory.Intelligence.name());

        indexAssesses.add(new IndexAssess(new IndexId().id(),6));
        indexAssesses.add(new IndexAssess(new IndexId().id(),5));
        indexAssesses.add(new IndexAssess(new IndexId().id(),4));
        indexAssesses.add(new IndexAssess("亚马爹"));

        NewTeacherAssessStudentCommand command = NewTeacherAssessStudentCommand.builder()
                .schoolId(new SchoolId().id())
                .teacherPersonId(new PersonId().id())
                .studentPersonId(new PersonId().id())
                .assesses(indexAssesses)
                .build();

        String[] ids = service.teacherAssessStudent(new NewTeacherAssessStudentCommand());
        assertEquals(0, ids.length);

        Assessor assessor = Assessor.builder().assessorId(new AssessorId()).build();
        when(assessorRepository.findByPersonIdAndSchoolId(any(PersonId.class),any(SchoolId.class))).thenReturn(assessor).thenReturn(null);

        Assessee assessee = Assessee.builder().schoolId(new SchoolId()).personId(new PersonId()).role("Student").build();

        when(assessorRepository.nextIdentity()).thenReturn(assessor.getAssessorId());

        Assess assess = mock(Assess.class);
        when(assess.getScore()).thenReturn(6d).thenReturn(5d).thenReturn(4d);
        when(assess.getAssessId()).thenReturn(new AssessId()).thenReturn(new AssessId()).thenReturn(new AssessId()).thenReturn(new AssessId());
        when(assesseService.newAssess(any(Index.class),any(Assessor.class),any(Assessee.class),any(Double.class),isNull()))
                .thenReturn(assess).thenReturn(assess).thenReturn(assess);
        when(assesseService.newAssess(isNull(),any(Assessor.class),any(Assessee.class),eq(-1d),eq("亚马爹")))
                .thenReturn(assess);

        doNothing().when(assessorRepository).save(any(Assessor.class));
        doNothing().when(messageListener).addEvent(any());
        doNothing().when(assessRepository).save(any());

        when(assesseeRepository.findByPersonIdAndSchoolId(any(PersonId.class),any(SchoolId.class))).thenReturn(assessee).thenReturn(null);

        when(indexRepository.loadOf(any(IndexId.class)))
                .thenReturn(index)
                .thenReturn(index)
                .thenReturn(index)
                .thenReturn(null);

        ids = service.teacherAssessStudent(command);

        assertEquals(4,ids.length);
        verify(assessorRepository,times(1)).findByPersonIdAndSchoolId(any(PersonId.class),any(SchoolId.class));
        verify(assesseeRepository,times(1)).findByPersonIdAndSchoolId(any(PersonId.class),any(SchoolId.class));
        verify(assessRepository,times(4)).save(any());
        verify(indexRepository,times(3)).loadOf(any(IndexId.class));
        verify(messageListener,times(4)).addEvent(any());
    }

    @Test
    public void rank()throws Exception{
        AssessApplicationService service = getService();
        String teamId = new ClazzId().id();
        PersonId personId = new PersonId();
        List<AssessRank> ranks = Lists.newArrayList();
        ranks.add(AssessRank.builder()
                .assessTeamId(teamId)
                .personId(personId)
                .rankCategory(RankCategory.Weekend)
                .rankScope(RankScope.Clazz)
                .yearStarts(2018)
                .yearEnds(2019)
                .rankNode("36")
                .rankDate(DateUtilWrapper.now())
                .rank(1)
                .promote(1)
                .score(1)
                .promoteScore(1)
                .build());
        ranks.add(AssessRank.builder()
                .assessTeamId(teamId)
                .personId(personId)
                .rankCategory(RankCategory.Weekend)
                .rankScope(RankScope.Clazz)
                .yearStarts(2018)
                .yearEnds(2019)
                .rankNode("36")
                .rankDate(DateUtilWrapper.now())
                .rank(1)
                .promote(1)
                .score(1)
                .promoteScore(1)
                .build());
        when(rankService.rank(any(String.class),any(RankCategory.class),any(RankScope.class))).thenReturn(ranks);
        service.rank(teamId,RankCategory.Weekend.name(),RankScope.Clazz.name());
    }
}