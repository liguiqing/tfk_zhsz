package com.zhezhu.share.domain.id.wechat;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 微信用户关关注者唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class WeChatFollowerId extends AbstractId {
    public WeChatFollowerId(String anId) {
        super(anId);
    }

    public WeChatFollowerId() {
        super(Identities.genIdNone(IdPrefixes.WeChatFollowerIdPrefix));
    }
}