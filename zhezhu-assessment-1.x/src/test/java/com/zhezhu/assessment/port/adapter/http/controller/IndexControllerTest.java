package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.index.IndexApplicationService;
import com.zhezhu.assessment.application.index.NewIndexCommand;
import com.zhezhu.assessment.application.index.UpdateIndexCommand;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.index.IndexId;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {IndexController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class IndexControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private IndexController controller;

    @Mock
    private IndexApplicationService indexApplicationService;

    @Test
    public void onNewIndex() throws Exception{
        NewIndexCommand command = NewIndexCommand.builder()
                .categoryName("Morals")
                .description("Desc")
                .name("name")
                .score(10.0d)
                .weight(0.5d)
                .build();
        String content = toJsonString(command);
        IndexId indexId = new IndexId();
        when(indexApplicationService.newStIndex(any(NewIndexCommand.class))).thenReturn(indexId.getId());
        this.mvc.perform(post("/index/common").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.indexId", equalTo(indexId.getId())));
    }

    @Test
    public void onNewTenantIndex() throws Exception{
        NewIndexCommand command = NewIndexCommand.builder()
                .categoryName("Morals")
                .description("Desc")
                .name("name")
                .score(10.0d)
                .ownerId(new TenantId().id())
                .weight(0.5d)
                .build();
        String content = toJsonString(command);
        IndexId indexId = new IndexId();
        when(indexApplicationService.newTenantIndex(any(NewIndexCommand.class))).thenReturn(indexId.getId());
        this.mvc.perform(post("/index/tenant").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.indexId", equalTo(indexId.getId())));

    }

    @Test
    public void onUpdateIndex() throws Exception {
        NewIndexCommand command = NewIndexCommand.builder()
                .categoryName("Morals")
                .description("Desc")
                .name("name")
                .score(10.0d)
                .ownerId(new TenantId().id())
                .weight(0.5d)
                .build();
        IndexId stIndexId1 = new IndexId();
        String content = toJsonString(new UpdateIndexCommand(stIndexId1.id()).build(command));
        doNothing().when(indexApplicationService).updateIndex(any(UpdateIndexCommand.class));
        this.mvc.perform(put("/index").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.indexId", equalTo(stIndexId1.getId())));


    }
}