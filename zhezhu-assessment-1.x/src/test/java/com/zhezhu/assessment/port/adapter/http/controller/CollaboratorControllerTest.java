package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.collaborator.CollaboratorApplicationService;
import com.zhezhu.assessment.application.collaborator.CollaboratorData;
import com.zhezhu.assessment.application.collaborator.CollaboratorQueryService;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.StudentData;
import com.zhezhu.share.infrastructure.school.TeacherData;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {CollaboratorController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class CollaboratorControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private CollaboratorController controller;

    @Mock
    private CollaboratorApplicationService collaboratorService;

    @Mock
    private CollaboratorQueryService collaboratorQueryService;

    @Test
    public void onToCollaborator() throws Exception{
        assertNotNull(controller);
        String schoolId = new SchoolId().id();

        doNothing().when(collaboratorService).toCollaborator(any(String.class));
        this.mvc.perform(post("/collaborator/all/"+schoolId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));

    }

    @Test
    public void onStudentToAssessee() throws Exception{
        assertNotNull(controller);
        String schoolId = new SchoolId().id();

        doNothing().when(collaboratorService).studentToAssessee(any(String.class));
        this.mvc.perform(post("/collaborator/assessee/from/student/"+schoolId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }

    @Test
    public void onTeacherToAssessor() throws Exception{
        assertNotNull(controller);
        String schoolId = new SchoolId().id();

        doNothing().when(collaboratorService).teacherToAssessor(any(String.class));
        this.mvc.perform(post("/collaborator/assessor/from/teacher/"+schoolId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }

    @Test
    public void onGetTeacherAsAssessor()throws Exception{
        String schoolId = new SchoolId().id();
        String personId = new PersonId().id();

        CollaboratorData assessor = CollaboratorData.builder()
                .teacher(TeacherData.builder().personId(personId).schoolId(schoolId).name("S1").build())
                .assessorId(new AssessorId().id())
                .build();

        when(collaboratorQueryService.getAssessorBy(eq(schoolId),eq(personId),eq(CollaboratorRole.Teacher))).thenReturn(assessor);

        this.mvc.perform(get("/collaborator/assessor/teacher/"+schoolId+"/"+personId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assessor.assessorId", equalTo(assessor.getAssessorId())))
                .andExpect(jsonPath("$.assessor.teacher.name", equalTo("S1")))
                .andExpect(view().name("/collaborator/assessorInfo"));
    }

    @Test
    public void onGetStudentAsAssessee()throws Exception{
        String schoolId = new SchoolId().id();
        String personId = new PersonId().id();
        CollaboratorData assessee = CollaboratorData.builder()
                .student(StudentData.builder().personId(personId).schoolId(schoolId).name("S1").build())
                .assesseeId(new AssesseeId().id())
                .build();

        when(collaboratorQueryService.getAssesseeBy(eq(schoolId),eq(personId),eq(CollaboratorRole.Student))).thenReturn(assessee);

        this.mvc.perform(get("/collaborator/assessee/student/"+schoolId+"/"+personId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assessee.assesseeId", equalTo(assessee.getAssesseeId())))
                .andExpect(jsonPath("$.assessee.student.name", equalTo("S1")))
                .andExpect(view().name("/collaborator/assesseeInfo"));
    }
}