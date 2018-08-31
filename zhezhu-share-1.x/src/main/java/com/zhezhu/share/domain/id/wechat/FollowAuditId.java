package com.zhezhu.share.domain.id.wechat;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 关注者审核ID
 *
 * @author Liguiqing
 * @since V3.0
 */

public class FollowAuditId extends AbstractId {

    public FollowAuditId(String anId) {
        super(anId);
    }

    public FollowAuditId() {
        super(Identities.genIdNone(IdPrefixes.FollowAuditIdPrefix));
    }
}