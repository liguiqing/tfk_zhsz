/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.id.exam;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.ProjectIdPrefix;

/**
 * 阅卷项目唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ProjectId extends AbstractId {

    public ProjectId(String anId) {
        super(anId);
    }

    public ProjectId() {
        super(Identities.genIdNone(ProjectIdPrefix));
    }


}