package com.zhezhu.access.port.adapter.http.controller;

import com.google.common.collect.Lists;
import com.zhezhu.access.application.wechat.*;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private WeChatQueryService weChatQueryService;

    @Test
    public void onGetJoined()throws Exception{

        ArrayList<WeChatData> data = new ArrayList<>();
        data.add(WeChatData.builder().name("Name").build());
        data.add(WeChatData.builder().role(WeChatCategory.Teacher.name()).build());
        when(weChatQueryService.getWeChats(any(String.class))).thenReturn(data);


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
    public void onBind() throws Exception{
        BindCommand command = BindCommand.builder().wechatOpenId("afad").category(WeChatCategory.Teacher.name()).build();
        WeChatId weChatId = new WeChatId();
        when(wechatApplicationService.bind(any(BindCommand.class))).thenReturn(weChatId.id());
        ArrayList<WeChatData> data = new ArrayList<>();
        String personId = new PersonId().id();
        data.add(WeChatData.builder().name("Name").personId(personId).build());
        data.add(WeChatData.builder().role(WeChatCategory.Teacher.name()).build());
        when(weChatQueryService.getWeChats(any(String.class))).thenReturn(data);

        String content = toJsonString(command);
        this.mvc.perform(post("/wechat/bind").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.weChatId", equalTo(weChatId.id())))
                .andExpect(jsonPath("$.weChats[0].name", equalTo("Name")))
                .andExpect(jsonPath("$.weChats[0].personId", equalTo(personId)))
                .andExpect(jsonPath("$.weChats[1].role", equalTo(WeChatCategory.Teacher.name())))
                .andExpect(view().name("/wechat/bindSuccess"));

    }

    @Test
    public void onFollower() throws Exception{
        List<FollowerData> followerData = Lists.newArrayList();
        followerData.add(FollowerData.builder().clazzId(new ClazzId().id()).name("T1").schoolId(new SchoolId().id()).gender(Gender.Female.name()).build());
        followerData.add(FollowerData.builder().clazzId(new ClazzId().id()).name("T2").schoolId(new SchoolId().id()).gender(Gender.Male.name()).build());
        BindCommand command = BindCommand.builder().wechatOpenId(UUID.randomUUID().toString()).followers(followerData).category(WeChatCategory.Parent.name()).build();

        doNothing().when(wechatApplicationService).applyFollowers(any(BindCommand.class));

        String content = toJsonString(command);
        this.mvc.perform(post("/wechat/apply/follower").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/wechat/applyFollowersSuccess"));
    }

    @Test
    public void onCancelFollower() throws Exception{
        FollowApplyId applyId = new FollowApplyId();
        doNothing().when(wechatApplicationService).cancelApply(any(String.class));
        this.mvc.perform(delete("/wechat/apply/follower/"+applyId.id()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/wechat/cancelFollowersSuccess"));
    }

    @Test
    public void onAuditFollower()throws Exception{
        ApplyAuditCommand command = ApplyAuditCommand.builder()
                .applyId(new FollowApplyId().id())
                .auditorId(new PersonId().id())
                .description("DESC")
                .ok(true)
                .build();
        FollowAuditId auditId = new FollowAuditId();
        when(wechatApplicationService.applyAudit(any(ApplyAuditCommand.class))).thenReturn(auditId.id());

        String content = toJsonString(command);
        this.mvc.perform(post("/wechat/audit/follower").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.auditId", equalTo(auditId.id())))
                .andExpect(view().name("/wechat/auditFollowersSuccess"));
    }

    @Test
    public void onCancelAuditFollower()throws Exception{
        FollowAuditId auditId = new FollowAuditId();
        ApplyAuditCommand command = ApplyAuditCommand.builder()
                .applyId(new FollowApplyId().id())
                .auditId(auditId.id())
                .auditorId(new PersonId().id())
                .description("DESC")
                .ok(false)
                .build();

        doNothing().when(wechatApplicationService).cancelApplyAudit(any(ApplyAuditCommand.class));

        String content = toJsonString(command);
        this.mvc.perform(delete("/wechat/audit/follower").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/wechat/cancelAuditFollowersSuccess"));
    }

    @Test
    public void onGetFollowerOfStudent()throws Exception{
        String weChatOpenId = UUID.randomUUID().toString();
        List<FollowerData> followers = Lists.newArrayList();
        followers.add(FollowerData.builder().personId(new PersonId().id()).schoolId(new SchoolId().id()).clazzId(new ClazzId().id()).name("S1").gender(Gender.Female.name()).build());
        followers.add(FollowerData.builder().personId(new PersonId().id()).schoolId(new SchoolId().id()).clazzId(new ClazzId().id()).name("S2").gender(Gender.Male.name()).build());

        when(weChatQueryService.getFollowers(eq(weChatOpenId),eq(WeChatCategory.Student),eq(true))).thenReturn(followers);

        this.mvc.perform(get("/wechat/query/followers/"+weChatOpenId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.followers[0].name", equalTo("S1")))
                .andExpect(jsonPath("$.followers[1].name", equalTo("S2")))
                .andExpect(view().name("/wechat/followerList"));
    }


    @Test
    public void onQueryFollowerCanBeApplied()throws Exception{
        String[] pids1 = new String[]{new PersonId().id()};
        String[] pids2 = new String[]{new PersonId().id()};
        String[] pids3 = new String[]{new PersonId().id(),new PersonId().id()};
        String clazzId = new ClazzId().id();
        when(weChatQueryService.findFollowerBy(any(String.class),any(String.class),isNull(),isNull(),isNull()))
                .thenReturn(new String[]{}).thenReturn(pids1);

        when(weChatQueryService.findFollowerBy(any(String.class),any(String.class),isNull(),isNull(),any(Gender.class)))
                .thenReturn(pids2).thenReturn(pids3);

        when(weChatQueryService.findFollowerBy(any(String.class),any(String.class),any(String.class),any(String.class),any(Gender.class)))
                .thenReturn(pids3);

        this.mvc.perform(get("/wechat/apply/query/followers").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).param("name","N1").param("clazzId",clazzId))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/wechat/followerList"));

        this.mvc.perform(get("/wechat/apply/query/followers").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).param("name","N1").param("clazzId",clazzId))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.personIds[0]", equalTo(pids1[0])))
                .andExpect(view().name("/wechat/followerList"));

        this.mvc.perform(get("/wechat/apply/query/followers").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("name","N1")
                .param("clazzId",clazzId)
                .param("gender",Gender.Male.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.personIds[0]", equalTo(pids2[0])))
                .andExpect(view().name("/wechat/followerList"));

        this.mvc.perform(get("/wechat/apply/query/followers").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("name","N1")
                .param("clazzId",clazzId)
                .param("credentialsName","身份证")
                .param("credentialsValue","1234567891")
                .param("gender",Gender.Male.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.personIds[0]", equalTo(pids3[0])))
                .andExpect(view().name("/wechat/followerList"));
    }
}