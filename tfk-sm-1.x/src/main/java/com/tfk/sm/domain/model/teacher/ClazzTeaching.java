package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import com.tfk.share.domain.school.TeachAndStudyClazz;
import lombok.*;


/**
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode
@ToString
public class ClazzTeaching extends IdentifiedValueObject {

    private TeacherId teacherId;

    private TeachAndStudyClazz clazz;

    protected ClazzTeaching(Teacher teacher, Period period,Grade grade,
                            ClazzId clazzId, Course course) {
        this.teacherId = teacher.teacherId();
        this.clazz = new TeachAndStudyClazz(teacher.schoolId(),clazzId,grade,course,period);
    }

    protected ClazzTeaching(){}
}