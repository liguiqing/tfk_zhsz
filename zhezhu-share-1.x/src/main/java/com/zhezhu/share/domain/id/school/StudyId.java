package com.zhezhu.share.domain.id.school;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 学生学习经历唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class StudyId extends AbstractId {
    public StudyId(String anId) {
        super(anId);
    }

    public StudyId() {
        super(Identities.genIdNone(IdPrefixes.StudyIdPrefix));
    }
}