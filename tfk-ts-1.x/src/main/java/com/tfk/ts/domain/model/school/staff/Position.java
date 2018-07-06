/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.Objects;

/**
 * 学校职务
 * 如果校长，年级主任，学科老师等
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Position extends IdentifiedValueObject implements Comparable<Position> {

    private SchoolId schoolId;

    private Period period;

    private String name; //教职工姓名

    private String identity; //唯一身份标识，关联到staff.Staff时就是Staff.staffId

    protected Position(SchoolId schoolId, String name, String identity, Period period) {
        AssertionConcerns.assertArgumentNotNull(schoolId,"请提供学校");
        AssertionConcerns.assertArgumentNotNull(name,"请提供姓名");
        AssertionConcerns.assertArgumentNotNull(identity,"请提供教职工唯一标识");
        this.schoolId = schoolId;
        this.period = period;
        this.name = name;
        this.identity = identity;
    }

    public abstract String positionName();

    public abstract Position renew(Period newPerid);

    public abstract Position rename(String newName);

    public boolean sameSchoolOf(SchoolId schoolId){
        return this.schoolId.equals(schoolId);
    }

    public boolean isOnPosition(){
        return !this.period.isOver();
    }

    @Override
    public int compareTo(Position duty) {
        if(this.identity.equals(duty.identity)){
            return duty.period().starts().compareTo(this.period().starts());
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position duty = (Position) o;
        return Objects.equal(schoolId, duty.schoolId) &&
                Objects.equal(identity, duty.identity) && Objects.equal(this.period,duty.period);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schoolId, identity,period);
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public Period period() {
        return period;
    }

    public String name() {
        return name;
    }

    public String identity() {
        return identity;
    }

    protected Position(){

    }
}