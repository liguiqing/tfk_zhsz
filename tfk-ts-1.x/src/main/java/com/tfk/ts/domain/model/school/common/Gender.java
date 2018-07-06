/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

/**
 * 人员性别
 *
 * @author Liguiqing
 * @since V3.0
 */

public enum Gender {
    Male("男",1),Female("女",2),Unknow("无",0);

    private final String name;

    private final int value;

    Gender(String name,int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}