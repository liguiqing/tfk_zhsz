package com.tfk.access.domain.model.school;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.access.ClazzFollowApplyId;
import com.tfk.share.domain.id.access.ClazzFollowAuditId;
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
}