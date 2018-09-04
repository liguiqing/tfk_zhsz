package com.zhezhu.sm.domain.model.school;

import com.zhezhu.commons.domain.Entity;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.SchoolScope;
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
@EqualsAndHashCode(of={"schoolId"},callSuper = false)
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
        int size = to - from + 1;
        Grade[] grades = new Grade[size];
        int i = 0;
        while(i<size){
            grades[i] = Grade.newWithLevel(from+i);
            i++;
        }
        return grades;
    }

    public boolean hasGrade(Grade grade){
        Grade[] grades = grades();
        for(Grade g:grades){
            if(g.equals(grade)){
                return true;
            }
        }
        return false;
    }

    protected School(){}
}