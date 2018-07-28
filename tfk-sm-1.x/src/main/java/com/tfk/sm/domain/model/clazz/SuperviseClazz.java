package com.tfk.sm.domain.model.clazz;

/**
 * 行政班
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SuperviseClazz extends Clazz {

    @Override
    public boolean canBeStudyAt() {
        return false;
    }
    @Override
    public boolean canBeManagedAt() {
        return true;
    }

}