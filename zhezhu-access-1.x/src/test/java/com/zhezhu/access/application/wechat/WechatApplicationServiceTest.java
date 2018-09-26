package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.*;
import com.zhezhu.access.domain.model.wechat.audit.*;
import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.message.MessageHandler;
import com.zhezhu.access.infrastructure.PersonService;
import com.zhezhu.access.infrastructure.WeChatMessageService;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class WechatApplicationServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private MessageHandler messageHandler;

    @Mock
    private WeChatConfig weChatConfig;

    @Mock
    private WebAccessTokenFactory webAccessTokenFactory;

    @Mock
    private WeChatRepository weChatRepository;

    @Mock
    private FollowApplyRepository applyRepository;

    @Mock
    private FollowAuditRepository auditRepository;

    @Mock
    private ApplyAuditService applyAuditService;

    @Mock
    private WeChatMessageService messageService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private WeChatApplicationService getService(){
        WeChatApplicationService service = new WeChatApplicationService(messageHandler,weChatConfig,
                webAccessTokenFactory,weChatRepository,
                applyRepository,auditRepository,
                applyAuditService,messageService);
        return spy(service);
    }

    @Test
    public void follow(){
        WeChatApplicationService service = getService();

        HashMap<String, String> params = new HashMap<>();
        params.put("signature", "signature");
        params.put("timestamp", "timestamp");
        params.put("nonce", "nonce");
        params.put("echostr", "echostr");
        String content = service.follow(params);
        assertNotNull(content);
    }

    @Test
    public void dialog() {
    }

    @Test
    public void bind(){
        WeChatApplicationService service = getService();

        WeChatCategory[] categories = WeChatCategory.values();
        for(WeChatCategory chatCategory:categories){
            BindCommand command = BindCommand.builder()
                    .category(chatCategory.name())
                    .name("Name")
                    .phone("12345678")
                    .wechatOpenId(""+ UUID.randomUUID().toString())
                    .build();

            WeChatId weChatId = new WeChatId();
            PersonId personId = new PersonId();
            when(weChatRepository.nextIdentity()).thenReturn(weChatId);
            doNothing().when(weChatRepository).save(any(WeChat.class));

            String weId = service.bind(command);

            assertEquals(weChatId.id(),weId);
        }
        verify(weChatRepository,times(3)).nextIdentity();
        verify(weChatRepository,times(3)).save(any(WeChat.class));
    }

    @Test
    public void transferTo(){
        WeChatApplicationService service = getService();
        WeChat weChat = WeChat.builder()
                .category(WeChatCategory.Teacher)
                .weChatId(new WeChatId())
                .name("N1")
                .weChatOpenId(UUID.randomUUID().toString())
                .phone("12345678")
                .bindDate(DateUtilWrapper.now())
                .build();
        PersonId personId1 = new PersonId();
        PersonId personId2 = new PersonId();
        weChat.addFollower(personId1, DateUtilWrapper.now());
        weChat.addFollower(personId2, DateUtilWrapper.now());

        when(weChatRepository.loadOf(any(WeChatId.class))).thenReturn(weChat).thenReturn(weChat).thenReturn(null);
        doNothing().when(weChatRepository).save(any(WeChat.class));

        String weChatId1 = service.transferTo(weChat.getWeChatId().id(), WeChatCategory.Parent, false);
        assertFalse(weChatId1.equals(weChat.getWeChatId().toString()));
        String weChatId2 = service.transferTo(weChat.getWeChatId().id(), WeChatCategory.Parent, true);
        assertFalse(weChatId2.equals(weChat.getWeChatId().toString()));
        assertFalse(weChatId1.equals(weChatId2));

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("ac-01-000");
        service.transferTo(weChat.getWeChatId().id(), WeChatCategory.Parent, true);
    }

    @Test
    public void copyFollowers(){
        WeChat weChat = WeChat.builder()
                .category(WeChatCategory.Teacher)
                .weChatId(new WeChatId())
                .name("N1")
                .weChatOpenId(UUID.randomUUID().toString())
                .phone("12345678")
                .bindDate(DateUtilWrapper.now())
                .build();

        PersonId personId1 = new PersonId();
        PersonId personId2 = new PersonId();
        weChat.addFollower(personId1, DateUtilWrapper.now());
        weChat.addFollower(personId2, DateUtilWrapper.now());

        WeChat weChat1 = weChat.cloneTo(WeChatCategory.Parent);
        when(weChatRepository.loadOf(any(WeChatId.class))).thenReturn(weChat).thenReturn(weChat1).thenReturn(weChat).thenReturn(null).thenReturn(null);
        WeChatApplicationService service = getService();
        service.copyFollowers(weChat.getWeChatId().id(),weChat1.getWeChatId().id());
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("ac-01-000");
        service.copyFollowers(weChat.getWeChatId().id(),weChat1.getWeChatId().id());
    }

    @Test
    public void applyFollowers(){
        WeChatApplicationService service = getService();
        String weChatOpenId = UUID.randomUUID().toString();
        BindCommand command = BindCommand.builder()
                .category(WeChatCategory.Parent.name())
                .name("Name")
                .phone("12345678")
                .wechatOpenId(weChatOpenId)
                .build()
                .addFollower(FollowerData.builder()
                        .cause("cause")
                        .clazzId(new ClazzId().id())
                        .schoolId(new SchoolId().id())
                        .personId(new PersonId().id())
                        .build());

        WeChat weChat = WeChat.builder()
                .name("WeChat")
                .weChatId(new WeChatId())
                .category(WeChatCategory.Parent)
                .weChatOpenId(weChatOpenId)
                .build();
        when(weChatRepository.findByWeChatOpenIdAndCategoryEquals(any(String.class), any(WeChatCategory.class))).thenReturn(weChat);
        when(applyRepository.nextIdentity()).thenReturn(new FollowApplyId());
        doNothing().when(applyRepository).save(any(FollowApply.class));
        service.applyFollowers(command);

        verify(weChatRepository,times(1)).findByWeChatOpenIdAndCategoryEquals(any(String.class), any(WeChatCategory.class));
        verify(applyRepository,times(1)).nextIdentity();
        verify(applyRepository,times(1)).save(any(FollowApply.class));
    }

    @Test
    public void applyAudit(){
        WeChatApplicationService service = getService();

        ApplyAuditCommand command = ApplyAuditCommand.builder()
                .applyId(new FollowApplyId().id())
                .auditorId(new PersonId().id())
                .description("Desc")
                .ok(true)
                .build();

        FollowApply apply = mock(FollowApply.class);
        when(apply.isAudited()).thenReturn(false).thenReturn(true);
        doNothing().when(apply).audite(any(FollowAudit.class));
        when(applyRepository.loadOf(any(FollowApplyId.class))).thenReturn(null).thenReturn(apply).thenReturn(apply);

        FollowAudit audit = mock(FollowAudit.class);
        when(audit.getAuditId()).thenReturn(new FollowAuditId());
        when(applyAuditService.auditFollowStudent(any(PersonId.class),eq(apply), any(Boolean.class), any(String.class))).thenReturn(audit);

        String weChatOpenId = UUID.randomUUID().toString();
        WeChat weChat = WeChat.builder()
                .name("WeChat")
                .weChatId(new WeChatId())
                .category(WeChatCategory.Parent)
                .weChatOpenId(weChatOpenId)
                .build();
        when(weChatRepository.loadOf(any(WeChatId.class))).thenReturn(weChat);

        String auditId = service.applyAudit(command);
        assertNotNull(auditId);
    }

    @Test
    public void cancelApply(){
        WeChatApplicationService service = getService();

        FollowApply apply = FollowApply.builder().applyId(new FollowApplyId()).build();
        when(applyRepository.loadOf(any(FollowApplyId.class))).thenReturn(apply).thenReturn(null);
        doNothing().when(applyRepository).delete(any(FollowApplyId.class));
        service.cancelApply(apply.getApplyId().id());

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("ac-01-005");
        service.cancelApply(apply.getApplyId().id());
    }

    @Test
    public void cancelApplyAudit(){
        WeChatApplicationService service = getService();

        FollowAudit audit = mock(FollowAudit.class);
        FollowApply apply = mock(FollowApply.class);
        when(auditRepository.loadOf(any(FollowAuditId.class))).thenReturn(audit);
        when(applyRepository.loadOf(any(FollowApplyId.class))).thenReturn(apply);
        when(apply.cancel(eq(audit))).thenReturn(true).thenReturn(false);
        doNothing().when(audit).no(any(String.class));
        doNothing().when(auditRepository).save(any(FollowAudit.class));
        doNothing().when(applyRepository).save(any(FollowApply.class));

        ApplyAuditCommand command = ApplyAuditCommand.builder().auditId(new FollowAuditId().id()).applyId(new FollowApplyId().id()).ok(false).description("desc").build();

        service.cancelApplyAudit(command);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ac-01-004");
        service.cancelApplyAudit(command);
    }


    @Test
    public void bindCancel(){
        WeChatApplicationService service = getService();
        BindCommand command = BindCommand.builder().category(WeChatCategory.Teacher.name()).build();
        WeChat weChat = WeChat.builder().weChatId(new WeChatId()).build();
        when(weChatRepository.findByWeChatOpenIdAndCategoryEquals(any(String.class), any(WeChatCategory.class))).thenReturn(weChat);
        doNothing().when( weChatRepository).delete(eq(weChat.getWeChatId()));
        service.bindCancel(command);
    }

    @Test
    public void getWeChatAccessToken(){
        WeChatApplicationService service = getService();
        WebAccessToken token = mock(WebAccessToken.class);
        when(webAccessTokenFactory.newWebAccessToken(any(String.class))).thenReturn(token);
        WebAccessToken token_ = service.getWeChatAccessToken("");
        assertEquals(token,token_);
    }
}