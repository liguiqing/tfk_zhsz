/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.common.Period;

/**
 * 职位扮演
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface Act {

    Position actTo(Staff staff, Period period);
}