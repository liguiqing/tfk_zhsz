package com.tfk.sm.domain.model.clazz;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.IdentifiedDomainObject;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;

/**
 * 班级史
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzHistory extends IdentifiedDomainObject {
    private ClazzId clazzId;

    private SchoolId schoolId;

    private Grade grade;

    private StudyYear studyYear;

    private String clazzName;

    public ClazzHistory(ClazzId clazzId, Grade grade, StudyYear studyYear, String clazzName) {
        this.clazzId = clazzId;
        this.grade = grade;
        this.studyYear = studyYear;
        this.clazzName = clazzName;
    }


    public boolean sameYearOf(StudyYear year) {
        return this.studyYear.equals(year);
    }

    protected void toSchool(SchoolId schoolId){
        this.schoolId = schoolId;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public Grade grade() {
        return grade;
    }

    public StudyYear studyYear() {
        return studyYear;
    }

    public String clazzName() {
        return clazzName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzHistory history = (ClazzHistory) o;
        return Objects.equal(clazzId, history.clazzId) &&
                Objects.equal(grade, history.grade) &&
                Objects.equal(studyYear, history.studyYear);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clazzId, grade, studyYear);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("grade", grade)
                .add("studyYear", studyYear)
                .add("clazzName", clazzName)
                .toString();
    }

    protected ClazzHistory(){

    }

}