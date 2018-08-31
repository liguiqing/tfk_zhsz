package com.zhezhu.share.domain.id.wechat;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 关注申请唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class FollowApplyId extends AbstractId {

    public FollowApplyId(String anId) {
        super(anId);
    }

    public FollowApplyId() {
        super(Identities.genIdNone(IdPrefixes.FollowApplyIdPrefix));
    }
}