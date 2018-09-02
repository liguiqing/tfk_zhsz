package com.zhezhu.share.domain.id.assessment;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 被评者唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AssesseeId extends AbstractId {
    public AssesseeId(String anId) {
        super(anId);
    }

    public AssesseeId() {
        super(Identities.genIdNone(IdPrefixes.AssesseeIdPrefix));
    }
}