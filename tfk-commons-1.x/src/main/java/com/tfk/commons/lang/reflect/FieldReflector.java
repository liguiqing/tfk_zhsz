package com.tfk.commons.lang.reflect;

import com.tfk.commons.domain.Tracer;
import com.tfk.commons.lang.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class FieldReflector {
    private static Logger logger = LoggerFactory.getLogger(FieldReflector.class);

    List<FieldMapper> mappers;

    public FieldReflector(List<FieldMapper> mappers) {
        this.mappers = mappers;
    }

    public <E> void mapper(E e, ResultSet rs) throws SQLException{
        Class clazz = e.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            for(FieldMapper mapper:mappers){
                if(field.getName().equals(mapper.nameOfClass())){
                    field.setAccessible(true);
                    try {
                        logger.debug("FieldReflect to {} with {}",e,mapper);
                        field.set(e,rs.getObject(mapper.nameOfDb()));
                    } catch (IllegalAccessException e1) {
                        logger.error(Throwables.toString(e1));
                    }
                }
            }
        }
    }
}