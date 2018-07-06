/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.commons.domain;

/**
 * 线程安全的实体对象超类
 * 一般情况下，领域模型中聚合根为此类的子类
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class ConcurrencySafeEntity extends Entity{
    private static final long serialVersionUID = 1L;

    private int concurrencyVersion;

    protected ConcurrencySafeEntity() {
        super();
    }

    public int concurrencyVersion() {
        return this.concurrencyVersion;
    }

    public void setConcurrencyVersion(int aVersion) {
        this.failWhenConcurrencyViolation(aVersion);
        this.concurrencyVersion = aVersion;
    }

    public void failWhenConcurrencyViolation(int aVersion) {
        if (aVersion != this.concurrencyVersion()) {
            throw new IllegalStateException(
                    "并发违规: 系统检测到过期数据， 实体对象已被修改.");
        }
    }
}