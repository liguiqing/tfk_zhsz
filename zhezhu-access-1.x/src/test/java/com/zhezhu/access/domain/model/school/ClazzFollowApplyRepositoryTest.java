package com.zhezhu.access.domain.model.school;

import com.zhezhu.access.AccessTestConfiguration;
import com.zhezhu.access.config.AccessApplicationConfiguration;
import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class ClazzFollowApplyRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ClazzFollowApplyRepository applyRepository;

    @Test
    public void test(){

        ClazzFollowApplyId applyId = applyRepository.nextIdentity();
        PersonId applierId = new PersonId();
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();
        ClazzFollowApply apply = ClazzFollowApply.builder()
                .applyId(applyId)
                .applierId(applierId)
                .applyingSchoolId(schoolId)
                .applyingClazzId(clazzId)
                .applierName("name")
                .applierPhone("13878965412")
                .applyDate(DateUtilWrapper.now())
                .cause("Cause")
                .build();
        applyRepository.save(apply);

        List<ClazzFollowApply> applies = applyRepository.findAllByApplierIdAndAuditIdIsNull(applierId);
        assertEquals(1,applies.size());
        assertFalse(applies.get(0).isPassed());

        ClazzFollowApply apply_ = applyRepository.loadOf(applyId);
        assertEquals(apply,apply_);

        ClazzFollowAuditId auditId = new ClazzFollowAuditId();
        ClazzFollowAudit audit = ClazzFollowAudit.builder().auditId(auditId).ok(true).applyId(applyId).build();
        apply_.audit(audit);
        applyRepository.save(apply_);

        apply = applyRepository.loadOf(applyId);
        assertTrue(apply.isAudited());
        assertTrue(apply.isPassed());
        int i = 0;
        while (i<100000){
            applyRepository.loadOf(applyId);
            i++;
        }

        applies = applyRepository.findAllByApplierIdAndAuditIdIsNotNull(applierId);
        assertEquals(1,applies.size());
        assertTrue(applies.get(0).isPassed());

        applyRepository.delete(applyId);
        apply = applyRepository.loadOf(applyId);
        assertNull(apply);

    }
}