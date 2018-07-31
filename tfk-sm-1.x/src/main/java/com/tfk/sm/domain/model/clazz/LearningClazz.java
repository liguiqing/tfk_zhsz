package com.tfk.sm.domain.model.clazz;

import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;

import java.util.Date;

/**
 *
 * @author Liguiqing
 * @since V3.0
 */

public class LearningClazz extends Clazz {

    public LearningClazz(ClazzId clazzId, SchoolId schoolId) {
        super(clazzId, schoolId);
    }

    public LearningClazz(ClazzId clazzId, SchoolId schoolId, Date openedTime) {
        super(clazzId, schoolId, openedTime);
    }

    @Override
    public boolean canBeStudyAt() {
        return true;
    }

    @Override
    public boolean canBeManagedAt() {
        return false;
    }

    protected LearningClazz() {
    }

}