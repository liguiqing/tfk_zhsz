package com.tfk.sm.domain.model.clazz;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.IdentifiedDomainObject;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 班级史
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(of={"clazzId","grade"},callSuper = false)
@ToString(of={"clazzId","grade"})
public class ClazzHistory extends IdentifiedDomainObject {
    private ClazzId clazzId;

    private SchoolId schoolId;

    private Grade grade;

    private StudyYear studyYear;

    private String clazzName;

    public ClazzHistory(ClazzId clazzId, Grade grade,String clazzName) {
        this.clazzId = clazzId;
        this.grade = grade;
        this.studyYear = grade.studyYear();
        this.clazzName = clazzName;
    }

    public boolean sameYearOf(StudyYear year) {
        return this.studyYear.equals(year);
    }


    public boolean sameGadeOf(Grade grade) {
        return this.grade.equals(grade);
    }

    public String fullName(){
        return this.grade.name()+this.clazzName;
    }

    protected void toSchool(SchoolId schoolId){
        this.schoolId = schoolId;
    }

    protected ClazzHistory(){

    }

}