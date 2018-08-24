package com.tfk.access.domain.model.wechat.audit;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.id.wechat.FollowAuditId;
import com.tfk.share.domain.id.wechat.WeChatFollowerId;
import lombok.*;

import java.util.Date;

/**
 * 关注者审核
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of = {"auditId"})
@ToString
public class FollowAudit extends Entity {

    private FollowAuditId auditId;

    private WeChatFollowerId followerId;

    private Auditor auditor;

    private Proposer proposer;

    private Defendant defendant;

    private Date auditDate;

    private boolean ok;

    private String description;

}