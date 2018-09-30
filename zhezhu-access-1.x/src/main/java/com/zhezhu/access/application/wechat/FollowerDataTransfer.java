package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.audit.FollowApply;

/**
 * 关注者数据转换器
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface FollowerDataTransfer {

    FollowerData trans(Follower follower, WeChatCategory category);

    FollowerData trans(FollowApply apply, WeChatCategory category);
}