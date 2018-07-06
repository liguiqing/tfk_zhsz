/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ActGradeMaster implements Act {

    private Grade grade;

    public ActGradeMaster(Grade grade) {
        this.grade = grade;
    }

    @Override
    public GradeMaster actTo(Staff staff, Period period) {
        return new GradeMaster(staff.schoolId(), this.grade,staff.staffId().id(),
                staff.name(), period);
    }
}