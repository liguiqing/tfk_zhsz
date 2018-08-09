package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;
import com.tfk.sm.application.student.NewStudentCommand;
import com.tfk.sm.application.student.StudentApplicationService;
import com.tfk.test.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
@ContextHierarchy({
        @ContextConfiguration(classes= {StudentController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class StudentControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private StudentController controller;

    @Mock
    private StudentApplicationService studentApplicationService;

    @Test
    public void onNewStudent() throws Exception{
        assertNotNull(controller);
        String studentId = Identities.genIdNone(IdPrefixes.StudentIdPrefix);
        NewStudentCommand command  = new NewStudentCommand();
        String content = toJsonString(command);
        when(studentApplicationService.newStudent(ArgumentMatchers.any(NewStudentCommand.class))).thenReturn(studentId);

        this.mvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.studentId", equalTo(studentId)));
    }
}