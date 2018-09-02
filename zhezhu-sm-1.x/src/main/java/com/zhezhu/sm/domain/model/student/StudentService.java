package com.zhezhu.sm.domain.model.student;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.school.Course;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.clazz.LearningClazzRepository;
import com.zhezhu.sm.domain.model.school.School;
import com.zhezhu.sm.domain.model.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 学习服务,控制学生入/转校,入/转班
 *
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class StudentService {

    @Autowired
    private LearningClazzRepository learningClazzRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public StudentService(){}

    public StudentService(LearningClazzRepository learningClazzRepository, StudentRepository studentRepository, SchoolRepository schoolRepository) {
        this.learningClazzRepository = learningClazzRepository;
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
    }

    public Study studyAt(Clazz clazz, Student student, Course course){

        AssertionConcerns.assertArgumentFalse(clazz.canBeStudyAt(),"行政班不能学习课程");

        List<Course> studiedCourses = null;//clazzRepository.findCanBeStudedCourses(clazz.clazzId());
        boolean canStudyHere = studiedCourses.contains(course);
        AssertionConcerns.assertArgumentFalse(canStudyHere,"班级目前未开能此课程:"+course.name());

        School school = schoolRepository.loadOf(clazz.getSchoolId());

        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();
        StudyYear year = StudyYear.now();

        //Study study =  new Study(student.personId(), clazz.clazzId(), period, course, grade,year);
        //List<Study> studies =  studentRepository.findStudentStudies(student);
        //boolean hasStuded = studies.size() == 0?false:studies.contains(study);
        //AssertionConcerns.assertArgumentFalse(hasStuded,"课程不能重复学习:"+course.name());
        return null;
    }

    public ClazzManaged managedAt(Clazz clazz, Student student){
        AssertionConcerns.assertArgumentFalse(clazz.canBeManagedAt(),"教学班不能管理学生");

        School school = schoolRepository.loadOf(clazz.getSchoolId());

        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();
        StudyYear year = StudyYear.now();

        return null;//new ClazzManaged(student.personId(), clazz.clazzId(),period,grade,year);
    }
}