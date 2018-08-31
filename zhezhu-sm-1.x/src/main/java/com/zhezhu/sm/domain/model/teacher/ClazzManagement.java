package com.zhezhu.sm.domain.model.teacher;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.TeacherId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.ManagementClazz;
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