package com.zhezhu.sm.port.adapter.http.controller;

import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.clazz.ClazzApplicationService;
import com.zhezhu.sm.application.clazz.ClazzQueryService;
import com.zhezhu.sm.application.clazz.NewClazzCommand;
import com.zhezhu.sm.application.data.ClazzData;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Copyright (c) 2016,2018, Liguiqing
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {ClazzController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class ClazzControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private ClazzController controller;

    @Mock
    private ClazzApplicationService clazzApplicationService;

    @Mock
    private ClazzQueryService clazzQueryService;

    @Test
    public void onNewClazz() throws Exception{
        assertNotNull(controller);

        Date openedTime = DateUtilWrapper.now();
        int yearStarts = DateUtilWrapper.thisYear();
        int yearEnds = DateUtilWrapper.nextYear(openedTime);
        int gradeLevel = Grade.G1().getLevel();
        SchoolId schoolId = new SchoolId();
        NewClazzCommand command = new NewClazzCommand(schoolId.id(), openedTime , "Test Clazz",
                yearStarts, yearEnds,gradeLevel);
        String content = toJsonString(command);

        when(clazzApplicationService.newClazz(any(NewClazzCommand.class))).thenReturn(schoolId.getId());
        this.mvc.perform(post("/clazz").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", CoreMatchers.is(Boolean.TRUE)));
    }

    @Test
    public void onGetSchoolClazz()throws Exception{
        SchoolId schoolId = new SchoolId();
        Grade grade = Grade.G1();
        List<ClazzData> datas = Arrays.asList(ClazzData.builder().name("c1").build(),ClazzData.builder().name("c2").build());

        when(clazzQueryService.findSchoolGradeClazzesCanBeManagedOfNow(any(String.class),any(Integer.class))).thenReturn(datas);

        this.mvc.perform(get("/clazz/grade/"+schoolId.id()+"/"+grade.getLevel()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.clazzes.[0].name", equalTo("c1")))
                .andExpect(jsonPath("$.clazzes.[1].name", equalTo("c2")));
    }
}