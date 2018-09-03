package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.domain.Entity;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.wechat.FollowApplyId;
import com.zhezhu.share.domain.id.wechat.FollowAuditId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import lombok.*;

import java.util.Date;

/**
 * 关注申请
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(of={"applyId"})
@ToString
public class FollowApply extends Entity {
    private FollowApplyId applyId;

    private FollowAuditId auditId;

    private WeChatId applierWeChatId;

    private String applierWeChatOpenId;

    private String applierName;

    private Date applyDate;

    private SchoolId followerSchoolId;

    private ClazzId followerClazzId;

    private PersonId followerId;

    private String cause;

    public boolean isAudited(){
        return this.auditId != null;
    }

    public void audite(FollowAudit audit){
        this.auditId = audit.getAuditId();
    }

}