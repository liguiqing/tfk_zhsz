package com.tfk.sm.domain.model.teacher;

import com.tfk.commons.AssertionConcerns;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzRepository;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherService {

    private ClazzRepository clazzRepository;

    private SchoolRepository schoolRepository;

    private TeacherRepository teacherRepository;

    public TeacherService(ClazzRepository clazzRepository, SchoolRepository schoolRepository, TeacherRepository teacherRepository) {
        this.clazzRepository = clazzRepository;
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
    }

    public ClazzTeaching teachingAt(Teacher teacher, Clazz clazz, Course course){
        AssertionConcerns.assertArgumentFalse(clazz.canBeStudyAt(),"行政班不能教授课程");

        School school = schoolRepository.loadOfId(clazz.schoolId());
        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();

        ClazzTeaching teaching = new ClazzTeaching(clazz.clazzId(),teacher,grade, period, course);

        List<ClazzTeaching> hasTeaching = teacherRepository.findTeaching(teacher.personId());
        boolean canBeTeached = hasTeaching.size()>0?hasTeaching.contains(teaching):true;
        AssertionConcerns.assertArgumentFalse(canBeTeached,"已在本班教授课程");

        return teaching;
    }

    public ClazzManagement managementAt(Teacher teacher, Clazz clazz){

        School school = schoolRepository.loadOfId(clazz.schoolId());
        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();

        ClazzManagement management = new ClazzManagement(clazz.clazzId(),teacher,grade, period);

        List<ClazzManagement> hasManages = teacherRepository.findManagement(teacher.personId());
        boolean canBeTeached = hasManages.size()>0?hasManages.contains(management):true;
        AssertionConcerns.assertArgumentFalse(canBeTeached,"已在本班教授课程");

        return management;
    }
}