package com.zhezhu.share.domain.id.school;

import com.zhezhu.commons.domain.AbstractId;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.IdPrefixes;

/**
 * 教师唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherId extends AbstractId {
    public TeacherId(String anId) {
        super(anId);
    }

    public TeacherId() {
        super(Identities.genIdNone(IdPrefixes.TeacherIdPrefix));
    }
}