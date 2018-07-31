package com.tfk.sm.domain.model.teacher.teach;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.teacher.Teacher;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzTeaching extends ValueObject {

    private SchoolId schoolId;

    private ClazzId clazzId;

    private PersonId teacherId;

    private Grade grade;

    private Period period;

    private Course course;

    public ClazzTeaching(Teacher teacher, Period period, SchoolId schoolId, ClazzId clazzId,
                         Grade grade, Course course) {
        this.schoolId = schoolId;
        this.teacherId = teacher.personId();
        this.clazzId = clazzId;
        this.grade = grade;
        this.period = period;
        this.course = course;
    }

}