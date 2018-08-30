package com.tfk.sm.domain.model.teacher;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;

/**
 * 老师可以教授的课程
 *
 * @author Liguiqing
 * @since V3.0
 */

public class TeachedCourse extends IdentifiedValueObject {
    private TeacherId teacherId;

    private Grade grade;

    private Course course;

    private String courseAlias; //课程简称

    protected TeachedCourse(Teacher teacher,Grade grade, Course course) {
        this.teacherId = teacher.teacherId();
        this.grade = grade;
        this.course = course;
        this.courseAlias = grade.getName() + course.aliais();
    }

    public boolean sameOf(Grade grade, Course course) {
        return this.grade.equals(grade) && this.course.equals(course);
    }

    public Grade grade() {
        return grade;
    }

    public void grade(Grade grade) {
        this.grade = grade;
    }

    public Course course() {
        return course;
    }

    public void course(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeachedCourse that = (TeachedCourse) o;
        return Objects.equal(grade, that.grade) &&
                Objects.equal(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(grade, course);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("grade", grade)
                .add("course", course)
                .toString();
    }

    protected TeachedCourse(){}
}