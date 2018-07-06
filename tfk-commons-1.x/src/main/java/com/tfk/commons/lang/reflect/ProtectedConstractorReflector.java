package com.tfk.commons.lang.reflect;

import com.tfk.commons.lang.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Protected构造器反射
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ProtectedConstractorReflector {

    private static Logger logger = LoggerFactory.getLogger(ProtectedConstractorReflector.class);

    public <E> E newInstanceOf(Class<?> clazz){

        Constructor[] ctors = clazz.getDeclaredConstructors();
        for(Constructor ctor:ctors){
            ctor.setAccessible(true);
            try {
                return (E)ctor.newInstance();
            } catch (InstantiationException e) {
                logger.error(Throwables.toString(e));
            } catch (IllegalAccessException e) {
                logger.error(Throwables.toString(e));
            } catch (InvocationTargetException e) {
                logger.error(Throwables.toString(e));
            }
        }
        return null;
    }

}