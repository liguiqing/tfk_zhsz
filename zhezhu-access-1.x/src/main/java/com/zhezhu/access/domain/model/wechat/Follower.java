package com.zhezhu.access.domain.model.wechat;

import com.google.common.base.Objects;
import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.wechat.WeChatFollowerId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import lombok.*;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(callSuper = false)
public class Follower extends IdentifiedValueObject {
    private WeChatFollowerId followerId;

    private WeChatId weChatId;

    private PersonId personId;

    private Date followDate;

    private FollowerAudit audited;

    public void audited(PersonId auditorId,String auditorName,Date auditDate,AuditResult result){
        this.audited = new FollowerAudit(auditorId, auditorName, auditDate, result);
    }

    public boolean isAudited(){
        return this.audited != null;
    }

    public boolean sameOf(PersonId personId){
        return this.personId.equals(personId);
    }

    public Follower copyTo(WeChatId weChatId){
        Follower follower = new Follower();
        follower.weChatId = weChatId;
        follower.followerId = new WeChatFollowerId();
        follower.personId = personId;
        follower.followDate = followDate;
        follower.audited = audited;
        return follower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        if(this.personId != null && follower.personId != null){
            return Objects.equal(personId, follower.personId);
        }

        return Objects.equal(followerId, follower.followerId);
    }

    @Override
    public int hashCode() {
        if(this.personId != null)
            return Objects.hashCode(followerId, personId);
        return Objects.hashCode(followerId);
    }
}