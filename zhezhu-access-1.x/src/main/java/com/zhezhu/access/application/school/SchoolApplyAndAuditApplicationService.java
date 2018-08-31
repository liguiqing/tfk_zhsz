package com.zhezhu.access.application.school;

import com.zhezhu.access.domain.model.school.ClazzFollowApply;
import com.zhezhu.access.domain.model.school.ClazzFollowApplyRepository;
import com.zhezhu.access.domain.model.school.ClazzFollowAudit;
import com.zhezhu.access.domain.model.school.ClazzFollowAuditRepository;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
public class SchoolApplyAndAuditApplicationService {

    @Autowired
    private ClazzFollowApplyRepository clazzFollowApplyRepository;

    @Autowired
    private ClazzFollowAuditRepository clazzFollowAuditRepository;

    @Transactional(rollbackFor = Exception.class)
    public String followClazzApply(ClazzFollowApplyCommand command){
        log.debug("Clazz follow applay {}",command);

        ClazzFollowApplyId applyId = clazzFollowApplyRepository.nextIdentity();
        ClazzFollowApply apply = command.toApply(applyId);

        clazzFollowApplyRepository.save(apply);
        return applyId.id();
    }

    @Transactional(rollbackFor = Exception.class)
    public void followClazzApplyCancel(String applyId){
        log.debug("Cancel clazz follow applay {}",applyId);

        ClazzFollowApplyId applyId1 = new ClazzFollowApplyId(applyId);
        ClazzFollowApply apply = clazzFollowApplyRepository.loadOf(applyId1);
        AssertionConcerns.assertArgumentTrue((apply != null && apply.isAudited()),"ac-01-001");
        clazzFollowApplyRepository.delete(applyId1);
    }

    @Transactional(rollbackFor = Exception.class)
    public String followClazzAudit(ClazzFollowAuditCommand command){
        log.debug(" Clazz apply audit {}",command);
        ClazzFollowAuditId auditId = clazzFollowAuditRepository.nextIdentity();
        ClazzFollowAudit audit = command.toAudit(auditId);

        ClazzFollowApply apply = clazzFollowApplyRepository.loadOf(audit.getApplyId());
        AssertionConcerns.assertArgumentTrue((apply != null && apply.isAudited()),"ac-01-002");
        clazzFollowAuditRepository.save(audit);
        apply.audit(audit);
        clazzFollowApplyRepository.save(apply);

        return auditId.id();
    }

    @Transactional(rollbackFor = Exception.class)
    public void followClazzAuditCancel(String auditId){
        log.debug(" Clazz apply audit cancel {}",auditId);
        ClazzFollowAuditId auditId1 = new ClazzFollowAuditId(auditId);
        ClazzFollowAudit audit = clazzFollowAuditRepository.loadOf(auditId1);
        AssertionConcerns.assertArgumentTrue((audit != null),"ac-01-003");
        ClazzFollowApply apply = clazzFollowApplyRepository.loadOf(audit.getApplyId());

        apply.cancelAudit(audit);
        clazzFollowAuditRepository.delete(auditId1);
        clazzFollowApplyRepository.save(apply);
    }

}