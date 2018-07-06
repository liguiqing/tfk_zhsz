/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.student;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.ts.domain.model.school.Course;
import com.tfk.ts.domain.model.school.Grade;
import com.tfk.ts.domain.model.school.SchoolId;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.clazz.ClazzId;
import com.tfk.ts.domain.model.school.common.Period;
import com.tfk.ts.domain.model.school.staff.Teacher;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

/**
 * 学生课程学习情况：学生什么时间在哪个班级学习什么课程
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Study extends IdentifiedValueObject {

    private StudentId studentId;

    private SchoolId schoolId;

    private ClazzId clazzId;

    private Grade grade;

    private Period period;

    private Course course;


    protected Study(StudentId studentId, SchoolId schoolId, Clazz clazz, Grade grade,
                 Course course,Date starts, Date ends) {
        AssertionConcerns.assertArgumentNotNull(studentId,"请提供学生");
        AssertionConcerns.assertArgumentNotNull(schoolId,"请提供学习学校");
        AssertionConcerns.assertArgumentNotNull(clazz,"请提供学习班级");
        AssertionConcerns.assertArgumentNotNull(course,"请提供学习课程");
        AssertionConcerns.assertArgumentTrue(clazz.canBeStudied(),"不能在非教学班级学习");

        this.studentId = studentId;
        this.schoolId = schoolId;
        this.clazzId = clazz.clazzId();
        this.grade = grade;
        this.period = new Period(starts, ends);
        this.course = course;
    }

    public boolean sameGradeCourseOf(Grade grade, Course course){
        return this.grade.equals(grade) && this.course().equals(course);
    }

    public boolean sameGradeCourseOnLineOf(Grade grade, Course course){
        return this.sameGradeCourseOf(grade,course) && !this.period.isOver();
    }

    public boolean isOver(){
        return this.period.isOver();
    }

    public Study overNow(){
        return this.over(DateUtilWrapper.today());
    }

    public Study over(Date ends){
        Study study =  new Study();
        BeanCopier copier = BeanCopier.create(Study.class,Study.class,false);
        copier.copy(this, study,null);
        Period period = new Period(this.period.starts(),ends);
        study.period = period;
        return study;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Study study = (Study) o;
        return Objects.equal(studentId, study.studentId) &&
                Objects.equal(clazzId, study.clazzId) &&
                Objects.equal(grade, study.grade) &&
                Objects.equal(course, study.course);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId, clazzId, grade, course);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("studentId", studentId)
                .add("clazzId", clazzId)
                .add("grade", grade)
                .add("course", course)
                .toString();
    }

    public StudentId studentId() {
        return studentId;
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public Grade grade() {
        return grade;
    }

    public Period period() {
        return period;
    }

    public Course course() {
        return course;
    }

    protected Study(){
        //Only 4 Persistance;
    }
}