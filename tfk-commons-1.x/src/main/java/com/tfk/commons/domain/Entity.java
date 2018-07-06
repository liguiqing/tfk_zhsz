/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons.domain;

/**
 * 领域模型中的实体对象超类
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Entity extends IdentifiedDomainObject{
    private static final long serialVersionUID = 1L;

    protected Entity() {
        super();
    }
}