package com.tfk.sm.domain.model.teacher.management;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.Entity;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.domain.model.teacher.Teacher;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzManagement extends Entity {
    private SchoolId schoolId;

    private ClazzId clazzId;

    private PersonId teacherId;

    private Grade grade;

    private Period period;

    public ClazzManagement(Teacher teacher, Period period, SchoolId schoolId, ClazzId clazzId,
                         Grade grade) {
        this.schoolId = schoolId;
        this.teacherId = teacher.personId();
        this.clazzId = clazzId;
        this.grade = grade;
        this.period = period;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public void clazzId(ClazzId clazzId) {
        this.clazzId = clazzId;
    }

    public Grade grade() {
        return grade;
    }

    public void grade(Grade grade) {
        this.grade = grade;
    }

    public Period period() {
        return period;
    }

    public void period(Period period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzManagement that = (ClazzManagement) o;
        return Objects.equal(clazzId, that.clazzId) &&
                Objects.equal(grade, that.grade) &&
                Objects.equal(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clazzId, grade, period);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clazzId", clazzId)
                .add("grade", grade)
                .add("period", period)
                .toString();
    }
}