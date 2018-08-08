/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons.domain;

import com.tfk.commons.AssertionConcerns;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * 对象唯一标识超类
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class AbstractId implements Identity,Serializable{
    private static final long serialVersionUID = 1L;

    private String id;

    public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            AbstractId other = (AbstractId) o;
            return Objects.equal(this.id,other.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id=" + id + "]";
    }

    protected AbstractId(String anId) {
        this();
        this.setId(anId);
    }

    protected AbstractId() {
        super();
    }


    protected void validateId(String anId) {
        // 如有必要，请在子类实现验证过程.throws a runtime exception if invalid.
    }

    private void setId(String anId) {
        AssertionConcerns.assertArgumentNotEmpty(anId, "唯一标识不能为空值.");
        AssertionConcerns.assertArgumentLength(anId, 7,37, "唯一标识字符长度为8-36位.");

        this.validateId(anId);

        this.id = anId;
    }

    public String getId(){
        return this.id();
    }
}