package com.zhezhu.assessment.application.collaborator;

import com.zhezhu.assessment.domain.model.collaborator.*;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class CollaboratorQueryServiceTest {
    @Mock
    private SchoolService schoolService;

    @Mock
    private AssesseeRepository assesseeRepository;

    @Mock
    private AssessorRepository assessorRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    private CollaboratorQueryService getService()throws Exception{
        CollaboratorQueryService service = new CollaboratorQueryService();
        FieldUtils.writeField(service,"schoolService",schoolService,true);
        FieldUtils.writeField(service,"assesseeRepository",assesseeRepository,true);
        FieldUtils.writeField(service,"assessorRepository",assessorRepository,true);
        return spy(service);
    }

    @Test
    public void getAssessorBy() throws Exception{
        PersonId personId = new PersonId();
        SchoolId schoolId = new SchoolId();
        Assessor assessor = mock(Assessor.class);
        AssessorId assessorId = new AssessorId();

        Collaborator collaborator = mock(Collaborator.class);
        when(assessor.getCollaborator()).thenReturn(collaborator);
        when(assessor.getAssessorId()).thenReturn(assessorId);
        when(collaborator.getPersonId()).thenReturn(personId);
        when(collaborator.getSchoolId()).thenReturn(schoolId);
        when(assessorRepository.findByPersonIdAndSchoolId(any(PersonId.class),any(SchoolId.class))).thenReturn(assessor);

        CollaboratorRole role = CollaboratorRole.Teacher;
        CollaboratorQueryService service = getService();
        CollaboratorData assessorData = service.getAssessorBy(schoolId.id(), personId.id(),role);
    }
}