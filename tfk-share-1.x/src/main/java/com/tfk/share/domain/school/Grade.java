/*
 * Copyright (c) 2016,2018, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.share.domain.school;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.ValueObject;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 年级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Grade extends ValueObject {
    private String name;

    private StudyYear studyYear;

    public Grade(String name, StudyYear studyYear) {
        AssertionConcerns.assertArgumentNotNull(studyYear,"无效的学年");
        this.name = name;
        this.studyYear = studyYear;
    }

    public static Grade G1(){
        return G("一年级");
    }

    public static Grade G2(){
        return G("二年级");
    }

    public static Grade G3(){
        return G("三年级");
    }

    public static Grade G4(){
        return G("四年级");
    }

    public static Grade G5(){
        return G("五年级");
    }

    public static Grade G6(){
        return G("六年级");
    }

    public static Grade G7(){
        return G("七年级");
    }

    public static Grade G8(){
        return G("八年级");
    }

    public static Grade G9(){
        return G("九年级");
    }

    public static Grade G10(){
        return G("高一年级");
    }

    public static Grade G11(){
        return G("高二年级");
    }

    public static Grade G12(){
        return G("高三年级");
    }

    private static Grade G(String name){
        return new Grade(name, StudyYear.now());
    }

    public String name() {
        return name;
    }

    public StudyYear studyYear() {
        return studyYear;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("studyYear", studyYear)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equal(name, grade.name) &&
                Objects.equal(studyYear, grade.studyYear);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, studyYear);
    }

    //Only 4 persistence
    protected Grade(){

    }
}