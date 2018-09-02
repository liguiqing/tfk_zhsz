package com.zhezhu.share.domain.id.identityaccess;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;

import static com.zhezhu.share.domain.id.IdPrefixes.TenantIdPrefix;

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