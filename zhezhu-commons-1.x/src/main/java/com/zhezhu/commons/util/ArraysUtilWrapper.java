package com.zhezhu.commons.util;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ArraysUtilWrapper {

    public static <E> boolean isNotNullAndNotEmpty(E[] es){
        return !isNullOrEmpty(es);
    }

    public static <E> boolean isNullOrEmpty(E[] es){
        return (es == null) || es.length == 0;
    }

    public static <E> boolean hasElements(E[] es){
        if((es != null) && es.length > 0){
            for(E e:es){
                if(e != null)
                    return true;
            }
        }
        return false;
    }

}