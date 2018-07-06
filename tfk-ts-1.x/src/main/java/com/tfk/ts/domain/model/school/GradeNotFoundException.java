/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import com.tfk.commons.lang.BusinessException;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class GradeNotFoundException extends BusinessException {

    public GradeNotFoundException() {
    }

    public GradeNotFoundException(String message) {
        super(message);
    }

    public GradeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}