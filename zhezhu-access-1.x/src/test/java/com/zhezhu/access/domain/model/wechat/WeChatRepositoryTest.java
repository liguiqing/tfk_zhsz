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
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
                .category(WeChatCategory.Student)
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

        weChat_ = weChatRepository.findByWeChatOpenId(weChatOpenId);
        assertEquals(weChat,weChat_);

        weChatRepository.delete(weChatId);
        weChat_ = weChatRepository.loadOf(weChatId);
        assertNull(weChat_);
    }
}