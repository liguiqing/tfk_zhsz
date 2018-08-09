package com.tfk.share.domain.school;

import com.google.common.base.Objects;
import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;

/**
 * 年级的班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class GradClazz extends ValueObject {
    private SchoolId schoolId;

    private ClazzId clazzId;

    private Grade grade;

    private StudyYear year;

    public GradClazz(SchoolId schoolId, ClazzId clazzId, Grade grade, StudyYear year) {
        this.schoolId = schoolId;
        this.clazzId = clazzId;
        this.grade = grade;
        this.year = year;

    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public Grade grade() {
        return grade;
    }

    public StudyYear year() {
        return year;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradClazz termClazz = (GradClazz) o;
        return Objects.equal(schoolId, termClazz.schoolId) &&
                Objects.equal(clazzId, termClazz.clazzId) &&
                Objects.equal(grade, termClazz.grade) &&
                Objects.equal(year, termClazz.year) ;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schoolId, clazzId, grade, year);
    }

    protected GradClazz(){}
}