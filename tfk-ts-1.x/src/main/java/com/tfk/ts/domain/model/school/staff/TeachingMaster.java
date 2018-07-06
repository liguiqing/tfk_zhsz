/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.Objects;

/**
 * 其他校领导，如教务主任
 *
 * @author Liguiqing
 * @since V3.0
 */
public class TeachingMaster extends Position {

    private String positionName;

    protected TeachingMaster(SchoolId schoolId, String name, String identity, Period period,String positionName) {
        super(schoolId,identity,name, period);
        this.positionName = positionName;
    }

    @Override
    public Position renew(Period newPerid) {
        return new TeachingMaster(this.schoolId(),this.name(),this.identity(),newPerid,this.positionName);
    }

    @Override
    public Position rename(String newName) {
        return new TeachingMaster(this.schoolId(),newName,this.identity(),this.period(),this.positionName);
    }

    @Override
    public String positionName() {
        return this.positionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TeachingMaster that = (TeachingMaster) o;
        return Objects.equal(positionName, that.positionName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), positionName);
    }
}
