package com.zhezhu.sm.application.student;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.share.domain.common.Period;
import com.zhezhu.share.domain.id.SubjectId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.StudentId;
import com.zhezhu.share.domain.school.Course;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.TeadentApplicationService;
import com.zhezhu.sm.application.data.StudyData;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Service
public class StudentApplicationService  extends TeadentApplicationService {
    private static Logger logger = LoggerFactory.getLogger(StudentApplicationService.class);

    @Transactional(rollbackFor = Exception.class)
    public String  newStudent(NewStudentCommand command) {
        logger.debug("New Student to School{}",command.getSchoolId());

        Student student = toStudent(command);
        studentRepository.save(student);
        return student.getStudentId().id();
    }

    @Transactional(rollbackFor = Exception.class)
    public void arrangingClazz(ArrangeStudentCommand command){
        logger.debug("Arranging student {} ",command.getStudentId());
        StudentId studentId = new StudentId(command.getStudentId());
        Student student = studentRepository.loadOf(studentId);
        AssertionConcerns.assertArgumentNotNull(student,"sm-04-001");
        ClazzId clazzId = new ClazzId(command.getManagedClazzId());
        Clazz clazz = getManagedClazz(clazzId);
        Grade grade = clazz.currentGrade();
        Period period = new Period(command.getDateStarts(), command.getDateEnds());
        student.managedAt(period,grade,clazz);
        arrangingStudies(command,student);
    }

    protected void arrangingStudies(ArrangeStudentCommand command, Student student){
        StudyData[] courseData = command.getCourses();
        if(courseData != null){
            for(StudyData cd:courseData){
                Clazz clazz = getStudyClazz(new ClazzId(cd.getClazzId()));
                Grade grade = clazz.currentGrade();
                Period period = new Period(command.getDateStarts(), command.getDateEnds());
                Course course = new Course(cd.getCourseName(),new SubjectId(cd.getSubjectId()));
                student.studyAt(period,grade,clazz,course);
            }
        }
    }
}