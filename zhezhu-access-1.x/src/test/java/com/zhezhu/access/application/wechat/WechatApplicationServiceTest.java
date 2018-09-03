package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.access.domain.model.wechat.audit.*;
import com.zhezhu.access.domain.model.wechat.config.WeChatConfig;
import com.zhezhu.access.domain.model.wechat.message.MessageHandler;
import com.zhezhu.access.infrastructure.PersonService;
import com.zhezhu.access.infrastructure.WeChatMessageService;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
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
    @Mock
    private MessageHandler messageHandler;

    @Mock
    private WeChatConfig weChatConfig;

    @Mock
    private WeChatRepository weChatRepository;

    @Mock
    private PersonService personService;

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

    private WeChatApplicationService getService()throws Exception{
        WeChatApplicationService service = new WeChatApplicationService();
        FieldUtils.writeField(service,"messageService",messageService,true);
        FieldUtils.writeField(service,"applyAuditService",applyAuditService,true);
        FieldUtils.writeField(service,"auditRepository",auditRepository,true);
        FieldUtils.writeField(service,"applyRepository",applyRepository,true);
        FieldUtils.writeField(service,"personService",personService,true);
        FieldUtils.writeField(service,"weChatRepository",weChatRepository,true);
        FieldUtils.writeField(service,"weChatConfig",weChatConfig,true);
        FieldUtils.writeField(service,"messageHandler",messageHandler,true);
        return spy(service);
    }

    @Test
    public void follow() throws Exception{
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
    public void bind() throws Exception{
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
            when(personService.getPersonId(any(String.class))).thenReturn(personId);
            doNothing().when(weChatRepository).save(any(WeChat.class));

            String weId = service.bind(command);

            assertEquals(weChatId.id(),weId);
        }
        verify(weChatRepository,times(3)).nextIdentity();
        verify(personService,times(3)).getPersonId(any(String.class));
        verify(weChatRepository,times(3)).save(any(WeChat.class));
    }

    @Test
    public void applyFollowers() throws Exception{
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
    public void applyAudit() throws Exception{
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

    }

    @Test
    public void bindCancel() {
    }

    @Test
    public void getWeChatAccessToken() {
    }
}