/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.staff;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.ts.domain.model.school.Course;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.common.Period;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * 教师，负责某课程教学
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Teacher extends Position {

    private Course course;

    protected Teacher(SchoolId schoolId,String name, String identity, Period period, Course course) {
        super(schoolId,identity,name, period);
        this.course = course;
    }

    public Course course() {
        return course;
    }

    public boolean canTeach(Course course){
        return this.course.equals(course);
    }

    public boolean isTeaching(){
        Date today = DateUtilWrapper.today();
        return this.period().isBetween(today);
    }

    @Override
    public Position renew(Period newPerid) {
        return new Teacher(this.schoolId(),this.name(),this.identity(),newPerid,this.course);
    }

    @Override
    public Position rename(String newName) {
        return new Teacher(this.schoolId(),newName,this.identity(),this.period(),this.course);
    }

    public  <T extends Position> T transfer(PositionTransfer transfer){
        return transfer.translate(this);
    }


    @Override
    public String positionName() {
        return "教师";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equal(course, teacher.course);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), course);
    }

    protected Teacher() {
    }
}