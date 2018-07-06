/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.Objects;


/**
 * 学科负责人,也称科目组长，在学校里某个学科的教学管理工作，是跨年级的
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SubjectMaster extends Position {

    private String subjectId;

    private String subjectName;

    protected SubjectMaster(SchoolId schoolId, String name, String identity, Period period,
                         String subjectName,String subjectId) {
        super(schoolId,name, identity, period);
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    @Override
    public String positionName() {
        return "学科组长";
    }

    @Override
    public Position renew(Period newPerid) {
        return new SubjectMaster(this.schoolId(),this.name(),this.identity(),newPerid,this.subjectId,this.subjectName);
    }

    @Override
    public Position rename(String newName) {
        return new SubjectMaster(this.schoolId(),newName,this.identity(),this.period(),this.subjectId,this.subjectName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubjectMaster that = (SubjectMaster) o;
        return Objects.equal(subjectId, that.subjectId) &&
                Objects.equal(subjectName, that.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), subjectId, subjectName);
    }

    public String subjectId() {
        return subjectId;
    }

    public String subjectName() {
        return subjectName;
    }
}