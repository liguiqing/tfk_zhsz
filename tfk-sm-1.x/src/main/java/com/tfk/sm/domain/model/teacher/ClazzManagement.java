package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.ManagementClazz;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ClazzManagement extends IdentifiedValueObject {
    private TeacherId teacherId;

    private ManagementClazz clazz;

    public ClazzManagement(Teacher teacher, Period period, ClazzId clazzId,Grade grade) {
        this.teacherId = teacher.teacherId();
        this.clazz = new ManagementClazz(teacher.schoolId(), clazzId, grade,period);
    }

}