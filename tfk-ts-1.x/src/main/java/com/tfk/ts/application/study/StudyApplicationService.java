/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.study;

import com.tfk.ts.domain.model.school.clazz.Clazz;
import com.tfk.ts.domain.model.school.clazz.ClazzRepository;
import com.tfk.ts.domain.model.school.student.Student;
import com.tfk.ts.domain.model.school.student.StudentRepository;

/**
 * 学习应用服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public class StudyApplicationService {

    private StudentRepository studentRepository;

    private ClazzRepository clazzRepository;

    public void joinClazz(JoinClazzCommand command){
       // Course course = courseRepository.loadOfId(command.get)
        Student student = studentRepository.loadOfId(command.getStdudentId());
        Clazz clazz = clazzRepository.loadOfId(command.getClazzId());

        if(clazz.canBeStudyAndTeachIn()){
            this.studyInClazz();
        }
        //ClazzCourse course = new ClazzCourse(command.getSchoolId(),command.getTermId(),command.getClazzId(),command.get);
    }

    public void studyInClazz(){}
}