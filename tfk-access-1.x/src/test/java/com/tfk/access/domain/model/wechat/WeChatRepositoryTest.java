package com.tfk.access.domain.model.wechat;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.wechat.WeChatFollowerId;
import com.tfk.share.domain.id.wechat.WeChatId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath:META-INF/spring/applicationContext-access-app.xml",
                "classpath:applicationContext-test-cache.xml",
                "classpath:applicationContext-test-jndi.xml",
                "classpath:applicationContext-access-test-data.xml"}
        )})
@Transactional
@Rollback
public class WeChatRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private WeChatRepository weChatRepository;

    @Test
    public void test(){
        WeChatId weChatId = weChatRepository.nextIdentity();
        PersonId personId = new PersonId();

        WeChat weChat = WeChat.builder()
                .personId(personId)
                .category(WeChatCategory.Student)
                .name("name")
                .phone("12345689788")
                .weChatId(weChatId)
                .weChatOpenId(UUID.randomUUID().toString())
                .bindDate(DateUtilWrapper.now()).build();
        weChatRepository.save(weChat);

        WeChat weChat_ = weChatRepository.loadOf(weChatId);
        assertEquals(weChat,weChat_);

        Date followDate = DateUtilWrapper.now();
        weChat_.addFollower(Follower.builder().followerId(new WeChatFollowerId()).followDate(followDate).weChatId(weChatId).personId(new PersonId()).build());
        weChat_.addFollower(Follower.builder().followerId(new WeChatFollowerId()).followDate(followDate).weChatId(weChatId).personId(new PersonId()).build());
        weChat_.addFollower(Follower.builder().followerId(new WeChatFollowerId()).followDate(followDate).weChatId(weChatId).personId(new PersonId()).build());
        weChat_.setName("name1");
        weChatRepository.save(weChat_);


        weChat_ = weChatRepository.loadOf(weChatId);

        assertEquals(3,weChat_.followerSize());
        assertEquals("name1",weChat_.getName());

        for(int i = 0;i<100000;i++){
            weChat_ = weChatRepository.loadOf(weChatId);
        }

        weChatRepository.delete(weChatId);
        weChat_ = weChatRepository.loadOf(weChatId);
        assertNull(weChat_);
    }
}