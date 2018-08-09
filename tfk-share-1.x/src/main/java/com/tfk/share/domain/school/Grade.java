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

    private int level;

    private StudyYear studyYear;

    public Grade(String name, StudyYear studyYear,int level) {
        AssertionConcerns.assertArgumentNotNull(studyYear,"无效的学年");
        this.name = name;
        this.studyYear = studyYear;
        this.level = level;
    }

    public static Grade newWithLevel(int level){
        switch (level){
            case 1:return G1();
            case 2:return G2();
            case 3:return G3();
            case 4:return G4();
            case 5:return G5();
            case 6:return G6();
            case 7:return G7();
            case 8:return G8();
            case 9:return G9();
            case 10:return G10();
            case 11:return G11();
            case 12:return G12();
            default:return G1();
        }
    }

    public static Grade G1(){
        return G("一年级",1);
    }

    public static Grade G2(){
        return G("二年级",2);
    }

    public static Grade G3(){
        return G("三年级",3);
    }

    public static Grade G4(){
        return G("四年级",4);
    }

    public static Grade G5(){
        return G("五年级",5);
    }

    public static Grade G6(){
        return G("六年级",6);
    }

    public static Grade G7(){
        return G("七年级",7);
    }

    public static Grade G8(){
        return G("八年级",8);
    }

    public static Grade G9(){
        return G("九年级",9);
    }

    public static Grade G10(){
        return G("高一年级",10);
    }

    public static Grade G11(){
        return G("高二年级",11);
    }

    public static Grade G12(){
        return G("高三年级",12);
    }

    private static Grade G(String name,int level){
        return new Grade(name, StudyYear.now(),level);
    }

    public String name() {
        return name;
    }

    public int level(){
        return this.level;
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