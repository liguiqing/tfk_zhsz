package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import com.tfk.sm.application.teacher.NewTeacherCommand;
import com.tfk.sm.application.teacher.TeacherApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */
@RequestMapping("/teacher")
public class TeacherController extends AbstractHttpController {

    @Autowired(required = false)
    private TeacherApplicationService teacherApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewTeacher(@RequestBody NewTeacherCommand command){
        logger.debug("New Teacher with name {} to schoole {}",command.getName(),command.getSchoolId());
        String teacherId = this.teacherApplicationService.newTeacher(command);
        return newModelAndViewBuilder("/teacher/newTeacherSuccess").withData("teacherId",teacherId).creat();
    }
}