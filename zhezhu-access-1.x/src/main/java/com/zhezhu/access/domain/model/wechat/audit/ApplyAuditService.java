package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.infrastructure.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class ApplyAuditService {

    @Autowired
    private FollowApplyRepository applyRepository;

    @Autowired
    private FollowAuditRepository auditRepository;

    @Autowired
    private PersonService personService;



    public FollowAudit auditFollowStudent(PersonId auditorId,FollowApply apply,boolean ok,String description){
        String auditorName = personService.getName(auditorId.id(),PersonService.QueryTarget.Teacher);

        Auditor auditor = Auditor.builder()
                .schoolId(apply.getFollowerSchoolId())
                .clazzId(apply.getFollowerClazzId())
                .auditorId(auditorId)
                .role(AuditRole.Teacher)
                .name(auditorName)
                .build();

        Applier applier = Applier.builder()
                .name(apply.getApplierName())
                .weChatId(apply.getApplierWeChatId())
                .weChatOpenId(apply.getApplierWeChatOpenId()).build();

        PersonId defendantId = apply.getFollowerId();
        String defendantName = personService.getName(defendantId.id(), PersonService.QueryTarget.Student);
        Defendant defendant = Defendant.builder()
                .schoolId(apply.getFollowerSchoolId())
                .clazzId(apply.getFollowerClazzId())
                .name(defendantName)
                .defendantId(defendantId)
                .role(AuditRole.Student)
                .build();

        FollowAudit audit = FollowAudit.builder()
                .auditId(auditRepository.nextIdentity())
                .applyId(apply.getApplyId())
                .auditor(auditor)
                .applier(applier)
                .defendant(defendant)
                .description(description)
                .ok(ok)
                .auditDate(DateUtilWrapper.now())
                .build();

        return audit;
    }

}