package com.tfk.sm.domain.model.teacher;

import com.google.common.base.Objects;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.ManagementClazz;
import com.tfk.share.domain.school.StudyYear;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzManagement extends IdentifiedValueObject {
    private TeacherId teacherId;

    private ManagementClazz clazz;

    public ClazzManagement(Teacher teacher, Period period, ClazzId clazzId,Grade grade) {
        this.teacherId = teacher.teacherId();
        StudyYear year = StudyYear.newYearsOf(period.starts());
        this.clazz = new ManagementClazz(teacher.schoolId(), clazzId, grade, year, period);
    }

    public TeacherId teacherId() {
        return teacherId;
    }

    public ManagementClazz clazz() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzManagement that = (ClazzManagement) o;
        return Objects.equal(teacherId, that.teacherId) &&
                Objects.equal(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(teacherId, clazz);
    }

    ClazzManagement(){}
}