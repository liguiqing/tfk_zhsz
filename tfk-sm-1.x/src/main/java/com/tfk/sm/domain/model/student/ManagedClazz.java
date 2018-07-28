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
 * 学生受管班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ManagedClazz extends ValueObject {
    private PersonId studentId;

    private ClazzId clazzId;

    private Period period;

    private Grade grade;

    private StudyYear year;

    public ManagedClazz(PersonId studentId, ClazzId clazzId, Period period, Grade grade, StudyYear year) {
        this.studentId = studentId;
        this.clazzId = clazzId;
        this.period = period;
        this.grade = grade;
        this.year = year;
    }

    public PersonId studentId() {
        return studentId;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public Period period() {
        return period;
    }

    public Grade grade() {
        return grade;
    }

    public StudyYear year() {
        return year;
    }
}