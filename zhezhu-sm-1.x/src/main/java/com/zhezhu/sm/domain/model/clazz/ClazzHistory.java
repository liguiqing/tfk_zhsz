package com.zhezhu.sm.domain.model.clazz;

import com.zhezhu.commons.domain.IdentifiedDomainObject;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.StudyYear;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

    private Grade grade;

    private String clazzName;

    public ClazzHistory(ClazzId clazzId, Grade grade,String clazzName) {
        this.clazzId = clazzId;
        this.grade = grade;
        this.clazzName = clazzName;
    }

    public boolean sameYearOf(StudyYear year) {
        return this.grade.getStudyYear().equals(year);
    }

    public boolean sameGadeOf(Grade grade) {
        return this.grade.equals(grade);
    }

    public String fullName(){
        return this.grade.getName()+this.clazzName;
    }

    protected ClazzHistory(){

    }

}