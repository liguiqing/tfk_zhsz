/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.student;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.tfk.ts.domain.model.school.*;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.common.Gender;
import com.tfk.ts.domain.model.school.common.Identity;
import com.tfk.ts.domain.model.school.common.Period;
import com.tfk.ts.domain.model.school.staff.Teacher;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 学生
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Student extends Entity {

    private SchoolId schoolId;

    private StudentId studentId;

    private String name;

    private Gender gender;

    private Period period;

    private Set<StudentIdentity> ids;

    private Set<Study> studies;

    private Set<BelongToClazz> belongTos;

    public Student(SchoolId schoolId,StudentId studentId, String name) {
        this(schoolId, studentId, name, studentId.id(), Gender.Unknow);
    }

    public Student(SchoolId schoolId,StudentId studentId, String name,String identity) {
        this(schoolId, studentId, name, identity, Gender.Unknow);
    }

    public Student(SchoolId schoolId,StudentId studentId, String name,String identity,Gender gender) {
        AssertionConcerns.assertArgumentNotNull(schoolId, "请提供学生所在学校");
        AssertionConcerns.assertArgumentNotNull(studentId, "请提供学生唯一标识");
        AssertionConcerns.assertArgumentNotNull(name, "请提供学生姓名");
        AssertionConcerns.assertArgumentLength(name, 1, 32, "学生姓名为1到32个字符");

        this.schoolId = schoolId;
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.ids = Sets.newHashSet();
        this.studies = Sets.newHashSet();
        this.belongTos = Sets.newHashSet();
    }

    public void addStudy(Clazz clazz, Grade grade,Course course, Date starts, Date ends){
        checkCourseCanStudy(grade,course);
        AssertionConcerns.assertArgumentFalse(isStuding(grade,course),
                grade.name()+course.name()+"正在其他班级学习");

        addStudy(new Study(this.studentId,this.schoolId, clazz, grade, course, starts, ends));
    }

    public void changeStudyClazzOfCourse(Clazz clazz, Grade grade,Course course, Date starts, Date ends){
        checkCourseCanStudy(grade,course);
        Study old = getStudyOnLine(grade,course);
        if(old != null){
            addStudy(old.overNow());
        }
        addStudy(new Study(this.studentId,this.schoolId, clazz, grade, course, starts, ends));
    }

    private void addStudy(Study aStudy){
        if(this.studies.contains(aStudy)){
            studies.remove(aStudy);
        }
        this.studies.add(aStudy);
    }

    private boolean isStuding(Grade grade,Course course){
        Study study  = getStudyOnLine(grade,course);
        if(study != null)
            return true;
        return false;
    }

    /**
     * 取某年级的学习某课程
     *
     * @param grade
     * @param course
     * @return
     */
    public Study getStudyOnLine(Grade grade,Course course){
        if(hasStudies()){
            Iterator<Study> it = this.studies.iterator();
            while(it.hasNext()){
                Study study = it.next();
                if(study.sameGradeCourseOnLineOf(grade,course)){
                    return study;
                }
            }
        }
        return null;
    }

    private void checkCourseCanStudy(Grade grade,Course course){
        GradeCourseable gradeCourseable = GradeCourseableFactory.lookup(this.schoolId());
        boolean canBeStudied = grade.canBeTeachOrStudyOfCourse(gradeCourseable, course);
        AssertionConcerns.assertArgumentTrue(canBeStudied,grade.name()+"不能学习"+course.name());

    }

    private boolean hasStudies(){
        return this.studies != null && this.studies.size() > 0;
    }

    public void changeManagedClazz(Clazz clazz, Date starts, Date ends){
        BelongToClazz belongTo = new BelongToClazz(this.schoolId,clazz, this.studentId, starts, ends);
        if(this.belongTos.contains(belongTo)){
            this.belongTos.remove(belongTo);
        }
        this.belongTos.add(belongTo);
    }

    public void addId(StudentIdentity sid) {
        AssertionConcerns.assertArgumentNotNull(sid, "请提供学生唯一标识");
        this.ids.add(sid);
    }

    public void chagePeriod(Date starts,Date ends){
        this.period = new Period(starts, ends);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Student) {
            Student that = (Student) o;
            return Objects.equal(this.studentId, that.studentId);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.name).add("id", this.studentId).toString();
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public StudentId studentId() {
        return studentId;
    }

    public String name() {
        return name;
    }

    public Gender gender() {
        return gender;
    }

    public Set<StudentIdentity> ids() {
        return ImmutableSet.copyOf(ids);
    }

    public Set<Study> studies() {
        return ImmutableSet.copyOf(studies);
    }

    public Set<BelongToClazz> belongTos() {
        return ImmutableSet.copyOf(belongTos);
    }

    public Period period() {
        return period;
    }
}