package com.tfk.share.domain.id.medal;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 勋章唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class MedalId extends AbstractId {
    public MedalId(String anId) {
        super(anId);
    }

    public MedalId() {
        super(Identities.genIdNone(IdPrefixes.MedalIdPrefix));
    }
}