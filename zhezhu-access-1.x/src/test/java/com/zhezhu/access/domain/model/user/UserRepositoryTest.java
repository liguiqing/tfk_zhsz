package com.zhezhu.access.domain.model.user;

import com.zhezhu.access.AccessTestConfiguration;
import com.zhezhu.access.config.AccessApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.UserId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
@ContextConfiguration(classes = {
        AccessApplicationConfiguration.class,
        AccessTestConfiguration.class,
        CommonsConfiguration.class})
@Transactional
@Rollback
public class UserRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test(){
        UserId userId = userRepository.nextIdentity();
        PersonId personId = new PersonId();
        UserDetail userDetail = UserDetail.builder().personId(personId).realName("RealName").mobile("136235647891").email("123@123.com").build();
        User user = User.builder().userId(userId)
                .userName("UserName")
                .password("password")
                .createTime(DateUtilWrapper.Now)
                .ok(true)
                .build();
        user.addDetail(userDetail);

        userRepository.save(user);

        User user_ = userRepository.loadOf(userId);
        assertEquals(user,user_);
        assertEquals(userDetail.getEmail(),user_.getDetail().getEmail());
        user_ = userRepository.findByUserName("UserName");
        assertEquals(user,user_);
        assertEquals(userDetail.getEmail(),user_.getDetail().getEmail());
        for(int i=0;i<10000;i++){
            userRepository.loadOf(userId);
        }
        userRepository.delete(userId);
        user_ = userRepository.loadOf(userId);
        assertNull(user_);
    }
}