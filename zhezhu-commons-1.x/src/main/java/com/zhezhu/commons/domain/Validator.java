/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.commons.domain;

/**
 * 验证器求超类
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Validator {

    private ValidationNotificationHandler notificationHandler;

    public Validator(ValidationNotificationHandler aHandler) {
        super();

        this.setNotificationHandler(aHandler);
    }

    public abstract void validate();

    protected ValidationNotificationHandler notificationHandler() {
        return this.notificationHandler;
    }

    private void setNotificationHandler(ValidationNotificationHandler aHandler) {
        this.notificationHandler = aHandler;
    }
}