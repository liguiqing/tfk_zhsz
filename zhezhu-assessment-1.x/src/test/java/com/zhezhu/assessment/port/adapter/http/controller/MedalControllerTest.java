package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.medal.MedalApplicationService;
import com.zhezhu.assessment.application.medal.NewMedalCommand;
import com.zhezhu.assessment.application.medal.UpdateMedalCommand;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.medal.MedalId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {MedalController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class MedalControllerTest  extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private MedalController controller;

    @Mock
    private MedalApplicationService medalApplicationService;

    @Test
    public void onNewMedal() throws Exception{
        assertNotNull(controller);
        SchoolId schoolId = new SchoolId();
        MedalId medalId = new MedalId();
        NewMedalCommand command = NewMedalCommand.builder()
                .name("阳光少年")
                .schoolId(schoolId.id())
                .category("B")
                .indexIds(new String[]{new IndexId().id(),new IndexId().id(),new IndexId().id()})
                .level(1).build();

        String content = toJsonString(command);
        when(medalApplicationService.newMedal(any(NewMedalCommand.class))).thenReturn(medalId.id());

        this.mvc.perform(post("/medal").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.medalId", equalTo(medalId.getId())));
    }

    @Test
    public void onUpdateMedal() throws Exception{
        assertNotNull(controller);
        MedalId medalId = new MedalId();
        UpdateMedalCommand command = UpdateMedalCommand.builder()
                .medalId(medalId.id())
                .name("月光少年")
                .indexIds(new String[]{new IndexId().id(),new IndexId().id(),new IndexId().id()})
                .build();
        String content = toJsonString(command);
        doNothing().when(medalApplicationService).updateMedal(any(UpdateMedalCommand.class));

        this.mvc.perform(put("/medal").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }

    @Test
    public void onDeleteMedal() throws Exception{
        assertNotNull(controller);
        String medalId = new MedalId().id();
        doNothing().when(medalApplicationService).deleteMedal(any(String.class));
        this.mvc.perform(delete("/medal/"+medalId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }
}