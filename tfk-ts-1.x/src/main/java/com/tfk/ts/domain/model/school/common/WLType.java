/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;


/**
 * 班级或者科目的文理分类
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum WLType {
    Liberal{//文科
        public int getValue(){
            return 1;
        }
    },Science{//理科
        public int getValue(){
            return 2;
        }
    },None;

    public int getValue(){
        return 0;
    }

    public static WLType fromName(String name){
        for(WLType type:WLType.values()){
            if(type.name().equals(name)){
                return type;
            }
        }
        throw new WLTypeNotFondException("错误的文理分类：" + name);
    }
}