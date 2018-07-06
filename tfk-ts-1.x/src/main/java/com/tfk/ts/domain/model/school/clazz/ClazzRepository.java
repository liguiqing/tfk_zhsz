/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.domain.EntityRepository;
import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.term.Term;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface ClazzRepository extends EntityRepository<Clazz,ClazzId> {

    List<Clazz> listGradeClazzes(Grade grade, Term term);
}