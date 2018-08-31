package com.zhezhu.sm.domain.model.clazz;

import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository("SuperviseClazzRepository")
public interface SuperviseClazzRepository extends ClazzRepository<SuperviseClazz> {

    @Override
    default boolean supports(Clazz clazz){
        return clazz.isTypeOf(SuperviseClazz.class);
    }

    @Override
    default boolean supports(Class clazz){
        return clazz.equals(SuperviseClazz.class);
    }
}