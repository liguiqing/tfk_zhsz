package com.tfk.share.domain.school;

import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.ToString;

/**
 * 管理班级
 *
 * @author Liguiqing
 * @since V3.0
 */
@ToString(callSuper = true)
public class ManagementClazz extends GradClazz {

    private Period period;

    public ManagementClazz(SchoolId schoolId, ClazzId clazzId, Grade grade,Period period) {
        super(schoolId, clazzId, grade);
        this.period = period;
    }

    ManagementClazz() {

    }
}