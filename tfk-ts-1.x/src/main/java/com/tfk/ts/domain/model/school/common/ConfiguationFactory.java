/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ConfiguationFactory {

    private static Set<Configable> configables = Sets.newHashSet();

    public static void register(Configable aConfigable){
        ConfiguationFactory.configables.add(aConfigable);
    }

    public Collection<Configable> getConfigables(){
        return ImmutableSet.copyOf(ConfiguationFactory.configables);
    }

    public static  Configable  getConfigablesByNameAndValue(String name,String value){

        Iterator<Configable> ables = ConfiguationFactory.configables.iterator();
        while(ables.hasNext()){
            Configable able = ables.next();
            Configuation c = able.config();
            if(c.sameNameAndValue(name,value)){
                return able;
            }
        }
        return null;
    }

    public static Collection<Configable> listConfigablesByName(String name){
        Set<Configable> set = Sets.newHashSet();
        Iterator<Configable> ables = ConfiguationFactory.configables.iterator();
        while(ables.hasNext()){
            Configable able = ables.next();
            Configuation c = able.config();
            if(c.sameName(name)){
                set.add(able);
            }
        }
        return ImmutableSet.copyOf(set);
    }

}