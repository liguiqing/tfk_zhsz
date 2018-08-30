package com.tfk.access.domain.model.school;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.access.ClazzFollowApplyId;
import com.tfk.share.domain.id.access.ClazzFollowAuditId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
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

    public boolean isAudited(){
        return this.auditId != null;
    }

    public void audit(ClazzFollowAudit audit){
        AssertionConcerns.assertArgumentTrue(audit.getApplyId().equals(this.applyId),"无效的审核");
        this.auditId = audit.getAuditId();
    }

}