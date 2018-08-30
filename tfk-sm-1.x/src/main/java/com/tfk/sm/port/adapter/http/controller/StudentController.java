package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import com.tfk.sm.application.data.StudentData;
import com.tfk.sm.application.student.ArrangeStudentCommand;
import com.tfk.sm.application.student.NewStudentCommand;
import com.tfk.sm.application.student.StudentApplicationService;
import com.tfk.sm.application.student.StudentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/student")
public class StudentController extends AbstractHttpController {

    @Autowired(required = false)
    private StudentApplicationService studentApplicationService;

    @Autowired(required = false)
    private StudentQueryService studentQueryService;

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

    @RequestMapping(value="/clazz/{schoolId}/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetClazzStudent(@PathVariable String schoolId,@PathVariable String clazzId){
        logger.debug("Get students of school {} in clazz {}  ",schoolId,clazzId);

        List<StudentData> students = studentQueryService.findStudentInOfNow(schoolId, clazzId);
        return newModelAndViewBuilder("/student/clazzStudentList").withData("students",students).creat();
    }

}