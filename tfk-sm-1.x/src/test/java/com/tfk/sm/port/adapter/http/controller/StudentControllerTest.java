package com.tfk.sm.port.adapter.http.controller;

import com.google.common.collect.Lists;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.sm.application.data.StudentData;
import com.tfk.sm.application.student.*;
import com.tfk.test.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

    @Mock
    private StudentQueryService studentQueryService;

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
        verify(studentApplicationService,times(1)).newStudent(ArgumentMatchers.any(NewStudentCommand.class));
    }

    @Test
    public void onArrangeStudent() throws Exception{
        assertNotNull(controller);
        String studentId = Identities.genIdNone(IdPrefixes.StudentIdPrefix);
        ArrangeStudentCommand command  = new ArrangeStudentCommand();
        command.setStudentId(studentId);
        String content = toJsonString(command);
        doNothing().when(studentApplicationService).arrangingClazz(ArgumentMatchers.any(ArrangeStudentCommand.class));

        this.mvc.perform(post("/student/arrange").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.studentId", equalTo(studentId)))
                .andExpect(view().name("/student/arrangeStudentSuccess"));

        verify(studentApplicationService,times(1)).arrangingClazz(ArgumentMatchers.any(ArrangeStudentCommand.class));
    }

    @Test
    public void onGetClazzStudent()throws Exception{
        String clazzId = new ClazzId(Identities.genIdNone(IdPrefixes.ClazzIdPrefix)).getId();
        String schoolId = new SchoolId(Identities.genIdNone(IdPrefixes.SchoolIdPrefix)).getId();
        ArrayList<StudentData> data = Lists.newArrayList();
        for(int i = 0;i<5;i++){
            data.add(StudentData.builder().schoolId(schoolId).clazzId(clazzId).name("S" + i).build());
        }
        when(studentQueryService.findStudentInOfNow(eq(schoolId), eq(clazzId))).thenReturn(data);
        this.mvc.perform(get("/student/list/clazz/"+schoolId+"/"+clazzId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.students[0].name", equalTo("S0")))
                .andExpect(jsonPath("$.students[2].clazzId", equalTo(clazzId)))
                .andExpect(jsonPath("$.students[4].schoolId", equalTo(schoolId)))
                .andExpect(view().name("/student/clazzStudentList"));
    }

    @Test
    public void onGetClazzStudentSorted()throws Exception{
        String clazzId = new ClazzId(Identities.genIdNone(IdPrefixes.ClazzIdPrefix)).getId();
        String schoolId = new SchoolId(Identities.genIdNone(IdPrefixes.SchoolIdPrefix)).getId();
        StudentQueryServiceTest studentQueryServiceTest = new StudentQueryServiceTest();
        List<StudentData> data =  studentQueryServiceTest.students();

        when(studentQueryService.findStudentInOfNowAndSortByName(eq(schoolId), eq(clazzId))).thenCallRealMethod();
        doReturn(data).when(studentQueryService).findStudentInOfNow(any(String.class), any(String.class));

        this.mvc.perform(get("/student/list/clazz/nameSorted/"+schoolId+"/"+clazzId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.students[0].letter", equalTo("C")))
                .andExpect(jsonPath("$.students[4].letter", equalTo("W")))
                .andExpect(jsonPath("$.students[5].letter", equalTo("Z")))
                .andExpect(view().name("/student/clazzStudentListSorted"));
    }
}