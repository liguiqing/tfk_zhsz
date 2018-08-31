package com.tfk.share.domain.id.school;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 学生受箮经历唯一标识,即学生在行政班或者统一班的ID
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ManagedId extends AbstractId {
    public ManagedId(String anId) {
        super(anId);
    }

    public ManagedId() {
        super(Identities.genIdNone(IdPrefixes.ManagedIdPrefix));
    }
}