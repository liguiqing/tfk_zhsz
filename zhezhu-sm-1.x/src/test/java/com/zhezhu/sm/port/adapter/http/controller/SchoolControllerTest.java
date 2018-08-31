package com.zhezhu.sm.port.adapter.http.controller;

import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.school.NewSchoolCommand;
import com.zhezhu.sm.application.school.SchoolApplicationService;
import com.zhezhu.sm.application.school.SchoolData;
import com.zhezhu.sm.application.school.SchoolQueryService;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Copyright (c) 2016,$today.year, Liguiqing
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

    @Mock
    private SchoolQueryService schoolQueryService;

    @Test
    public void onNewSchool() throws Exception{
        assertNotNull(schoolController);

        NewSchoolCommand command = new NewSchoolCommand("zhezhu school","TS",6);
        String content = toJsonString(command);

        doNothing().when(schoolApplicationService).newSchool(any(NewSchoolCommand.class));
        this.mvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }

    @Test
    public void onGetAllSchool() throws Exception{
        assertNotNull(schoolController);
        SchoolData schoolData = new SchoolData(new SchoolId().id(),"name2");
        schoolData.addGradeDatas(new Grade[]{Grade.G1(),Grade.G2(),Grade.G3()});
        List<SchoolData> datas = Arrays.asList(new SchoolData(new SchoolId().id(),"name1"),
                new SchoolData(new SchoolId().id(),"name2"),schoolData);


        when(schoolQueryService.findAllSchool(any(Integer.class),any(Integer.class))).thenReturn(datas);
        this.mvc.perform(get("/school/1/20").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.schools.[0].name", equalTo("name1")))
                .andExpect(jsonPath("$.schools.[2].grads[2].name", equalTo(Grade.G3().getName())));
    }
}