/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.id.subject;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;

import static com.tfk.share.domain.id.IdPrefixes.AnswerSheetIdPrefix;

/**
 * 阅卷考试科目答题卡唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class AnswerSheetId extends AbstractId {
    public AnswerSheetId(String anId) {
        super(anId);
    }

    public AnswerSheetId() {
        super(Identities.genIdNone(AnswerSheetIdPrefix));
    }

}