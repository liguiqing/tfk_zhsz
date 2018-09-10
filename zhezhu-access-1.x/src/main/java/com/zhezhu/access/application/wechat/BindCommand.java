package com.zhezhu.access.application.wechat;


import com.google.common.collect.Lists;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import lombok.*;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"followers"})
@Builder
public class BindCommand {
    private String wechatOpenId;

    private String category; //WeChatCategory

    private String name;

    private String phone;

    private List<FollowerData> followers;

    public boolean hasFollowers(){
        return CollectionsUtilWrapper.isNotNullAndNotEmpty(this.followers);
    }

    public BindCommand addFollower(FollowerData follower){
        if(this.followers == null)
            this.followers = Lists.newArrayList();
        this.followers.add(follower);
        return this;
    }
}