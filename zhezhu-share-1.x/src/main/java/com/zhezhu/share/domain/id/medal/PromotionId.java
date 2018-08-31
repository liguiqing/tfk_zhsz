package com.zhezhu.share.domain.id.medal;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class PromotionId extends AbstractId {
    public PromotionId(String anId) {
        super(anId);
    }

    public PromotionId() {
        super(Identities.genIdNone(IdPrefixes.PromotionIdPrefix));
    }
}