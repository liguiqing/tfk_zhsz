package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.domain.Identities;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.IdPrefixes;
import com.tfk.share.domain.id.SubjectId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.person.Gender;
import com.tfk.sm.application.data.Contacts;
import com.tfk.sm.application.data.CourseData;
import com.tfk.sm.application.teacher.ArrangeTeacherCommand;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
                "Test Teacher",null,Gender.Male,contacts);
        String content = toJsonString(command);
        String teacherId = new TeacherId().id();
        when(teacherApplicationService.newTeacher(any(NewTeacherCommand.class))).thenReturn(teacherId);

        this.mvc.perform(post("/teacher").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.teacherId", equalTo(teacherId)));

        verify(teacherApplicationService,times(1)).newTeacher(any(NewTeacherCommand.class));
    }

    @Test
    public void onArrangeTeacher()throws Exception{

        String teacherId = Identities.genIdNone(IdPrefixes.TeacherIdPrefix);

        SubjectId subjectId1 = new SubjectId();
        SubjectId subjectId2 = new SubjectId();
        SubjectId subjectId3 = new SubjectId();

        CourseData[] courses = new CourseData[3];
        courses[0] = new CourseData("语文",subjectId1.id());
        courses[1] = new CourseData("数学",subjectId2.id());
        courses[2] = new CourseData("英语",subjectId3.id());

        String clazzId1 = Identities.genIdNone(IdPrefixes.ClazzIdPrefix);
        String clazzId2 = Identities.genIdNone(IdPrefixes.ClazzIdPrefix);
        String[] clazzIds = new String[]{clazzId1,clazzId2};

        ArrangeTeacherCommand command = new ArrangeTeacherCommand(teacherId,courses,clazzIds,clazzIds);
        command.setDateStarts(DateUtilWrapper.now());
        command.setDateEnds(DateUtilWrapper.now());
        String content = toJsonString(command);

        doNothing().when(teacherApplicationService).arranging(any(ArrangeTeacherCommand.class));

        this.mvc.perform(post("/teacher/arrange").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.teacherId", equalTo(teacherId)));

        verify(teacherApplicationService,times(1)).arranging(any(ArrangeTeacherCommand.class));
    }
}