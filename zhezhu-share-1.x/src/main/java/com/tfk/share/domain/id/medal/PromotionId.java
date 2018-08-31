package com.tfk.share.domain.id.medal;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

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