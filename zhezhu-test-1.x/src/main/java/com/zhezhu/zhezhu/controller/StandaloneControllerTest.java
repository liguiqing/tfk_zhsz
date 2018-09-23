package com.zhezhu.zhezhu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;

/**
 * 不依赖spring环境的ControllerTest
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class StandaloneControllerTest {

    protected MockMvc mvc;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    protected void applyController(Object... controller){
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.setDefaultContentType(MediaType.APPLICATION_JSON);
        contentNegotiationManager.addMediaType("html", MediaType.TEXT_HTML);
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
        contentNegotiationManager.build();
        StandaloneMockMvcBuilder mvcBuilder =  MockMvcBuilders.standaloneSetup(controller);
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        viewResolver.setDefaultViews(Arrays.asList(new MappingJackson2JsonView()));
        mvcBuilder.setViewResolvers(viewResolver);
        mvcBuilder.setContentNegotiationManager(contentNegotiationManager.getObject());
        this.mvc = mvcBuilder.build();
    }

    protected String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }
}