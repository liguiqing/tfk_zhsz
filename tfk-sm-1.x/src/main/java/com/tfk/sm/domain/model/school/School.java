package com.tfk.sm.domain.model.school;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.Entity;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.SchoolScope;

/**
 * 学校
 *
 * @author Liguiqing
 * @since V1.0
 */

public class School extends Entity {
    private SchoolId schoolId;

    private TenantId tenantId;

    private String name;//学校名称

    private String alias; //学校简称

    private SchoolScope scope;

    public School(SchoolId schoolId, String name, String alias, SchoolScope scope) {
        this.schoolId = schoolId;
        this.name = name;
        this.alias = alias;
        this.scope = scope;
        this.tenantId = new TenantId();
    }

    public Period  getThisTermStartsAndEnds(){
        //TODO
        return new Period(DateUtilWrapper.now(), DateUtilWrapper.tomorrow());
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public void schoolId(SchoolId schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return Objects.equal(schoolId, school.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(schoolId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("schoolId", schoolId)
                .add("name", name)
                .add("alias", alias)
                .toString();
    }

    protected School(){};
}