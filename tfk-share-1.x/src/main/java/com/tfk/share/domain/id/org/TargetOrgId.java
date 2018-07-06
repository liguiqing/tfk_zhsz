/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.id.org;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.TargetOrgIdPrefix;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class TargetOrgId extends AbstractId {

    public TargetOrgId(String anId) {
        super(anId);
    }

    public TargetOrgId() {
        this(Identities.genIdNone(TargetOrgIdPrefix));
    }

}