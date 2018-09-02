package com.zhezhu.access.domain.model.school;

import com.zhezhu.commons.domain.Entity;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import lombok.*;

import java.util.Date;

/**
 * 班级关注审核
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of={"auditId"},callSuper = false)
@ToString
public class ClazzFollowAudit extends Entity {

    private ClazzFollowAuditId auditId;

    private ClazzFollowApplyId applyId;

    private PersonId auditorId;

    private Date auditDate;

    private boolean ok;

    private String description;

    public boolean sameOf(ClazzFollowAuditId auditId){
        return this.auditId.equals(auditId);
    }
}