package com.zhezhu.access.domain.model.wechat;

import com.google.common.collect.Sets;
import com.zhezhu.access.domain.model.wechat.audit.FollowAudit;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.domain.Entity;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.wechat.WeChatFollowerId;
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
@ToString(exclude = {"followers"})
@EqualsAndHashCode(of="weChatId",callSuper = false)
public class WeChat extends Entity {

    private WeChatId weChatId;

    private PersonId personId;

    private String weChatOpenId;

    private WeChatCategory category;

    private Date bindDate;

    private String name;

    private String phone;

    private Set<Follower> followers;

    protected WeChat addFollower(Follower follower){
        if(this.followers == null)
            this.followers = Sets.newHashSet();

        this.followers.add(follower);
        return this;
    }

    public WeChat addFollower(PersonId followerId,Date followDateDate){
        if(followDateDate == null)
            followDateDate = DateUtilWrapper.now();

        Follower follower = Follower.builder()
                .weChatId(this.getWeChatId())
                .followerId(new WeChatFollowerId())
                .personId(followerId)
                .followDate(followDateDate)
                .build();
        this.addFollower(follower);
        return this;
    }

    public Follower followerOf(PersonId personId){
        if(CollectionsUtilWrapper.isNotNullAndNotEmpty(this.followers)){
            for(Follower follower:this.followers){
                if(follower.getPersonId().equals(personId))
                    follower.weChatOf(this);
                    return follower;
            }
        }
        return null;
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

    public WeChat cloneTo(WeChatCategory other) {
        AssertionConcerns.assertArgumentNotEquals(this.category,other,"ac-01-008");
        return WeChat.builder()
                .weChatId(new WeChatId())
                .weChatOpenId(this.weChatOpenId)
                .personId(this.personId)
                .category(other)
                .phone(this.phone)
                .name(this.name)
                .bindDate(this.bindDate)
                .build();
    }

    public void copyFollowers(WeChat other){
        if(this.equals(other))
            return;

        if(other.followerSize() > 0){
            for(Follower follower:other.followers){
                this.addFollower(follower.copyTo(this.weChatId));
            }
        }
    }
}