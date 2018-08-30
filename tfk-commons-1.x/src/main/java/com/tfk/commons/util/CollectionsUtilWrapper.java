package com.tfk.commons.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合处理包装器
 *
 * @author Liguiqing
 * @since V3.0
 */

public class CollectionsUtilWrapper {

    public static <E> boolean isNotNullAndNotEmpty(Collection<E> collection){
        return !isNullOrEmpty(collection);
    }

    public static <E> boolean isNullOrEmpty(Collection<E> collection){
        return (collection == null) || collection.size() == 0;
    }

    public static <E> boolean hasElements(Collection<E> collection){
        return (collection != null) && collection.size() > 0;
    }

}