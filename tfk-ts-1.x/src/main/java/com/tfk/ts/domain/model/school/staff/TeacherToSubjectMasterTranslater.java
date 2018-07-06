/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.common.Period;

/**
 * 老师担任为学科组长
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherToSubjectMasterTranslater implements PositionTransfer {

    private String subjectName;

    private String subjectId;

    private Period period;

    public TeacherToSubjectMasterTranslater(String subjectName,String subjectId, Period period) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.period = period;
    }

    @Override
    public SubjectMaster translate(Teacher teacher) {
        return new SubjectMaster(teacher.schoolId(), teacher.name(), teacher.identity(),
                period, subjectName, subjectId);
    }
    
}