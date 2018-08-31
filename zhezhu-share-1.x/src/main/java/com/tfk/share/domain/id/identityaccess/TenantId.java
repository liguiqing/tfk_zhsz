package com.tfk.share.domain.id.identityaccess;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.TenantIdPrefix;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class TenantId extends AbstractId {

    public TenantId(String anId) {
        super(anId);
    }

    public TenantId() {
        super(Identities.genIdNone(TenantIdPrefix));
    }
}