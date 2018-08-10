package com.tfk.share.domain.id.assessment;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 评价参与者ID
 *
 * @author Liguiqing
 * @since V3.0
 */

public class CollaboratorId extends AbstractId {

    public CollaboratorId(String anId) {
        super(anId);
    }

    public CollaboratorId() {
        super(Identities.genIdNone(IdPrefixes.CollaboratorId));
    }
}