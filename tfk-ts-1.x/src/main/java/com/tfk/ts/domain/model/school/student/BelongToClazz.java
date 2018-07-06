/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.student;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.clazz.ClazzId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * 学生所属班级
 * @author Liguiqing
 * @since V3.0
 */

public class BelongToClazz extends IdentifiedValueObject {

    private SchoolId schoolId;

    private ClazzId clazzId;

    private StudentId studentId;

    private Period period;

    protected BelongToClazz(SchoolId schoolId,Clazz clazz, StudentId studentId, Date starts,Date ends) {
        AssertionConcerns.assertArgumentNotNull(schoolId,"请提供学生所属学校");
        AssertionConcerns.assertArgumentNotNull(clazz,"请提供学生所属班级");
        AssertionConcerns.assertArgumentNotNull(studentId,"请提供学生");
        AssertionConcerns.assertArgumentTrue(clazz.canBeManaged(),"学生所属班级不能管理学生");
        this.schoolId =schoolId;
        this.clazzId = clazz.clazzId();
        this.studentId = studentId;
        this.period = new Period(starts,ends);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BelongToClazz that = (BelongToClazz) o;
        return Objects.equal(clazzId, that.clazzId) &&
                Objects.equal(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clazzId, studentId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clazzId", clazzId)
                .add("studentId", studentId)
                .add("period", period)
                .toString();
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public StudentId studentId() {
        return studentId;
    }

    public Period period() {
        return period;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    protected BelongToClazz(){

    }
}