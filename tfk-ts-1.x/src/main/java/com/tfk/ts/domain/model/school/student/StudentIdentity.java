/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.student;

import com.tfk.ts.domain.model.school.common.Identity;
import com.tfk.ts.domain.model.school.common.IdentityType;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class StudentIdentity extends Identity {


    public StudentIdentity(StudentId studentId,IdentityType type, String value) {
        super(studentId,type, value);
    }

    public StudentIdentity(StudentId studentId,IdentityType type, String value, Period validity) {
        super(studentId,type, value, validity);
    }
}