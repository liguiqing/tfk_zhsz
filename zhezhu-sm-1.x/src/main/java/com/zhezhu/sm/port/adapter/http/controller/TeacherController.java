package com.zhezhu.sm.port.adapter.http.controller;

import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.sm.application.teacher.ArrangeTeacherCommand;
import com.zhezhu.sm.application.teacher.NewTeacherCommand;
import com.zhezhu.sm.application.teacher.TeacherApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController extends AbstractHttpController {

    @Autowired(required = false)
    private TeacherApplicationService teacherApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewTeacher(@RequestBody NewTeacherCommand command){
        logger.debug("URL /teacher Method=POST  {}",command);

        String teacherId = this.teacherApplicationService.newTeacher(command);
        return newModelAndViewBuilder("/teacher/newTeacherSuccess").withData("teacherId",teacherId).creat();
    }

    @RequestMapping(value = "/arrange",method = RequestMethod.POST)
    public ModelAndView onArrangeTeacher(@RequestBody ArrangeTeacherCommand command){
        logger.debug("URL /teacher/arrange Method=POST  {}",command);

        this.teacherApplicationService.arranging(command);
        return newModelAndViewBuilder("/teacher/arrangeTeacherSuccess").withData("teacherId",command.getTeacherId()).creat();
    }
}