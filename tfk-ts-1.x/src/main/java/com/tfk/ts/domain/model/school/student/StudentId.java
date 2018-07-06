/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.student;

import com.tfk.commons.domain.AbstractId;

/**
 * 学生唯一标识
 *
 * @author Liguiqing
 * @since V3.0
 */

public class StudentId extends AbstractId {
    public StudentId(String anId) {
        super(anId);
    }

    public StudentId() {
    }
}