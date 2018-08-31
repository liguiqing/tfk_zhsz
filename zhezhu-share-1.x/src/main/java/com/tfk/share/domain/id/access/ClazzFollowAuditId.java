package com.tfk.share.domain.id.access;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 班级关注审核唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzFollowAuditId extends AbstractId {
    public ClazzFollowAuditId(String anId) {
        super(anId);
    }

    public ClazzFollowAuditId() {
        super(Identities.genIdNone(IdPrefixes.ClazzFollowAuditIdPrefix));
    }
}