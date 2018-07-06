/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.student;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.ts.domain.model.school.clazz.ClazzId;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface StudentRepository extends EntityRepository<Student,StudentId> {

    List<Student> studentsOf(ClazzId clazzId);
}