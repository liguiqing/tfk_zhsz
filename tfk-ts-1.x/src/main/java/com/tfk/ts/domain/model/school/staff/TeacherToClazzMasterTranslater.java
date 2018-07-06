/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.clazz.ClazzId;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * 老师担任为班主任
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherToClazzMasterTranslater implements PositionTransfer {

    private ClazzId clazzId;

    private Period period;

    public TeacherToClazzMasterTranslater(ClazzId clazzId, Period period) {
        this.clazzId = clazzId;
        this.period = period;
    }

    @Override
    public ClazzMaster translate(Teacher teacher) {
        return new ClazzMaster(clazzId,teacher.schoolId(),teacher.name(),teacher.identity(),this.period);
    }

}