/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.common.Period;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ActHeadMaster implements Act {

    @Override
    public HeadMaster actTo(Staff staff, Period period) {
        return new HeadMaster(staff.schoolId(), staff.staffId().id(), staff.name(), period);
    }
}