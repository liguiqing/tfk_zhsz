/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ActClazzTeacher implements  Act {

    private Clazz clazz;

    private Teacher teacher;

    private Grade grade;

    public ActClazzTeacher(Clazz clazz, Teacher teacher, Grade grade) {
        this.clazz = clazz;
        this.teacher = teacher;
        this.grade = grade;
    }

    @Override
    public ClazzTeacher actTo(Staff staff, Period period) {
        return  new ClazzTeacher(staff.schoolId() ,this.clazz, staff.staffId().id(),
                staff.name(), period,this.teacher,this.grade);
    }
}