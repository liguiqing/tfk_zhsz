package com.tfk.access.domain.model.wechat.audit;

import com.tfk.access.AccessTestConfiguration;
import com.tfk.access.config.AccessApplicationConfiguration;
import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.wechat.FollowAuditId;
import com.tfk.share.domain.id.wechat.WeChatId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/


@ContextHierarchy({
    @ContextConfiguration(classes = {
            AccessApplicationConfiguration.class,
            AccessTestConfiguration.class,
            CommonsConfiguration.class})
})
@Transactional
@Rollback
public class FollowAuditRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FollowAuditRepository followAuditRepository;

    @Test
    public void test(){
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();

        FollowAuditId auditId = followAuditRepository.nextIdentity();
        FollowAudit audit = FollowAudit.builder()
                .auditId(auditId)
                .auditDate(DateUtilWrapper.now())
                .ok(true)
                .description("Desc")
                .auditor(Auditor.builder().schoolId(schoolId).clazzId(clazzId).name("Auditor").auditorId(new PersonId()).role(AuditRole.Teacher).build())
                .applier(Applier.builder().name("Applier").weChatId(new WeChatId()).weChatOpenId("Weixin").build())
                .defendant(Defendant.builder().defendantId(new PersonId()).name("Name").schoolId(schoolId).role(AuditRole.Student).clazzId(clazzId).build())
                .build();

        followAuditRepository.save(audit);

        FollowAudit audit_ = followAuditRepository.loadOf(auditId);
        assertEquals(audit,audit_);

        for(int i=0;i<100000;i++){
            followAuditRepository.loadOf(auditId);
        }
    }
}