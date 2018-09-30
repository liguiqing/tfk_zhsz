package com.zhezhu.access.application.wechat;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zhezhu.access.domain.model.wechat.*;
import com.zhezhu.access.domain.model.wechat.audit.FollowApply;
import com.zhezhu.access.domain.model.wechat.audit.FollowApplyRepository;
import com.zhezhu.access.domain.model.wechat.audit.FollowAuditRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import com.zhezhu.share.domain.person.Gender;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.CredentialsData;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
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

    @Mock
    private FollowApplyRepository applyRepository;

    @Mock
    private FollowAuditRepository auditRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private WeChatQueryService getService(){
        WeChatQueryService service = new WeChatQueryService(weChatRepository,
                followerTransferHelper,schoolService,
                applyRepository,auditRepository);

        return spy(service);
    }

    @Test
    public void getWeChats()  {
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
    public void getFollowers()  {
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
        List<FollowerData> followerData = service.getFollowers(weChatOpenId, WeChatCategory.Student,true);
        assertEquals(0,followerData.size());
        followerData = service.getFollowers(weChatOpenId, WeChatCategory.Student,true);
        assertEquals(0,followerData.size());
        followerData = service.getFollowers(weChatOpenId, WeChatCategory.Student,true);
        assertEquals(2,followerData.size());
        assertEquals("S1",followerData.get(0).getName());
        assertEquals("S2",followerData.get(1).getName());
    }

    @Test
    public void findFollowerBy(){
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

    @Test
    public void getAllFollowers(){
        List<FollowApply> applies1 = new ArrayList<>();
        List<FollowApply> applies2 = new ArrayList<>();
        for(int i=0;i<10;i++){
            FollowApply apply1 = mock(FollowApply.class);
            when(apply1.getApplierWeChatOpenId()).thenReturn(UUID.randomUUID().toString());
            when(apply1.getFollowerId()).thenReturn(new PersonId());
            when(apply1.getApplyId()).thenReturn(new FollowApplyId());
            when(apply1.getCause()).thenReturn("c"+i);
            when(apply1.getApplyCredential()).thenReturn("ac"+i);
            applies1.add(apply1);
            applies2.add(apply1);
        }
        FollowerData data1 = mock(FollowerData.class);
        FollowerData data2 = mock(FollowerData.class);
        FollowerData data3 = mock(FollowerData.class);
        FollowerData data4 = mock(FollowerData.class);
        FollowerData data5 = mock(FollowerData.class);
        FollowerData data6 = mock(FollowerData.class);
        FollowerData data7 = mock(FollowerData.class);
        FollowerData data8 = mock(FollowerData.class);
        FollowerData data9 = mock(FollowerData.class);
        FollowerData data10 = mock(FollowerData.class);

        when(followerTransferHelper.transTo(any(FollowApply.class),eq(WeChatCategory.Student)))
                .thenReturn(data1).thenReturn(data2)
                .thenReturn(data3).thenReturn(data4)
                .thenReturn(data5).thenReturn(data6)
                .thenReturn(data7).thenReturn(data8)
                .thenReturn(data9).thenReturn(data10);

        when(applyRepository.findAllByAuditIdIsNotNull(anyInt(),anyInt())).thenReturn(applies1).thenReturn(null);
        when(applyRepository.findAllByAuditIdIsNull(anyInt(),anyInt())).thenReturn(applies2).thenReturn(null);

        WeChatQueryService service  = getService();
        List<FollowerData> datas1 = service.getAllFollowers(1, 10, true);
        List<FollowerData> datas2 = service.getAllFollowers(1, 10, false);
        assertEquals(10,datas1.size());
        assertEquals(10,datas2.size());

        datas1 = service.getAllFollowers(1, 10, true);
        datas2 = service.getAllFollowers(1, 10, false);
        assertEquals(0,datas1.size());
        assertEquals(0,datas2.size());

    }

}