/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school.data;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class SchoolData {

    private String name;//学校名称

    private String alias; //学校简称

    private String type; //学校类型：小学，初中，高中，完中（七年级到高三），完校（一年级到高三）


    public SchoolData(String name, String alias, String type) {
        this.name = name;
        this.alias = alias;
        this.type = type;
    }

    public SchoolData() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}