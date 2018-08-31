package com.tfk.access.domain.model.school;

import com.tfk.access.AccessTestConfiguration;
import com.tfk.access.config.AccessApplicationConfiguration;
import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.access.ClazzFollowApplyId;
import com.tfk.share.domain.id.access.ClazzFollowAuditId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

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

        ClazzFollowApply apply_ = applyRepository.loadOf(applyId);
        assertEquals(apply,apply);

        ClazzFollowAuditId auditId = new ClazzFollowAuditId();
        ClazzFollowAudit audit = ClazzFollowAudit.builder().auditId(auditId).applyId(applyId).build();
        apply_.audit(audit);
        applyRepository.save(apply_);

        apply = applyRepository.loadOf(applyId);
        assertTrue(apply.isAudited());

        int i = 0;
        while (i<100000){
            applyRepository.loadOf(applyId);
            i++;
        }

        applyRepository.delete(applyId);
        apply = applyRepository.loadOf(applyId);
        assertNull(apply);
    }
}