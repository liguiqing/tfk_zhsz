/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.common;

import com.tfk.commons.lang.BusinessException;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class WLTypeNotFondException extends BusinessException {

    public WLTypeNotFondException() {
    }

    public WLTypeNotFondException(String message) {
        super(message);
    }

    public WLTypeNotFondException(String message, Throwable cause) {
        super(message, cause);
    }
}