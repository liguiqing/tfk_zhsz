package com.zhezhu.access.application.wechat;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.access.domain.model.wechat.FollowerAudit;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.share.domain.person.Person;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.CredentialsData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class WeChatQueryServiceTest {

    @Mock
    private WeChatRepository weChatRepository;

    @Mock
    private FollowerTransferHelper followerTransferHelper;

    @Mock
    private SchoolService schoolService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private WeChatQueryService getService()throws Exception{
        WeChatQueryService service = new WeChatQueryService();
        FieldUtils.writeField(service,"weChatRepository",weChatRepository,true);
        FieldUtils.writeField(service,"followerTransferHelper",followerTransferHelper,true);
        FieldUtils.writeField(service,"schoolService",schoolService,true);
        return spy(service);
    }

    @Test
    public void getWeChats() throws Exception {
        WeChatQueryService service = getService();
        List<WeChat> weChats = Lists.newArrayList();
        PersonId personId = new PersonId();
        weChats.add(WeChat.builder().personId(personId).weChatId(new WeChatId()).bindDate(DateUtilWrapper.now()).category(WeChatCategory.Teacher).name("W1").phone("123456789").build());
        weChats.add(WeChat.builder().personId(personId).weChatId(new WeChatId()).bindDate(DateUtilWrapper.now()).category(WeChatCategory.Parent).name("W1").phone("123456789").build());
        String weChatOpenId = UUID.randomUUID().toString();
        when(weChatRepository.findAllByWeChatOpenId(eq(weChatOpenId))).thenReturn(weChats);
        List<WeChatData> weChatData = service.getWeChats(weChatOpenId);
        assertEquals(weChats.size(),weChatData.size());
        assertEquals(weChats.get(0).getName(),weChatData.get(0).getName());
        assertEquals(weChats.get(1).getPhone(),weChatData.get(0).getPhone());
    }

    @Test
    public void getFollowers() throws Exception {
        String weChatOpenId = UUID.randomUUID().toString();
        Set<Follower> followers = Sets.newHashSet();
        followers.add(Follower.builder().personId(new PersonId()).audited(FollowerAudit.builder().build()).build());
        followers.add(Follower.builder().personId(new PersonId()).audited(FollowerAudit.builder().build()).build());
        followers.add(Follower.builder().personId(new PersonId()).build());
        followers.add(Follower.builder().personId(new PersonId()).build());
        WeChat weChat = mock(WeChat.class);
        when(weChat.getFollowers()).thenReturn(Sets.newHashSet()).thenReturn(followers);
        when(weChatRepository.findByWeChatOpenIdAndCategoryEquals(weChatOpenId, WeChatCategory.Student)).thenReturn(null).thenReturn(weChat);
        FollowerData student1 = FollowerData.builder().name("S1").build();
        FollowerData student2 = FollowerData.builder().name("S2").build();
        when(followerTransferHelper.transTo(any(Follower.class),any(WeChatCategory.class))).thenReturn(student1).thenReturn(student2);

        WeChatQueryService service  = getService();
        List<FollowerData> followerData = service.getFollowers(weChatOpenId, WeChatCategory.Student);
        assertEquals(0,followerData.size());
        followerData = service.getFollowers(weChatOpenId, WeChatCategory.Student);
        assertEquals(0,followerData.size());
        followerData = service.getFollowers(weChatOpenId, WeChatCategory.Student);
        assertEquals(2,followerData.size());
        assertEquals("S1",followerData.get(0).getName());
        assertEquals("S2",followerData.get(1).getName());
    }

    @Test
    public void findFollowerBy()throws Exception{
        WeChatQueryService service  = getService();
        List<StudentData> studentData = Lists.newArrayList();
        ClazzId clazzId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        List<ClazzData> clazzData = Lists.newArrayList();
        clazzData.add(ClazzData.builder().clazzId(clazzId.id()).build());

        for(int i=0;i<10;i++){
            List<CredentialsData> credentialsData = Lists.newArrayList();
            String gender = Gender.Female.name();
            String name = "S"+i;

            if(i%2 == 0){
                credentialsData.add(new CredentialsData("身份证", "12345678" + i));
                gender = Gender.Male.name();
            }else{
                credentialsData.add(new CredentialsData("学号", "987654321" + i));
            }

            if(i>6)
                name = "SS";

            StudentData student = StudentData.builder()
                    .schoolId(schoolId.id())
                    .clazzes(clazzData)
                    .name(name)
                    .gender(gender)
                    .credentials(credentialsData)
                    .personId(new PersonId().id())
                    .build();
            studentData.add(student);
        }

        when(schoolService.getClazzStudents(any(ClazzId.class))).thenReturn(null).thenReturn(studentData);
        String[] c = service.findFollowerBy("", clazzId.id(), "", "", null);
        assertEquals(0, c.length);
        c = service.findFollowerBy("S0", clazzId.id(), null, null, null);
        assertEquals(1, c.length);
        c = service.findFollowerBy("S5", clazzId.id(), null, null, null);
        assertEquals(1, c.length);
        c = service.findFollowerBy("S6", clazzId.id(), null, null, null);
        assertEquals(1, c.length);
        c = service.findFollowerBy("Sasdf", clazzId.id(), null, null, null);
        assertEquals(0, c.length);
        c = service.findFollowerBy("SS", clazzId.id(), null, null, null);
        assertEquals(3, c.length);
        c = service.findFollowerBy("SS", clazzId.id(), "身份证", "123456788", null);
        assertEquals(1, c.length);
        c = service.findFollowerBy("SS", clazzId.id(), null, null, Gender.Female);
        assertEquals(2, c.length);
        c = service.findFollowerBy("SS", clazzId.id(), "学号", "9876543219", null);
        assertEquals(1, c.length);
        c = service.findFollowerBy("SS", clazzId.id(), "学号", "9876543217", null);
        assertEquals(1, c.length);
    }

}