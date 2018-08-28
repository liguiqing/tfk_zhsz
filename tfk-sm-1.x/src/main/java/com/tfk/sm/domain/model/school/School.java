package com.tfk.sm.domain.model.school;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.Entity;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.SchoolScope;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 学校
 *
 * @author Liguiqing
 * @since V1.0
 */
@Getter
@EqualsAndHashCode(of={"schoolId"})
@ToString(of = {"schoolId","name"})
public class School extends Entity {
    private SchoolId schoolId;

    private TenantId tenantId;

    private String name;//学校名称

    private String alias; //学校简称

    private SchoolScope scope;

    public School(SchoolId schoolId, String name, SchoolScope scope) {
        this(schoolId, name, name, scope);
    }

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

    public Grade[] grades(){
        int from = this.scope.gradeFrom();
        int to = this.scope.gradeTo();
        Grade[] grades = new Grade[to - from];
        int i = 0;
        while(i<(to - from)){
            grades[i] = Grade.newWithLevel(from+i);
            i++;
        }
        return grades;
    }

    protected School(){}
}