/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import java.util.Collection;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface GradeCourseable {

    Collection<Course> courseOf(Grade grade);
}