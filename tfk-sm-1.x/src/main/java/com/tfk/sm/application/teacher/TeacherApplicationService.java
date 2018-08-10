package com.tfk.sm.application.teacher;

import com.tfk.commons.AssertionConcerns;
import com.tfk.share.domain.common.Period;
import com.tfk.share.domain.id.SubjectId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.application.TeadentApplicationService;
import com.tfk.sm.application.data.CourseData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.teacher.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Service
public class TeacherApplicationService extends TeadentApplicationService {
    private static Logger logger = LoggerFactory.getLogger(TeacherApplicationService.class);

    @Transactional(rollbackFor = Exception.class)
    public String newTeacher(NewTeacherCommand command){
        logger.debug("New Teacher for School {}",command.getSchoolId());

        Teacher teacher = toTeacher(command);
        teacherRepository.save(teacher);
        return teacher.teacherId().id();
    }

    @Transactional(rollbackFor = Exception.class)
    public void arranging(ArrangeTeacherCommand command){
        logger.debug("Teacher join {}",command.getTeacherId());
        TeacherId teacherId = new TeacherId(command.getTeacherId());
        Teacher teacher = teacherRepository.loadOf(teacherId);
        AssertionConcerns.assertArgumentNotNull(teacher,"sm-03-001");

        addTeacherCourse(command, teacher);
        addManagementClazzes(command,teacher);
        addTeachingClazzes(command, teacher);

        teacherRepository.save(teacher);
    }

    protected void addTeacherCourse(ArrangeTeacherCommand command, Teacher teacher){
        CourseData[] courseData = command.getCourses();
        if(courseData != null){
            for(CourseData cd:courseData){
                Course course = new Course(cd.getName(),new SubjectId(cd.getSubjectId()));
                Grade grade = Grade.newWithLevel(cd.getGradeLevel());
                teacher.addCourse(grade,course);
            }
        }
    }

    protected void addManagementClazzes(ArrangeTeacherCommand command, Teacher teacher){
        String[] managementClazzIds  = command.getManagementClazzIds();
        if(managementClazzIds != null){
            for(String clazzId:managementClazzIds){
                Clazz clazz = getManagedClazz(new ClazzId(clazzId));
                Grade grade = clazz.currentGrade();
                Period period = new Period(command.getDateStarts(), command.getDateEnds());
                teacher.managementAt(period,grade,clazz);
            }
        }
    }

    protected void addTeachingClazzes(ArrangeTeacherCommand command, Teacher teacher){
        String[] teachingClazzIds = command.getTeachingClazzIds();
        CourseData[] courseData = command.getCourses();
        if(teachingClazzIds != null && courseData != null){
            for(String clazzId:teachingClazzIds){
                Clazz clazz = getStudyClazz(new ClazzId(clazzId));
                Grade grade = clazz.currentGrade();
                Period period = new Period(command.getDateStarts(), command.getDateEnds());
                for(CourseData cd:courseData){
                    Course course = new Course(cd.getName(),new SubjectId(cd.getSubjectId()));
                    teacher.teachingAt(period,grade,clazz,course);
                }
            }
        }
    }

    public void offSchool(){

    }
}