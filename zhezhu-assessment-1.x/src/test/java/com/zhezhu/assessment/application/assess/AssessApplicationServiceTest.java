package com.zhezhu.assessment.application.assess;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.Assess;
import com.zhezhu.assessment.domain.model.assesse.AssessRepository;
import com.zhezhu.assessment.domain.model.assesse.AssessService;
import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.AssesseeRepository;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.collaborator.AssessorRepository;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.medal.AwardRepository;
import com.zhezhu.commons.message.MessageListener;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.person.Person;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class AssessApplicationServiceTest {
    @Mock
    private AssessService assesseService;

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
        return spy(service);
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

        service.assess(command1);
        verify(assessRepository,times(1)).save(any(Assess.class));
        verify(messageListener,times(1)).addEvent(any());
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
}