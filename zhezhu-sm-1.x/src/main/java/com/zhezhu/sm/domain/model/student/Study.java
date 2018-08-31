package com.zhezhu.sm.domain.model.student;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.StudentId;
import com.zhezhu.share.domain.school.Course;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.share.domain.school.TeachAndStudyClazz;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 学生学习
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Study extends IdentifiedValueObject {

    private StudentId studentId;

    private TeachAndStudyClazz clazz;

    protected Study(Student student, ClazzId clazzId, Period period, Course course, Grade grade) {
        this.studentId = student.getStudentId();
        this.clazz = new TeachAndStudyClazz(student.schoolId(),clazzId, grade,course,period);
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


    Study(){}
}