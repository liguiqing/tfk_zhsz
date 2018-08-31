package com.zhezhu.share.domain.id.access;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

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