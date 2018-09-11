package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class WeChatTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addFollower() {
        PersonId personId1 = new PersonId();
        PersonId personId2 = new PersonId();

        WeChat weChat = WeChat.builder().weChatId(new WeChatId()).build();
        weChat.addFollower(personId1, DateUtilWrapper.now());
        weChat.addFollower(personId2, DateUtilWrapper.now());

        assertEquals(2, weChat.followerSize());
    }

    @Test
    public void followerSize() {
        WeChat weChat = WeChat.builder().weChatId(new WeChatId()).build();
        assertEquals(0,weChat.followerSize());
        weChat.addFollower(new PersonId(), DateUtilWrapper.now());
        assertEquals(1,weChat.followerSize());

    }

    @Test
    public void followerAudited() {
    }

    @Test
    public void cloneTo() {
        PersonId personId1 = new PersonId();
        PersonId personId2 = new PersonId();

        WeChat weChat = WeChat.builder()
                .category(WeChatCategory.Teacher)
                .weChatId(new WeChatId())
                .name("N1")
                .weChatOpenId(UUID.randomUUID().toString())
                .phone("12345678")
                .bindDate(DateUtilWrapper.now())
                .build();
        weChat.addFollower(personId1, DateUtilWrapper.now());
        weChat.addFollower(personId2, DateUtilWrapper.now());

        WeChat weChat2 =  weChat.cloneTo(WeChatCategory.Parent);
        assertEquals(0,weChat2.followerSize());
        assertEquals(weChat2.getCategory(),WeChatCategory.Parent);
        assertEquals(weChat.getWeChatOpenId(),weChat2.getWeChatOpenId());
        assertEquals(weChat.getName(),weChat2.getName());
        assertEquals(weChat.getPhone(),weChat2.getPhone());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ac-01-008");
        weChat.cloneTo(WeChatCategory.Teacher);
    }

    @Test
    public void copyFollowers() {
        PersonId personId1 = new PersonId();
        PersonId personId2 = new PersonId();

        WeChat weChat = WeChat.builder()
                .category(WeChatCategory.Teacher)
                .weChatId(new WeChatId())
                .name("N1")
                .weChatOpenId(UUID.randomUUID().toString())
                .phone("12345678")
                .bindDate(DateUtilWrapper.now())
                .build();
        weChat.addFollower(personId1, DateUtilWrapper.now());
        weChat.addFollower(personId2, DateUtilWrapper.now());

        WeChat weChat2 =  weChat.cloneTo(WeChatCategory.Parent);
        weChat2.copyFollowers(weChat);
    }
}