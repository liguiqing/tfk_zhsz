package com.tfk.sm.domain.model.teacher;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.Teadent;
import com.tfk.sm.domain.model.clazz.Clazz;

import java.util.Set;

/**
 * 教师
 *
 * @author Liguiqing
 * @since V3.0
 */
public class Teacher extends Teadent {
    private TeacherId teacherId;

    private Set<TeachedCourse> courses;

    private Set<ClazzTeaching> teachings;

    private Set<ClazzManagement> manages;

    public Teacher(TeacherId teacherId,PersonId personId,SchoolId schoolId, String name) {
        super(schoolId, personId, name);
        this.teacherId = teacherId;
    }

    public void teachingAt(Period period, Grade grade, Clazz clazz, Course course){
        if(!clazz.canBeStudyAt())
            return;

        if(!this.canTeach(grade,course))
            return;

        ClazzTeaching ct = new ClazzTeaching(this, period,grade,clazz.getClazzId(), course);
        if(this.teachings == null)
            this.teachings = Sets.newHashSet();
        this.teachings.add(ct);
    }

    public void managementAt(Period period, Grade grade, Clazz clazz){
        if(!clazz.canBeManagedAt())
            return;

        ClazzManagement cm = new ClazzManagement(this, period,clazz.getClazzId(),grade);
        if(this.manages == null)
            this.manages = Sets.newHashSet();
        this.manages.add(cm);
    }

    public void addCourse(Grade grade, Course course){
        if(this.courses == null)
            this.courses = Sets.newHashSet();
        TeachedCourse tc = new TeachedCourse(this,grade, course);
        this.courses.add(tc);
    }

    public boolean canTeach(Grade grade, Course course){
        //TODO
        return true;
//        if(this.courses == null)
//            return false;
//        TeachedCourse tc = new TeachedCourse(this,grade, course);
//        return this.courses.contains(tc);
    }

    public TeacherId teacherId() {
        return teacherId;
    }

    public Set<TeachedCourse> courses() {
        return ImmutableSet.copyOf(this.courses);
    }

    public Set<ClazzTeaching> teachings() {
        return ImmutableSet.copyOf(this.teachings);
    }

    public Set<ClazzManagement> manages() {
        return ImmutableSet.copyOf(this.manages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equal(teacherId, teacher.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), teacherId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("teacherId", teacherId)
                .toString();
    }

    protected Teacher(){}
}