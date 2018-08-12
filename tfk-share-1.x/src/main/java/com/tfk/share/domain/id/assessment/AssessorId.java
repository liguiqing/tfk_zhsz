package com.tfk.share.domain.id.assessment;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 主评者唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AssessorId extends AbstractId {
    public AssessorId(String anId) {
        super(anId);
    }

    public AssessorId() {
        super(Identities.genIdNone(IdPrefixes.AssessorIdPrefix));
    }
}