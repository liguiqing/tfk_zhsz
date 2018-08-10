package com.tfk.sm.domain.model.student;

import com.google.common.base.Objects;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.ManagementClazz;
import com.tfk.share.domain.school.StudyYear;

/**
 * 学生受管班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzManaged extends IdentifiedValueObject {
    private StudentId studentId;

    private ManagementClazz clazz;

    protected ClazzManaged(Student student, ClazzId clazzId, Period period, Grade grade) {
        this.studentId = student.studentId();
        StudyYear year = StudyYear.newYearsOf(period.starts());
        this.clazz = new ManagementClazz(student.schoolId(), clazzId, grade, year, period);
    }

    public StudentId studentId() {
        return studentId;
    }

    public ManagementClazz clazz() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzManaged that = (ClazzManaged) o;
        return Objects.equal(studentId, that.studentId) &&
                Objects.equal(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId, clazz);
    }

    protected ClazzManaged(){}
}
