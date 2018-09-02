package com.zhezhu.access.domain.model.school;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.domain.Entity;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.*;

import java.util.Date;

/**
 * 班级关注申请
 *
 * @author Liguiqing
 * @since V3.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of = {"applyId"},callSuper = false)
@ToString
public class ClazzFollowApply extends Entity {

    private ClazzFollowApplyId applyId;

    private ClazzFollowAuditId auditId;

    private SchoolId applyingSchoolId;

    private ClazzId applyingClazzId;

    private PersonId applierId;

    private String applierName;

    private String applierPhone;

    private Date applyDate;

    private String cause;

    private boolean passed;

    public boolean isAudited(){
        return this.auditId != null;
    }

    public void audit(ClazzFollowAudit audit){
        AssertionConcerns.assertArgumentTrue(audit.getApplyId().equals(this.applyId),"无效的审核");
        this.auditId = audit.getAuditId();
        this.passed = audit.isOk();
    }

    public void cancelAudit(ClazzFollowAudit audit){
        if(this.isAudited() && audit.sameOf(this.auditId))
            this.auditId = null;
    }

}