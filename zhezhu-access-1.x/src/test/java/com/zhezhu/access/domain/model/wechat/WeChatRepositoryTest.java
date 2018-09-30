package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.access.AccessTestConfiguration;
import com.zhezhu.access.config.AccessApplicationConfiguration;
import com.zhezhu.access.domain.model.wechat.audit.*;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatFollowerId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextConfiguration(classes = {
        AccessApplicationConfiguration.class,
        AccessTestConfiguration.class,
        CommonsConfiguration.class})
@Transactional
@Rollback
public class WeChatRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private WeChatRepository weChatRepository;

    @Test
    public void test(){
        WeChatId weChatId = weChatRepository.nextIdentity();
        PersonId personId = new PersonId();
        String weChatOpenId = UUID.randomUUID().toString();
        WeChat weChat = WeChat.builder()
                .personId(personId)
                .category(WeChatCategory.Teacher)
                .name("name")
                .phone("12345689788")
                .weChatId(weChatId)
                .weChatOpenId(weChatOpenId)
                .bindDate(DateUtilWrapper.now()).build();
        weChatRepository.save(weChat);

        WeChat weChat_ = weChatRepository.loadOf(weChatId);
        assertEquals(weChat,weChat_);

        Date followDate = DateUtilWrapper.now();
        PersonId followerPId = new PersonId();
        weChat_.addFollower(Follower.builder().followerId(new WeChatFollowerId()).followDate(followDate).weChatId(weChatId).personId(new PersonId()).build());
        weChat_.addFollower(Follower.builder().followerId(new WeChatFollowerId()).followDate(followDate).weChatId(weChatId).personId(new PersonId()).build());
        weChat_.addFollower(Follower.builder().followerId(new WeChatFollowerId()).followDate(followDate).weChatId(weChatId).personId(followerPId).build());
        weChat_.setName("name1");
        //Follower follower1 = weChat_.getFollowers().get(0);

        FollowAuditId auditId = new FollowAuditId();
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();
        WeChatFollowerId followerId = new WeChatFollowerId();
        FollowAudit audit = FollowAudit.builder()
                .auditId(auditId)
                .auditDate(DateUtilWrapper.now())
                .ok(true)
                .description("Desc")
                .auditor(Auditor.builder().schoolId(schoolId).clazzId(clazzId).name("Auditor").auditorId(new PersonId()).role(AuditRole.Teacher).build())
                .applier(Applier.builder().name("Applier").weChatId(new WeChatId()).weChatOpenId("Weixin").build())
                .defendant(Defendant.builder().defendantId(followerPId).name("Name").schoolId(schoolId).role(AuditRole.Student).clazzId(clazzId).build())
                .build();
        weChat_.followerAudited(audit);

        weChatRepository.save(weChat_);

        weChat_ = weChatRepository.loadOf(weChatId);
        Set<Follower> followerSet = weChat_.getFollowers();

        AtomicInteger cout = new AtomicInteger(0);

        followerSet.forEach(follower -> {
            if(follower.isAudited()){
                cout.getAndIncrement();
            }
        });
        assertEquals(1,cout.get());
        assertEquals(3,weChat_.followerSize());

        assertEquals(3,weChat_.followerSize());
        assertEquals("name1",weChat_.getName());

        for(int i = 0;i<100000;i++){
            weChat_ = weChatRepository.loadOf(weChatId);
        }
        WeChatCategory chatCategory = WeChatCategory.valueOf("Teacher");
        weChat_ = weChatRepository.findByWeChatOpenIdAndCategoryEquals(weChatOpenId,chatCategory);
        assertEquals(weChat,weChat_);

        weChatRepository.delete(weChatId);
        weChat_ = weChatRepository.loadOf(weChatId);
        assertNull(weChat_);
        PersonId pid = null;
        for(int k=1;k<=13;k++){
            personId = new PersonId();
            weChatId = new WeChatId();
            weChatOpenId = UUID.randomUUID().toString();
            weChat = WeChat.builder()
                    .personId(personId)
                    .category(WeChatCategory.Parent)
                    .name("name"+1)
                    .phone((12345689788l+k)+"")
                    .weChatId(weChatId)
                    .weChatOpenId(weChatOpenId)
                    .bindDate(DateUtilWrapper.now()).build();
            WeChatFollowerId fid1 = new WeChatFollowerId();
            WeChatFollowerId fid2 = new WeChatFollowerId();
            PersonId pid1 = new PersonId();
            PersonId pid2 = new PersonId();
            weChat.addFollower(Follower.builder().followerId(fid1).followDate(followDate).weChatId(weChatId).personId(pid1).build());

            if(k%5 == 0) {
                weChat.addFollower(Follower.builder().followerId(fid2).followDate(followDate).weChatId(weChatId).personId(pid2).build());
                pid = personId;
            }
            if(k%2 == 0){
                audit = FollowAudit.builder()
                        .auditId(auditId)
                        .auditDate(DateUtilWrapper.now())
                        .ok(true)
                        .description("Desc")
                        .auditor(Auditor.builder().schoolId(schoolId).clazzId(clazzId).name("Auditor").auditorId(new PersonId()).role(AuditRole.Teacher).build())
                        .applier(Applier.builder().name("Applier").weChatId(weChatId).weChatOpenId(weChatOpenId).build())
                        .defendant(Defendant.builder().defendantId(pid1).name("Name").schoolId(schoolId).role(AuditRole.Student).clazzId(clazzId).build())
                        .build();
                weChat.followerAudited(audit);
            }
            weChatRepository.save(weChat);
        }

        List<WeChat> weChats1 = weChatRepository.findAllByFollowersIsAudited(1, 10);
        List<WeChat> weChats2 = weChatRepository.findAllByFollowersIsNotAudited(1, 10);
        assertEquals(6,weChats1.size());
        assertEquals(10,weChats2.size());
        weChats2 = weChatRepository.findAllByFollowersIsNotAudited(1, 15);
        assertTrue(weChats2.size()>=10);

        WeChat weChat1 = weChatRepository.findByPersonIdAndCategoryEquals(pid, WeChatCategory.Parent);
        assertNotNull(weChat1);
    }
}