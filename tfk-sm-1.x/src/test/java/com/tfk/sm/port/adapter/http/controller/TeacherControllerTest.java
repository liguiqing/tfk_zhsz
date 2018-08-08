package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.person.Gender;
import com.tfk.sm.application.data.Contacts;
import com.tfk.sm.application.teacher.NewTeacherCommand;
import com.tfk.sm.application.teacher.TeacherApplicationService;
import com.tfk.test.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {TeacherController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class TeacherControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    TeacherController controller;

    @Mock
    private TeacherApplicationService teacherApplicationService;

    @Test
    public void onNewTeacher() throws Exception{
        assertNotNull(controller);

        Date joinDate = DateUtilWrapper.now();
        Contacts[] contacts = new Contacts[5];
        contacts[0] = new Contacts("QQ", "123456");
        contacts[2] = new Contacts("Email", "123456@aa.com");
        contacts[3] = new Contacts("Mobile", "85623321");
        contacts[4] = new Contacts("QQ", "1234567");
        contacts[1] = new Contacts("Weixin", "123456@aa.com");

        NewTeacherCommand command = new NewTeacherCommand("test school",joinDate,null,
                "Test Teacher",null,Gender.Male,null);
        String content = toJsonString(command);

        doNothing().when(teacherApplicationService).newTeacher(any(NewTeacherCommand.class));
        this.mvc.perform(post("/teacher").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }
}