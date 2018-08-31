/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.port.adapter.persistence.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CoreLogging;
import org.hibernate.usertype.LoggableUserType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.jboss.logging.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * 使用枚举属性进行映射
 *　xml配置：
 * <pre>
 * <property name="gender" column="gender" >
       <type name="FieldEnumType">
           <param name="enumClass">com.zhezhu.ts.domain.exam.school.common.Gender</param>
           <param name="field">name</param>
       </type>
   </property>
 *</pre>
 *
 * @author Liguiqing
 * @since V3.0
 */

public class FieldEnumType implements UserType, ParameterizedType,LoggableUserType, Serializable {
    private static final Logger LOG = CoreLogging.logger(FieldEnumType.class);

    private Class<Enum<?>> enumClass;//枚举类
    private Field typeField;//映射的类属性
    private Method method;


    @SuppressWarnings("unchecked")
    @Override
    public void setParameterValues(Properties parameters) {
        final boolean traceEnabled = LOG.isTraceEnabled();
        if (parameters != null) {
            try {
                enumClass = (Class<Enum<?>>) Class.forName(parameters.get("enumClass").toString());
                String field = parameters.get("field").toString();
                typeField=enumClass.getDeclaredField(field);
                typeField.setAccessible(true);
                try {
                    method=enumClass.getDeclaredMethod("get"+ StringUtils.capitalize(field), typeField.getDeclaringClass());
                } catch (NoSuchMethodException e) {
                    if(traceEnabled){
                        LOG.trace(e);
                    }
                }

            } catch (SecurityException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            } catch (ClassNotFoundException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            } catch (NoSuchFieldException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            }
        }

    }

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }

    @Override
    public Class<?> returnedClass() {
        return enumClass;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return (x!=null && y!=null) ? x.equals(y) : false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {

        return x.hashCode();
    }


    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        final boolean traceEnabled = LOG.isTraceEnabled();
        String value = rs.getString(names[0]);
        Object returnVal = null;

        if (value == null)
            return null;
        else {
            try {
                if(method!=null){
                    return method.invoke(null, value);
                }
                Enum<?>[] consts=enumClass.getEnumConstants();
                for (Enum<?> enum1 : consts) {
                    Object type= typeField.get(enum1);
                    if(value.equals(type)){
                        return enum1;
                    }
                }
            } catch (IllegalArgumentException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            } catch (IllegalAccessException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            } catch (Exception e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            }
        }
        return returnVal;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        final boolean traceEnabled = LOG.isTraceEnabled();
        if (value == null) {
            st.setObject(index, null);
        } else {
            try {
                st.setObject(index, typeField.get(value));
            } catch (IllegalArgumentException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            } catch (IllegalAccessException e) {
                if(traceEnabled){
                    LOG.trace(e);
                }
            }
        }

    }



    /**
     * Deep copy method
     */
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable))
            return (Serializable) deepCopy;

        return null;
    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return deepCopy(cached);
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public String toLoggableString(Object value, SessionFactoryImplementor factory) {
        return value.toString();
    }
}