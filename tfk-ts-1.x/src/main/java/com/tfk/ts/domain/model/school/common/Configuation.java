/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

import com.tfk.commons.domain.ValueObject;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 学校配置信息
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Configuation extends ValueObject {

    private String name;

    private String value;

    private String description;

    public Configuation(String name, String value) {
        this(name, value, "");
    }

    public Configuation(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean sameName(String name) {
        return this.name.equals(name);
    }

    public boolean sameValue(String value) {
        return this.value.equals(value);
    }

    public boolean sameNameAndValue(String name,String value) {
        return this.sameName(name) && this.sameValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuation that = (Configuation) o;
        return Objects.equal(name, that.name) && Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name,value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("value", value)
                .add("description", description)
                .toString();
    }

    protected Configuation(){

    }

}