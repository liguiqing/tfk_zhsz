package com.tfk.commons.lang;

/**
 * 异常转换器
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Throwables {

    public static String toString(Throwable throwable){
        return throwable.getMessage();
    }

}