package com.zhezhu.sm.domain.model.teacher;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.school.Course;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.clazz.ClazzRepository;
import com.zhezhu.sm.domain.model.school.School;
import com.zhezhu.sm.domain.model.school.SchoolRepository;

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

    public ClazzTeaching teachingAt(Teacher teacher,Clazz clazz, Course course){
        AssertionConcerns.assertArgumentFalse(clazz.canBeStudyAt(),"行政班不能教授课程");

        School school = schoolRepository.loadOf(clazz.getSchoolId());
        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();

        ClazzTeaching teaching = new ClazzTeaching(teacher,period,grade,clazz.getClazzId(), course);

        //List<ClazzTeaching> hasTeaching = teacherRepository.findTeaching(teacher.personId());
        //boolean canBeTeached = hasTeaching.size()>0?hasTeaching.contains(teachingAt):true;
        //AssertionConcerns.assertArgumentFalse(canBeTeached,"已在本班教授课程");

        return teaching;
    }

    public ClazzManagement managementAt(Teacher teacher, Clazz clazz){

        School school = schoolRepository.loadOf(clazz.getSchoolId());
        Period period = school.getThisTermStartsAndEnds();
        Grade grade = clazz.currentGrade();

        //ClazzManagement management = new ClazzManagement(teacher,period,school.schoolId(),clazz.clazzId(),grade);

        //List<ClazzManagement> hasManages = teacherRepository.findManagement(teacher.personId());
        //boolean canBeTeached = hasManages.size()>0?hasManages.contains(management):true;
        //AssertionConcerns.assertArgumentFalse(canBeTeached,"已在本班教授课程");

        return null;
    }
}