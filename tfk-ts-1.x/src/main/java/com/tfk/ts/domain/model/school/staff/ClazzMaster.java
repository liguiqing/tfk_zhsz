/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.clazz.ClazzId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.Objects;

/**
 * 班主任
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzMaster extends Position {

    private ClazzId clazzId;

    protected ClazzMaster(ClazzId clazzId,SchoolId schoolId,String name, String identity, Period period) {
        super(schoolId,name, identity, period);
        this.clazzId = clazzId;
    }

    @Override
    public Position renew(Period newPerid) {
        return new ClazzMaster(this.clazzId,this.schoolId(),this.name(),this.identity(),newPerid);
    }

    @Override
    public Position rename(String newName) {
        return new ClazzMaster(this.clazzId,this.schoolId(),newName,this.identity(),this.period());
    }

    @Override
    public String positionName() {
        return "班主任";
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClazzMaster that = (ClazzMaster) o;
        return Objects.equal(clazzId, that.clazzId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), clazzId);
    }
}