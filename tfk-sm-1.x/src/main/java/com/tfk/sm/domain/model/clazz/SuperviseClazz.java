package com.tfk.sm.domain.model.clazz;

import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;

import java.util.Date;

/**
 * 行政班
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SuperviseClazz extends Clazz {

    public SuperviseClazz(ClazzId clazzId, SchoolId schoolId) {
        super(clazzId, schoolId);
    }

    public SuperviseClazz(ClazzId clazzId, SchoolId schoolId, Date openedTime) {
        super(clazzId, schoolId, openedTime);
    }

    @Override
    public boolean canBeStudyAt() {
        return false;
    }

    @Override
    public boolean canBeManagedAt() {
        return true;
    }


    protected SuperviseClazz() {
    }

}