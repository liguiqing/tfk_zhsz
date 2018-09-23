package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.user.NewUserCommand;
import com.zhezhu.access.application.user.UpdatePasswordCommand;
import com.zhezhu.access.application.user.UserApplicationService;
import com.zhezhu.share.domain.id.UserId;
import com.zhezhu.zhezhu.controller.StandaloneControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/


public class ApplicationUserControllerTest extends StandaloneControllerTest {


    @Mock
    private UserApplicationService userApplicationService;

    @Before
    public void before(){
        super.before();
        ApplicationUserController controller =  new ApplicationUserController(this.userApplicationService);
        applyController(controller);
    }

    @Test
    public void onNewUser() throws Exception{
        NewUserCommand command = NewUserCommand.builder().userName("admin").password("123456").build();
        UserId userId = new UserId();
        when(userApplicationService.createUser(any(NewUserCommand.class))).thenReturn(userId.id());

        String content = toJsonString(command);

        this.mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.userId", equalTo(userId.id())))
                .andExpect(view().name("/user/userList"));
    }

    @Test
    public void onUpdateUserPassword() throws Exception{
        UserId userId = new UserId();
        UpdatePasswordCommand command = UpdatePasswordCommand.builder().userId(userId.id()).oldPassword("123456").newPassword("111111").build();
        doNothing().when(userApplicationService).updatePassword(any(UpdatePasswordCommand.class));

        String content = toJsonString(command);

        this.mvc.perform(put("/user/password").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.userId", equalTo(userId.id())))
                .andExpect(view().name("/user/userDetail"));
    }
}