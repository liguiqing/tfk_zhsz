/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.commons.AssertionConcerns;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.common.Period;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ActClazzMaster implements Act{

    private Clazz clazz;

    public ActClazzMaster(Clazz clazz) {
        this.clazz = clazz;
    }

    @Override
    public ClazzMaster actTo(Staff staff, Period period) {
        AssertionConcerns.assertArgumentTrue(this.clazz.canBeManaged(),"教学班不能安排班主任");
        return new ClazzMaster(this.clazz.clazzId(),staff.schoolId(),staff.staffId().id(),staff.name(),period);
    }
}