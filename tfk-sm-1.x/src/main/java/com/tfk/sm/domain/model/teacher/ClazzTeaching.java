package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzTeaching extends Entity {
    private ClazzId clazzId;

    private Teacher teacher;

    private Grade grade;

    private Period period;

    private Course course;

    public ClazzTeaching(ClazzId clazzId, Teacher teacher, Grade grade, Period period, Course course) {
        this.clazzId = clazzId;
        this.teacher = teacher;
        this.grade = grade;
        this.period = period;
        this.course = course;
    }
}