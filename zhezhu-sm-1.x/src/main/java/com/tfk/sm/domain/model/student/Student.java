package com.tfk.sm.domain.model.student;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.tfk.commons.util.CollectionsUtilWrapper;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.person.Gender;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import com.tfk.share.domain.school.Teadent;
import com.tfk.sm.domain.model.clazz.Clazz;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

/**
 * 学生
 *
 * @author Liguiqing
 * @since V3.0
 */
@EqualsAndHashCode(of={"studentId"},callSuper = false)
@ToString(of={"studentId"})
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

        Study study = new Study(this,clazz.getClazzId(), period, course,grade);
        if(this.studies == null)
            this.studies = Sets.newHashSet();
        this.studies.add(study);
    }

    public void managedAt(Period period, Grade grade, Clazz clazz){
        if(!clazz.canBeManagedAt())
            return;

        ClazzManaged cm = new ClazzManaged(this, clazz.getClazzId(),period,grade);
        if(this.manageds == null)
            this.manageds = Sets.newHashSet();
        this.manageds.add(cm);
    }

    public ClazzId currentManagedClazz(){
        if(CollectionsUtilWrapper.isNullOrEmpty(this.manageds))
            return null;

        StudyYear year = StudyYear.now();
        for (ClazzManaged cm:this.manageds){
            if(cm.isStudyYearOf(year)){
                return cm.getClazzId();
            }
        }

        return null;
    }

    public ClazzId managedClazzOf(Grade grade){
        if(CollectionsUtilWrapper.isNullOrEmpty(this.manageds))
            return null;

        for (ClazzManaged cm:this.manageds){
            if(cm.isGradeOf(grade))
                return cm.getClazzId();
        }
        return null;
    }

    public ClazzId currentStudyClazz(){
        if(CollectionsUtilWrapper.isNullOrEmpty(this.studies))
            return null;

        StudyYear year = StudyYear.now();
        for (Study study:this.studies){
            if(study.isStudyYearOf(year)){
                return study.getClazzId();
            }
        }

        return null;
    }

    public ClazzId studyClazzOf(Grade grade){
        if(CollectionsUtilWrapper.isNullOrEmpty(this.studies))
            return null;

        for (Study study:this.studies){
            if(study.isGradeOf(grade))
                return study.getClazzId();
        }
        return null;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Set<ClazzManaged> getManageds() {
        return ImmutableSet.copyOf(manageds);
    }

    public Set<Study> getStudies() {
        return ImmutableSet.copyOf(studies);
    }

    protected Student(){}
}