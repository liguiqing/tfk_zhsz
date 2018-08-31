package com.tfk.access.domain.model.wechat.audit;

import com.tfk.access.AccessTestConfiguration;
import com.tfk.access.config.AccessApplicationConfiguration;
import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.wechat.FollowApplyId;
import com.tfk.share.domain.id.wechat.FollowAuditId;
import com.tfk.share.domain.id.wechat.WeChatId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextConfiguration(classes = {
        AccessApplicationConfiguration.class,
        AccessTestConfiguration.class,
        CommonsConfiguration.class})
@Transactional
@Rollback
public class FollowApplyRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FollowApplyRepository applyRepository;

    @Test
    public void test(){
        String weChatOpenId = UUID.randomUUID().toString();
        FollowApplyId applyId = applyRepository.nextIdentity();
        FollowApply apply = FollowApply.builder()
                .applyId(applyId)
                //.auditId(new FollowAuditId())
                .applierName("ApplierName")
                .applierWeChatId(new WeChatId())
                .applierWeChatOpenId(weChatOpenId)
                .applyDate(DateUtilWrapper.now())
                .cause("Cause")
                .followerSchoolId(new SchoolId())
                .followerClazzId(new ClazzId())
                .followerId(new PersonId())
                .build();
        applyRepository.save(apply);

        FollowApply apply_ = applyRepository.loadOf(applyId);

        assertEquals(apply,apply_);
        for(int i = 0;i<100000;i++){
            applyRepository.loadOf(applyId);
        }
        FollowAudit audit = FollowAudit.builder().auditId(new FollowAuditId()).build();
        apply_.audite(audit);
        applyRepository.save(apply_);

        FollowApply apply__ = applyRepository.loadOf(applyId);
        assertEquals(apply_,apply__);
        assertEquals(audit.getAuditId(),apply__.getAuditId());
    }
}