package com.zhezhu.share.domain.id.school;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;

import static com.zhezhu.share.domain.id.IdPrefixes.ClazzIdPrefix;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzId extends AbstractId {
    public ClazzId(String anId) {
        super(anId);
    }

    public ClazzId() {
        super(Identities.genIdNone(ClazzIdPrefix));
    }
}