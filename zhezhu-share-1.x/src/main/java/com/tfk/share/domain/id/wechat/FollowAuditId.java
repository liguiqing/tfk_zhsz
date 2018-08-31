package com.tfk.share.domain.id.wechat;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

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