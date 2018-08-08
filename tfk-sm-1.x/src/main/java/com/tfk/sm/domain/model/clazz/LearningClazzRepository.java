package com.tfk.sm.domain.model.clazz;

import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("LearningClazzRepository")
public interface LearningClazzRepository extends ClazzRepository<LearningClazz> {

    @Override
    default boolean supports(Clazz clazz){
        return clazz.isTypeOf(LearningClazz.class);
    }

    @Override
    default boolean supports(Class clazz){
        return clazz.equals(LearningClazz.class);
    }
}