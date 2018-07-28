package com.tfk.sm.domain.model.clazz;

/**
 *
 * @author Liguiqing
 * @since V3.0
 */

public class LearningClazz extends Clazz {
    @Override
    public boolean canBeStudyAt() {
        return true;
    }
    @Override
    public boolean canBeManagedAt() {
        return false;
    }


}