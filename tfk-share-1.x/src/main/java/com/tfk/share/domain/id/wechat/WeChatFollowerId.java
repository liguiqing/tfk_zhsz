package com.tfk.share.domain.id.wechat;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

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