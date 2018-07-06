/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school;

import com.tfk.commons.AssertionConcerns;
import com.tfk.ts.application.school.command.NewClazzCommand;
import com.tfk.ts.application.school.command.NewClazzMasterCommand;
import com.tfk.ts.application.school.command.NewClazzTeacherCommand;
import com.tfk.ts.application.school.command.NewStudentCommand;
import com.tfk.ts.domain.model.school.*;
import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.clazz.ClazzFactory;
import com.tfk.ts.domain.model.school.clazz.ClazzId;
import com.tfk.ts.domain.model.school.clazz.ClazzRepository;
import com.tfk.ts.domain.model.school.common.Period;
import com.tfk.ts.domain.model.school.common.WLType;
import com.tfk.ts.domain.model.school.staff.*;
import com.tfk.ts.domain.model.school.student.Student;
import com.tfk.ts.domain.model.school.student.StudentRepository;
import com.tfk.ts.domain.model.school.term.Term;
import com.tfk.ts.domain.model.school.term.TermId;
import com.tfk.ts.domain.model.school.term.TermOrder;
import com.tfk.ts.domain.model.school.term.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 班级应用服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzApplicationService {
    private static Logger logger = LoggerFactory.getLogger(ClazzApplicationService.class);

    private SchoolRepository schoolRepository;

    private ClazzRepository clazzRepository;

    private SchoolApplicationService schoolApplicationService;

    private StaffRepository staffRepository;

    private TermRepository termRepository;

    private StudentRepository studentRepository;


    /**
     * 新的班级
     *
     * @param schoolId
     * @param command
     */
    public void newClazz(String schoolId, NewClazzCommand command){
        logger.debug("New Clazz {} for school{}",command,schoolId);

        School school = schoolApplicationService.getSchool(schoolId);
        ClazzId clazzId = clazzRepository.nextIdentity();
        Grade grade = new Grade(command.getGradeName(),GradeLevel.fromName(command.getGradeLevel()),
                new StudyYear(command.getYear()));

        WLType wl = WLType.fromName(command.getWlType());
        Term term = termRepository.loadOfId(new TermId(command.getTermId()));

        Clazz newClazz = ClazzFactory.newClazz(school.schoolId(),clazzId,command.getName(),command.getClazzNo(),
                command.getCreateDate(),grade,command.getType(),wl,term);
        clazzRepository.save(newClazz);
    }

    /**
     * 增加或者更换班主任务
     *
     * @param command
     */
    public void addClassMaster(NewClazzMasterCommand command){
        logger.debug("ClazzMaster {} for clazz {}",command.getName(),command.getClazzId());

        Clazz clazz = clazzRepository.loadOfId(new ClazzId(command.getClazzId()));
        AssertionConcerns.assertArgumentNotNull(clazz,"请提供班级");

        schoolApplicationService.staffActToAndSave(new StaffId(command.getIdentity()),new ActClazzMaster(clazz),
                new Period(command.getStarts(),command.getEnds()));
    }

    /**
     * 增加班级或者更新班级的教学老师
     * @param command
     */
    public void addClassTeacher(final NewClazzTeacherCommand command){
        logger.debug("Clazzteacher {} for clazz {}",command.getName(),command.getClazzId());

        Clazz clazz = clazzRepository.loadOfId(new ClazzId(command.getClazzId()));
        AssertionConcerns.assertArgumentNotNull(clazz,"请提供班级");
        AssertionConcerns.assertArgumentNotNull(clazz.canBeStudied(),clazz.name() + "不能进行教学");

        Staff staff = staffRepository.loadOfId(new StaffId(command.getIdentity()));
        AssertionConcerns.assertArgumentNotNull(staff,"请提供老师");

        Position position = staff.positionOf(new PositionFilter() {
            @Override
            public boolean isSatisfied(Position position) {
                if(position instanceof Teacher){
                    Teacher teacher = (Teacher) position;
                    return teacher.canTeach(new Course(command.getCourseName(),command.getSubjectId()));
                }
                return false;
            }
        });

        AssertionConcerns.assertArgumentNotNull(position,command.getName()+"老师没有教" +
                command.getCourseName());
        Teacher teacher = (Teacher)position;
        Period period = new Period(command.getStarts(), command.getEnds());
        Grade grade = clazz.periodGrade(period);
        AssertionConcerns.assertArgumentNotNull(grade,period.formatString() +"没有找到" +
                clazz.name());

        ClazzTeacher clazzTeacher = teacher.transfer(new TeacherToClazzTeacherTranslater(clazz,
                new Period(command.getStarts(),command.getEnds()),grade));

        staff.addPosition(clazzTeacher);
        staffRepository.save(staff);
    }

    /**
     * 班级升一个年级
     *
     * @param clazz
     * @param term
     */
    public void upGrade(Clazz clazz,Term term){
        clazz.upGrade(term);

        if(!clazz.canBeStudied())
            return;

        List<Student> students = this.studentRepository.studentsOf(clazz.clazzId());
        Grade grade = clazz.termGrade(term);
        GradeCourseable courseable = GradeCourseableFactory.lookup(clazz.schoolId());
        Collection<Course> gradeCourses = courseable.courseOf(grade);
        if(students != null){
            for(Student student:students){
                for(Course course : gradeCourses)
                    student.changeStudyClazzOfCourse(clazz,grade,course, term.timeSpan().starts(),term.timeSpan().ends());
                studentRepository.save(student);
            }
        }
    }

    /**
     * 年级所有班级升级
     *
     * @param grade
     * @param term
     */
    public void upGrade(Grade grade,Term term){
        AssertionConcerns.assertArgumentTrue(term.order() == TermOrder.Second,"上学期不能升班");

        List<Clazz> clazzes = clazzRepository.listGradeClazzes(grade,term);
        for(Clazz clazz:clazzes){
            upGrade(clazz,term);
        }
    }

    /**
     * 学校所有年级升级
     *
     * @param school
     * @param term
     */
    public void upGrade(School school, Term term){
        AssertionConcerns.assertArgumentTrue(term.order() == TermOrder.Second,"上学期不能升班");

        List<Grade> grades = school.grades();
        for(Grade grade:grades){
            this.upGrade(grade,term);
        }
    }

    public void addStudent(String schoolId,NewStudentCommand command){

    }

    public void setSchoolRepository(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public void setClazzRepository(ClazzRepository clazzRepository) {
        this.clazzRepository = clazzRepository;
    }

    public void setSchoolApplicationService(SchoolApplicationService schoolApplicationService) {
        this.schoolApplicationService = schoolApplicationService;
    }

    public void setStaffRepository(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void setTermRepository(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}