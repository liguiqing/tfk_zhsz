/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * 老师担任为班级学科老师
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherToClazzTeacherTranslater implements PositionTransfer {

    private Clazz clazz;

    private Period period;

    private Grade grade;

    public TeacherToClazzTeacherTranslater(Clazz clazz, Period period,Grade grade) {
        this.clazz = clazz;
        this.period = period;
        this.grade = grade;
    }

    @Override
    public ClazzTeacher translate(Teacher teacher) {
        return new ClazzTeacher(teacher.schoolId(),this.clazz,teacher.name(),teacher.identity(),this.period,teacher,grade);
    }

}