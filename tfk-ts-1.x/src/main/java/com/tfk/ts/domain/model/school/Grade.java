/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school;

import com.tfk.commons.domain.ValueObject;
import com.google.common.base.Objects;

import java.util.Collection;

/**
 * 年级
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Grade extends ValueObject implements Comparable<Grade>{

    private String name;

    private GradeLevel seq;

    private StudyYear year;


    public Grade(String name, GradeLevel seq, StudyYear year) {
        this.name = name;
        this.seq = seq;
        this.year = year;
    }

    public Grade next(GradeNameGenerateStrategy nameGenerateStrategy){
        StudyYear year = this.year.nextYear();
        GradeLevel level = this.seq.next();
        String name = nameGenerateStrategy.genGradeName(level);
        if(level == null)
            return this;
        return new Grade(name, level, year);
    }

    /**
     * 年级可以教学的课程
     *
     * @return
     */
    public Collection<Course> courseOf(GradeCourseable gradeCourseable){
        return gradeCourseable.courseOf(this);
    }

    public boolean canBeTeachOrStudyOfCourse(GradeCourseable gradeCourseable,Course course){
        Collection<Course> gradeCourses = this.courseOf(gradeCourseable);
        return gradeCourses.contains(course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return seq == grade.seq &&
                Objects.equal(year, grade.year);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq, year);
    }

    public String name() {
        return name;
    }

    public String getName(){
        return this.name();
    }

    public GradeLevel seq() {
        return seq;
    }

    public StudyYear year() {
        return year;
    }

    @Override
    public int compareTo(Grade o) {
        return this.seq.getSeq() - o.seq.getSeq();
    }

    protected Grade() {

    }
}