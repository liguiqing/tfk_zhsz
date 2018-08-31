/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons.domain;

/**
 * 须独立持久化的值对象
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class IdentifiedValueObject extends IdentifiedDomainObject {
    private static final long serialVersionUID = 1L;

    protected IdentifiedValueObject() {
        super();
    }
}