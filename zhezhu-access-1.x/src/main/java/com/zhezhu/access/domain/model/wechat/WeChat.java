package com.zhezhu.access.domain.model.wechat;

import com.google.common.collect.Sets;
import com.zhezhu.access.domain.model.wechat.audit.FollowAudit;
import com.zhezhu.commons.domain.Entity;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import lombok.*;

import java.util.Date;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"followers"},callSuper = false)
@EqualsAndHashCode(of="weChatId")
public class WeChat extends Entity {

    private WeChatId weChatId;

    private PersonId personId;

    private String weChatOpenId;

    private WeChatCategory category;

    private Date bindDate;

    private String name;

    private String phone;

    private Set<Follower> followers;

    public WeChat addFollower(Follower follower){
        if(this.followers == null)
            this.followers = Sets.newHashSet();
        this.followers.add(follower);
        return this;
    }

    public int followerSize(){
        if(this.followers == null)
            return 0;
        return this.followers.size();
    }

    public void followerAudited(FollowAudit audit) {
        if(this.followers == null)
            return;
        this.followers.forEach(follower -> {
            if(!follower.isAudited() && follower.sameOf(audit.getDefendant().getDefendantId())){
                AuditResult result = audit.isOk()?AuditResult.Yes:AuditResult.No;
                follower.audited(audit.getAuditor().getAuditorId(),audit.getAuditor().getName(),audit.getAuditDate(),result);
            }
        });
    }

}