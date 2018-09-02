package com.zhezhu.sm.port.adapter.http.controller;

import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.sm.application.data.StudentData;
import com.zhezhu.sm.application.data.StudentNameSortedData;
import com.zhezhu.sm.application.student.ArrangeStudentCommand;
import com.zhezhu.sm.application.student.NewStudentCommand;
import com.zhezhu.sm.application.student.StudentApplicationService;
import com.zhezhu.sm.application.student.StudentQueryService;
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
        logger.debug("URL /student Method=POST  {}",command.getSchoolId());
        String studentId = studentApplicationService.newStudent(command);
        return newModelAndViewBuilder("/student/newStudentSuccess")
                .withData("studentId",studentId).creat();
    }

    @RequestMapping(value="/arrange",method = RequestMethod.POST)
    public ModelAndView onArragnStudent(@RequestBody ArrangeStudentCommand command){
        logger.debug("URL /student/arrange Method=POST  {} ",command);
        studentApplicationService.arrangingClazz(command);
        return newModelAndViewBuilder("/student/arrangeStudentSuccess")
                .withData("studentId",command.getStudentId()).creat();
    }

    /**
     * 读取班级学生信息
     *
     * @param schoolId
     * @param clazzId
     * @return
     */
    @RequestMapping(value="/list/clazz/{schoolId}/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetClazzStudent(@PathVariable String schoolId,@PathVariable String clazzId){
        logger.debug("URL /student/list/clazz/{}/{} Method=GET ",schoolId,clazzId);

        List<StudentData> students = studentQueryService.findStudentInOfNow(schoolId, clazzId);
        return newModelAndViewBuilder("/student/clazzStudentList").withData("students",students).creat();
    }

    /**
     * 读取班级学生信息,并按姓名首字母排序
     *
     * @param schoolId
     * @param clazzId
     * @return
     */
    @RequestMapping(value="/list/clazz/nameSorted/{schoolId}/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetClazzStudentSorted(@PathVariable String schoolId,@PathVariable String clazzId){
        logger.debug("URL /student/list/clazz/nameSorted/{}/{} Method=GET ",schoolId,clazzId);

        StudentNameSortedData sortedData = studentQueryService.findStudentInOfNowAndSortByName(schoolId, clazzId);
        return newModelAndViewBuilder("/student/clazzStudentListSorted").withData("students",sortedData.getGroups()).creat();
    }

}