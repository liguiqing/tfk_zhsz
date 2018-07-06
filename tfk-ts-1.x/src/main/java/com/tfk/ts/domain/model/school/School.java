/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 学校
 *
 * @author Liguiqing
 * @since V3.0
 */

public class School extends Entity {

    private SchoolId schoolId;

    private TenantId tenantId;

    private String name;//学校名称

    private String alias; //学校简称

    private SchoolType type;

    public School(TenantId tenantId,SchoolId schoolId,String name,String alias,SchoolType type){
        AssertionConcerns.assertArgumentNotNull(tenantId,"请提供租户唯一标识");
        AssertionConcerns.assertArgumentNotNull(schoolId,"请提供学校唯一标识");
        AssertionConcerns.assertArgumentNotNull(schoolId,"请提供校名");
        this.name = name;
        this.alias = alias==null?name:alias;
        this.type = type == null?SchoolType.Unkow:type;
        this.schoolId = schoolId;
        this.tenantId = tenantId;
    }

    public School(TenantId tenantId,SchoolId schoolId,String name,SchoolType type){
        this(tenantId, schoolId, name, name, type);
    }

    public void changeName(String newName){
        this.name = name;
    }

    public void changeAlias(String alias){
        this.alias =alias;
    }

    public List<Grade> grades (){
        GradeNameGenerateStrategy nameGenerateStrategy =
                GradeNameGenerateStrategyFactory.lookup(this.schoolId());
        SchoolType type = this.type();
        StudyYear year = StudyYear.yearOfNow();
        ArrayList<Grade> grads = Lists.newArrayList();
        for(int i=type.gradeFrom();i <= type.gradeTo();i++){
            GradeLevel level = GradeLevel.fromLevel(i);
            Grade grade = new Grade(nameGenerateStrategy.genGradeName(level),level,year);
            grads.add(grade);
        }
        return grads;
    }

    public boolean hasGrade(Grade aGrade){
        Iterator<Grade> grades = this.grades().iterator();
        while(grades.hasNext()){
            Grade grade = grades.next();
            if(grade.equals(aGrade))
                return true;
        }
        return false;
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
                .add("tenantId", tenantId)
                .add("name", name)
                .add("type", type)
                .toString();
    }

    public String name(){
        return this.name;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public TenantId tenantId() {
        return tenantId;
    }

    public SchoolType type() {
        return type;
    }

    public String alias() {
        return alias;
    }

    protected School() {
    }
}