package com.zhezhu.boot.port.adapter.http.controller;

import com.zhezhu.boot.infrastructure.init.DbInitService;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {MainController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class MainControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private MainController controller;

    @Mock
    private DbInitService dbInitService;

    @Test
    public void onIndex() throws Exception{
        assertNotNull(controller);

        this.mvc.perform(post("/index").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("/index"))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));

        this.mvc.perform(get("/index")
                .param("token", ""+ UUID.randomUUID()))
                .andExpect(view().name("/index"))
                .andExpect(content().string(startsWith("<!DOCTYPE html>")))
                .andExpect(content().string(containsString("</html>")))
                .andExpect(content().string(endsWith("</html>")));
    }
}