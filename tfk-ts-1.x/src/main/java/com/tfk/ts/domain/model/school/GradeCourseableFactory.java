/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import com.tfk.commons.spring.SpringContextUtil;
import com.tfk.ts.infrastructure.strategy.DefaultGradeCourseable;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class GradeCourseableFactory {

    public static GradeCourseable lookup(SchoolId schoolId){
        //TODO
        return SpringContextUtil.getBean(DefaultGradeCourseable.class);
    }

}