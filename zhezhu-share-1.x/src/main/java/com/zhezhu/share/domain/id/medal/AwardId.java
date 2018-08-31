package com.zhezhu.share.domain.id.medal;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 授勋唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AwardId extends AbstractId {
    public AwardId(String anId) {
        super(anId);
    }

    public AwardId() {
        super(Identities.genIdNone(IdPrefixes.AwardIdPrefix));
    }
}