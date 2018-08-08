package com.tfk.sm.port.adapter.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfk.sm.application.school.NewSchoolCommand;
import com.tfk.sm.application.school.SchoolApplicationService;
import com.tfk.test.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {SchoolController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class SchoolControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    SchoolController  schoolController;

    @Mock
    SchoolApplicationService schoolApplicationService;

    @Test
    public void onNewSchool() throws Exception{
        assertNotNull(schoolController);

        NewSchoolCommand command = new NewSchoolCommand("test school","TS",6);
        String content = toJsonString(command);

        doNothing().when(schoolApplicationService).newSchool(any(NewSchoolCommand.class));
        this.mvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }
}