package com.tfk.assessment.port.adapter.http.controller;

import com.tfk.assessment.application.collaborator.CollaboratorService;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.test.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    private CollaboratorService collaboratorService;

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
}