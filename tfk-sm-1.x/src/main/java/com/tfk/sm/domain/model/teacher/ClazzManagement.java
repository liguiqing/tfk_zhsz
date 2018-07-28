package com.tfk.sm.domain.model.teacher;

import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.school.Grade;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzManagement {
    private ClazzId clazzId;

    private Teacher teacher;

    private Grade grade;

    private Period period;

    public ClazzManagement(ClazzId clazzId, Teacher teacher, Grade grade, Period period) {
        this.clazzId = clazzId;
        this.teacher = teacher;
        this.grade = grade;
        this.period = period;
    }
}