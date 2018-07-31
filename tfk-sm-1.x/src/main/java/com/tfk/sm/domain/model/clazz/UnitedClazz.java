package com.tfk.sm.domain.model.clazz;

import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;

import java.util.Date;

/**
 * 教学行政混合班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class UnitedClazz extends Clazz {

    public UnitedClazz(ClazzId clazzId, SchoolId schoolId) {
        super(clazzId, schoolId);
    }

    public UnitedClazz(ClazzId clazzId, SchoolId schoolId, Date openedTime) {
        super(clazzId, schoolId, openedTime);
    }

    @Override
    public boolean canBeStudyAt() {
        return true;
    }

    @Override
    public boolean canBeManagedAt() {
        return true;
    }

    protected UnitedClazz() {
    }

}