package com.zhezhu.access.domain.model.school;

import com.zhezhu.access.AccessTestConfiguration;
import com.zhezhu.access.config.AccessApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
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
public class ClazzFollowAuditRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ClazzFollowAuditRepository repository;

    @Test
    public void test(){
        ClazzFollowAuditId auditId = repository.nextIdentity();
        ClazzFollowApplyId applyId = new ClazzFollowApplyId();
        PersonId personId = new PersonId();
        ClazzFollowAudit audit = ClazzFollowAudit.builder()
                .auditorId(personId)
                .applyId(applyId)
                .auditId(auditId)
                .auditDate(DateUtilWrapper.Now)
                .ok(true)
                .description("DESC")
                .build();

        repository.save(audit);

        ClazzFollowAudit audit_ = repository.loadOf(auditId);
        assertEquals(audit,audit_);
        assertEquals(personId,audit_.getAuditorId());
        assertTrue(audit_.isOk());
        assertEquals("DESC",audit_.getDescription());

        for(int i=0;i<10000;i++){
            repository.loadOf(auditId);
        }

        repository.delete(auditId);
        audit_ = repository.loadOf(auditId);
        assertNull(audit_);


    }
}