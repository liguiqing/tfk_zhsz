package com.tfk.commons.persistence;

import com.tfk.commons.lang.reflect.FieldReflector;
import com.tfk.commons.lang.reflect.ProtectedConstractorReflector;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 实体映射
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class EntityMapper<E> {
    protected ProtectedConstractorReflector constractorReflector;

    protected FieldReflector fieldReflector;

    private Class<E> clazz;

    public EntityMapper(ProtectedConstractorReflector constractorReflector, FieldReflector fieldReflector,Class<E> clazz) {
        this.constractorReflector = constractorReflector;
        this.fieldReflector = fieldReflector;
        this.clazz = clazz;
    }

    public E mapper(ResultSet rs)throws SQLException{
        if(!rs.next())
            return null;

        E e = constractorReflector.newInstanceOf(this.clazz);
        fieldReflector.mapper(e,rs);
        return e;
    }

    public String insertSql(){

        return "";
    }

    public Object[] insertOrgs(E e) {
        return null;
    }
}