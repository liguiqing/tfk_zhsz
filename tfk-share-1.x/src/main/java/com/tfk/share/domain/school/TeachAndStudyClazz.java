package com.tfk.share.domain.school;

import com.google.common.base.Objects;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;

/**
 * 教学班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeachAndStudyClazz extends GradClazz {

    private Course course;

    private Period period;

    public TeachAndStudyClazz(SchoolId schoolId, ClazzId clazzId,
                              Grade grade, StudyYear year, Course course, Period period) {
        super(schoolId, clazzId, grade, year);
        this.course = course;
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TeachAndStudyClazz that = (TeachAndStudyClazz) o;
        return Objects.equal(course, that.course) &&
                Objects.equal(period, that.period);
    }

    public Course course() {
        return course;
    }

    public Period period() {
        return period;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), course, period);
    }

    TeachAndStudyClazz(){}
}