/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.Objects;

/**
 * 年级负责人
 *
 * @author Liguiqing
 * @since V3.0
 */

public class GradeMaster extends Position {
    private Grade grade;

    protected GradeMaster(SchoolId schoolId, Grade grade, String name, String identity, Period period) {
        super(schoolId,name, identity, period);
        this.grade = grade;
    }


    @Override
    public String positionName() {
        return "年级主任";
    }

    @Override
    public Position renew(Period newPerid) {
        return new GradeMaster(this.schoolId(),this.grade,this.name(),this.identity(),newPerid);
    }

    @Override
    public Position rename(String newName) {
        return new GradeMaster(this.schoolId(),this.grade,newName,this.identity(),this.period());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GradeMaster that = (GradeMaster) o;
        return Objects.equal(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), grade);
    }

    public Grade grade() {
        return grade;
    }
}