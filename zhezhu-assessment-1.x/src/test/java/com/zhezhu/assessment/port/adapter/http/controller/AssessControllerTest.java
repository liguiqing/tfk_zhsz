package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.assess.AssessApplicationService;
import com.zhezhu.assessment.application.assess.NewAssessCommand;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {AssessController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class AssessControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private AssessController controller;

    @Mock
    private AssessApplicationService assessApplicationService;

    @Test
    public void onAssess() throws Exception{
        assertNotNull(controller);
        AssesseeId assesseeId = new AssesseeId();
        AssessorId assessorId = new AssessorId();
        IndexId indexId = new IndexId();
        NewAssessCommand command  = NewAssessCommand.builder()
                .assesseeId(assesseeId.id())
                .assessorId(assessorId.id())
                .indexId(indexId.id())
                .score(10.0d)
                .word("亚马爹")
                .build();

        String content = toJsonString(command);

        doNothing().when(assessApplicationService).assess(any(NewAssessCommand.class));

        this.mvc.perform(post("/assess").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
    }
}