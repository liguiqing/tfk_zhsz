package com.tfk.sm.application.teacher;

import com.tfk.sm.domain.model.clazz.ClazzRepository;
import com.tfk.sm.domain.model.school.SchoolRepository;
import com.tfk.sm.domain.model.teacher.TeacherRepository;
import com.tfk.sm.domain.model.teacher.TeacherService;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherApplicationService {

    private ClazzRepository clazzRepository;

    private SchoolRepository schoolRepository;

    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    public void newTeacher(NewTeacherCommand command){

    }

    public void joinSchool(JoinSchoolCommand command){


    }

    public void offSchool(){

    }
}