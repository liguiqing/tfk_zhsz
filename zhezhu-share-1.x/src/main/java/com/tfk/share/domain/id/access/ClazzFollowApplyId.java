package com.tfk.share.domain.id.access;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 班级关注申请唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzFollowApplyId extends AbstractId {
    public ClazzFollowApplyId(String anId) {
        super(anId);
    }

    public ClazzFollowApplyId() {
        super(Identities.genIdNone(IdPrefixes.ClazzFollowApplyIdPrefix));
    }

}