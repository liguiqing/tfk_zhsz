package com.tfk.sm.domain.model.student;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.person.Gender;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.Teadent;
import com.tfk.sm.domain.model.clazz.Clazz;

import java.util.Date;
import java.util.Set;

/**
 * 学生
 *
 * @author Liguiqing
 * @since V3.0
 */

public class Student extends Teadent {
    private StudentId studentId;

    private Set<ClazzManaged> manageds;

    private Set<Study> studies;

    public Student(StudentId studentId,SchoolId schoolId,PersonId personId, String name) {
        super(schoolId,personId,name);
        this.studentId = studentId;
    }

    public Student(StudentId studentId,SchoolId schoolId,PersonId personId, String name, Date birthday, Gender gender) {
        super(schoolId,personId,name,birthday,gender);
        this.studentId = studentId;
    }

    public void studyAt(Period period, Grade grade, Clazz clazz, Course course){
        if(!clazz.canBeStudyAt())
            return;


        Study study = new Study(this,clazz.clazzId(), period, course,grade);
        if(this.studies == null)
            this.studies = Sets.newHashSet();
        this.studies.add(study);
    }

    public void managedAt(Period period, Grade grade, Clazz clazz){
        if(!clazz.canBeManagedAt())
            return;

        ClazzManaged cm = new ClazzManaged(this, clazz.clazzId(),period,grade);
        if(this.manageds == null)
            this.manageds = Sets.newHashSet();
        this.manageds.add(cm);
    }

    public StudentId studentId() {
        return studentId;
    }

    public Set<ClazzManaged> manageds() {
        return manageds;
    }

    public Set<Study> studies() {
        return studies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equal(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), studentId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("studentId", studentId)
                .toString();
    }

    protected Student(){}
}