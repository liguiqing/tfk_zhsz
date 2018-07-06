/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.Course;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ActTeacher implements Act {

    private Course course;

    public ActTeacher(Course course){
        this.course = course;
    }

    @Override
    public Teacher actTo(Staff staff, Period period) {
        return new Teacher(staff.schoolId(), staff.staffId().id(), staff.name(), period, this.course);
    }
}