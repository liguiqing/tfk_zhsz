package com.zhezhu.access.application.user;

import com.zhezhu.access.domain.model.user.PasswordService;
import com.zhezhu.access.domain.model.user.User;
import com.zhezhu.access.domain.model.user.UserRepository;
import com.zhezhu.share.domain.id.UserId;
import com.zhezhu.share.infrastructure.security.MD5PasswordEncoder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class UserApplicationServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private UserRepository userRepository;


    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private UserApplicationService getService(){

        UserApplicationService service = new UserApplicationService(getPasswordService(),userRepository);
        return service;
    }

    private PasswordService getPasswordService(){
        MD5PasswordEncoder passwordEncoder  = new MD5PasswordEncoder();
        PasswordService passwordService = new PasswordService(passwordEncoder,4,10,"123456");
        return passwordService;
    }

    @Test
    public void createUser() {
        UserApplicationService service = getService();
        UserId userId = new UserId();
        when(userRepository.nextIdentity()).thenReturn(userId);
        doNothing().when(userRepository).save(any(User.class));
        NewUserCommand command = NewUserCommand.builder().userName("userName").password("123456").build();
        String uid = service.createUser(command);
        assertEquals(userId.id(),uid);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ac-01-010");
        service.createUser(NewUserCommand.builder().userName("userName").password("1").build());
    }

    @Test
    public void updatePassword(){
        UserApplicationService service = getService();
        PasswordService passwordService = getPasswordService();
        UserId userId = new UserId();
        String password = passwordService.password("123456",userId.id());
        User user = User.builder().password(password).userId(userId).build();
        when(userRepository.loadOf(eq(userId))).thenReturn(user);
        UpdatePasswordCommand command = UpdatePasswordCommand.builder()
                .userId(userId.id())
                .newPassword("12345678")
                .oldPassword("123456")
                .build();
        service.updatePassword(command);
        String password_ = passwordService.password("12345678",userId.id());
        assertEquals(password_,user.getPassword());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ac-01-011");
        command = UpdatePasswordCommand.builder()
                .userId(userId.id())
                .newPassword("12345678")
                .oldPassword("1234561")
                .build();
        service.updatePassword(command);
    }
}