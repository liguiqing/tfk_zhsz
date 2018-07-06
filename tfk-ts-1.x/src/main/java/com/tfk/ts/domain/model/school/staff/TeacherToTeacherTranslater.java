/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.Course;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * 老师教其他科目
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherToTeacherTranslater implements PositionTransfer {

    private Period period;

    private Course course;

    public TeacherToTeacherTranslater(Period period, Course course) {
        this.period = period;
        this.course = course;
    }

    @Override
    public Teacher translate(Teacher teacher) {
        Teacher newTeacher = new Teacher(teacher.schoolId(),teacher.name(),teacher.identity(),this.period,this.course);
        return newTeacher;
    }
}