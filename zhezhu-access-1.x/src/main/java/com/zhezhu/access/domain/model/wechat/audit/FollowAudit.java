package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.domain.Entity;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
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

    private FollowApplyId applyId;

    private Auditor auditor;

    private Applier applier;

    private Defendant defendant;

    private Date auditDate;

    private boolean ok;

    private String description;

    public void yes(String description){
        this.ok = true;
        this.auditDate = DateUtilWrapper.now();
        if(description != null)
            this.description = description;
    }

    public void no(String description){
        this.ok = false;
        this.auditDate = DateUtilWrapper.now();
        if(description != null)
            this.description = description;
    }

}