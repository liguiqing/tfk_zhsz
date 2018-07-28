package com.tfk.sm.domain.model.clazz;

/**
 * 教学行政混合班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class UnitedClazz extends Clazz {


    @Override
    public boolean canBeStudyAt() {
        return true;
    }

    @Override
    public boolean canBeManagedAt() {
        return true;
    }
}