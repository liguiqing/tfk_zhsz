/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.id.subject;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.SubjectIdPrefix;

/**
 * 阅卷考试科目唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SubjectId extends AbstractId {
    private static String prefix = "SUB";

    public SubjectId(String anId) {
        super(anId);
    }

    public SubjectId() {
        super(Identities.genIdNone(SubjectIdPrefix));
    }

}