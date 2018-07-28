package com.tfk.sm.domain.model.student;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.person.Person;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;

/**
 * 学生学习
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Study extends ValueObject {
    private PersonId studentId;

    private ClazzId clazzId;

    private Period period;

    private Course course;

    private Grade grade;

    private StudyYear year;

    public Study(PersonId studentId, ClazzId clazzId, Period period, Course course, Grade grade, StudyYear year) {
        this.studentId = studentId;
        this.clazzId = clazzId;
        this.period = period;
        this.course = course;
        this.grade = grade;
        this.year = year;
    }
}