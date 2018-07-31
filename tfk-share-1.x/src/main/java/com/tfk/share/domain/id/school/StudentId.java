package com.tfk.share.domain.id.school;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.Identities;
import com.tfk.share.domain.id.IdPrefixes;

/**
 * 学生唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class StudentId extends AbstractId {
    public StudentId(String anId) {
        super(anId);
    }

    public StudentId() {
        super(Identities.genIdNone(IdPrefixes.StudentIdPrefix));
    }
}