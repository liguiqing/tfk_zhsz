package com.zhezhu.sm.domain.model.clazz;

import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("UnitedClazzRepository")
public interface UnitedClazzRepository extends ClazzRepository<UnitedClazz> {

    @Override
    default boolean supports(Clazz clazz){
        return clazz.isTypeOf(UnitedClazz.class);
    }

    @Override
    default boolean supports(Class clazz){
        return clazz.equals(UnitedClazz.class);
    }
}