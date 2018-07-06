/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

import com.tfk.commons.domain.AbstractId;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 人员身份标识，如学籍号，考号，身份证号
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Identity extends IdentifiedValueObject{

    private AbstractId entityId;

    private IdentityType type;

    private String value;

    private Period validity;//有效期，为空表示一直有效

    public Identity(AbstractId entityId,IdentityType type, String value) {
        this.entityId = entityId;
        this.type = type;
        this.value = value;
    }

    public Identity(AbstractId entityId,IdentityType type, String value,Period validity) {
        this.entityId = entityId;
        this.type = type;
        this.value = value;
        this.validity = validity;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.entityId,this.type, this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Identity) {
            Identity that = (Identity) o;
            return Objects.equal(this.entityId, that.entityId)
                    && Objects.equal(this.type, that.type)
                    && Objects.equal(this.value, that.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("entityId",this.entityId)
                .add("type", this.type)
                .add("value", this.value)
                .add("validity",this.validity).toString();
    }

    public IdentityType type() {
        return type;
    }

    public String value() {
        return value;
    }

    public Period validity() {
        return validity;
    }

    public AbstractId entityId() {
        return entityId;
    }

    protected Identity(){}
}