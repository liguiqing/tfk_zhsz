/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.lang.BusinessException;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzTypeNotFoundException extends BusinessException {

    public ClazzTypeNotFoundException() {
    }

    public ClazzTypeNotFoundException(String message) {
        super(message);
    }

    public ClazzTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClazzTypeNotFoundException(Throwable cause) {
        super(cause);
    }
}