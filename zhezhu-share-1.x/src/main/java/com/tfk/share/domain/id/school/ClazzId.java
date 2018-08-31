package com.tfk.share.domain.id.school;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.ClazzIdPrefix;

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