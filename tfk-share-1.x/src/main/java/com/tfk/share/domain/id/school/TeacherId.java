package com.tfk.share.domain.id.school;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

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