/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.term;

import com.tfk.ts.domain.model.school.School;
import com.tfk.ts.domain.model.school.StudyYear;

/**
 * 学期名称生成服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface TermNameGenerateService {

    public String  genTermName(School school, StudyYear studyYear, TermOrder termOrder);
}