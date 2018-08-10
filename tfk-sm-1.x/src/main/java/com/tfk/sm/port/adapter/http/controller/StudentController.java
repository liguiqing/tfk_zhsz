package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import com.tfk.sm.application.student.ArrangeStudentCommand;
import com.tfk.sm.application.student.NewStudentCommand;
import com.tfk.sm.application.student.StudentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */

@RequestMapping("/student")
public class StudentController extends AbstractHttpController {

    @Autowired(required = false)
    private StudentApplicationService studentApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewStudent(@RequestBody NewStudentCommand command){
        logger.debug("New Student Of {} ",command.getSchoolId());
        String studentId = studentApplicationService.newStudent(command);
        return newModelAndViewBuilder("/student/newStudentSuccess")
                .withData("studentId",studentId).creat();
    }

    @RequestMapping(value="/arrange",method = RequestMethod.POST)
    public ModelAndView onArragnStudent(@RequestBody ArrangeStudentCommand command){
        logger.debug("Arrange Student Of {} ",command.getStudentId());
        studentApplicationService.arrangingClazz(command);
        return newModelAndViewBuilder("/student/arrangeStudentSuccess")
                .withData("studentId",command.getStudentId()).creat();
    }

}