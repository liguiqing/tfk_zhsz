package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.wechat.WeChatApplicationService;
import com.zhezhu.access.application.wechat.WeChatData;
import com.zhezhu.access.application.wechat.WechatQueryService;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {WeChatController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class WeChatControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private  WeChatController controller;

    @Mock
    private WeChatApplicationService wechatApplicationService;

    @Mock
    private WechatQueryService wechatQueryService;

    @Test
    public void onGetJoined()throws Exception{

        ArrayList<WeChatData> data = new ArrayList<>();
        data.add(WeChatData.builder().name("Name").build());
        data.add(WeChatData.builder().role(WeChatCategory.Teacher.name()).build());
        when(wechatQueryService.getWeChats(any(String.class))).thenReturn(data);


        this.mvc.perform(get("/wechat/join/"+new PersonId().id()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.weChats[0].name", equalTo("Name")))
                .andExpect(jsonPath("$.weChats[1].role", equalTo(WeChatCategory.Teacher.name())))
                .andExpect(view().name("/wechat/wechatList"));
    }

    @Test
    public void onFollow() {
    }

    @Test
    public void onDialog() {
    }

    @Test
    public void onOauth2() {
    }

    @Test
    public void onBind() {
    }

    @Test
    public void onFollower() {
    }

    @Test
    public void onCancelFollower() {
    }
}