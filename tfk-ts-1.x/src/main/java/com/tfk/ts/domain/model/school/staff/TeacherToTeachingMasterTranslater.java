/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.common.Period;

/**
 * 老师担任校领导
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherToTeachingMasterTranslater implements PositionTransfer {

    private String positionName;

    private Period period;

    public TeacherToTeachingMasterTranslater(String positionName, Period period) {
        this.positionName = positionName;
        this.period = period;
    }

    @Override
    public TeachingMaster translate(Teacher teacher) {
        return new TeachingMaster(teacher.schoolId(), teacher.name(), teacher.identity(),
                period, this.positionName);
    }
    
}