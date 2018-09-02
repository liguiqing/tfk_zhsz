package com.zhezhu.sm.domain.model.student;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.StudentId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.ManagementClazz;
import com.zhezhu.share.domain.school.StudyYear;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 学生受管班级
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ClazzManaged extends IdentifiedValueObject {
    private StudentId studentId;

    private ManagementClazz clazz;

    protected ClazzManaged(Student student, ClazzId clazzId, Period period, Grade grade) {
        this.studentId = student.getStudentId();
        this.clazz = new ManagementClazz(student.schoolId(), clazzId, grade,period);
    }

    public boolean isGradeOf(Grade grade){
        return this.clazz.isSameGrade(grade);
    }

    public boolean isStudyYearOf(StudyYear year){
        return this.clazz.isSameYearOf(year);
    }

    public ClazzId getClazzId(){
        return this.clazz.getClazzId();
    }

    protected ClazzManaged(){}
}
