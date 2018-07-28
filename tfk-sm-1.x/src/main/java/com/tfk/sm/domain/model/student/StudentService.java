package com.tfk.sm.domain.model.student;

import com.tfk.commons.AssertionConcerns;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.StudyYear;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzRepository;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;

import java.util.List;

/**
 * 学习服务,控制学生入/转校,入/转班
 *
 * @author Liguiqing
 * @since V3.0
 */

public class StudentService {

    private ClazzRepository clazzRepository;

    private StudentRepository studentRepository;

    private SchoolRepository schoolRepository;

    public StudentService(ClazzRepository clazzRepository, StudentRepository studentRepository, SchoolRepository schoolRepository) {
        this.clazzRepository = clazzRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
    }

    public Study studyAt(Clazz clazz, Student student, Course course){

        AssertionConcerns.assertArgumentFalse(clazz.canBeStudyAt(),"行政班不能学习课程");

        List<Course> studiedCourses = clazzRepository.findCanBeStudedCourses(clazz.clazzId());
        boolean canStudyHere = studiedCourses.contains(course);
        AssertionConcerns.assertArgumentFalse(canStudyHere,"班级目前未开能此课程:"+course.name());

        School school = schoolRepository.loadOfId(clazz.schoolId());

        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();
        StudyYear year = StudyYear.now();

        Study study =  new Study(student.personId(), clazz.clazzId(), period, course, grade,year);
        List<Study> studies =  studentRepository.findStudentStudies(student);
        boolean hasStuded = studies.size() == 0?false:studies.contains(study);
        AssertionConcerns.assertArgumentFalse(hasStuded,"课程不能重复学习:"+course.name());
        return study;
    }

    public ManagedClazz managedAt(Clazz clazz, Student student){
        AssertionConcerns.assertArgumentFalse(clazz.canBeManagedAt(),"教学班不能管理学生");

        School school = schoolRepository.loadOfId(clazz.schoolId());

        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();
        StudyYear year = StudyYear.now();

        return new ManagedClazz(student.personId(), clazz.clazzId(),period,grade,year);
    }
}