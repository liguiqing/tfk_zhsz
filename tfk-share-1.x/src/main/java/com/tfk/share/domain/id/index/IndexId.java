package com.tfk.share.domain.id.index;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 评价指标唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class IndexId extends AbstractId {
    public IndexId(String anId) {
        super(anId);
    }

    public IndexId() {
        super(Identities.genIdNone(IdPrefixes.IndexIdPrefix));
    }
}