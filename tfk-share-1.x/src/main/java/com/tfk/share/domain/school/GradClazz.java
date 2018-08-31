package com.tfk.share.domain.school;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 年级的班级
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class GradClazz extends ValueObject {
    private SchoolId schoolId;

    private ClazzId clazzId;

    private Grade grade;

    public GradClazz(SchoolId schoolId, ClazzId clazzId, Grade grade) {
        this.schoolId = schoolId;
        this.clazzId = clazzId;
        this.grade = grade;
    }

    public boolean isSameGrade(Grade grade){
        return this.grade.equals(grade);
    }

    public boolean isSameYearOf(StudyYear year){
        return this.grade.isSameYearOf(year);
    }

    public StudyYear getYear() {
        return grade.getStudyYear();
    }

    protected GradClazz(){}
}