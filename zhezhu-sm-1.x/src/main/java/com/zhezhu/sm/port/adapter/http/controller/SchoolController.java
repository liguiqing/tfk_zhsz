package com.zhezhu.sm.port.adapter.http.controller;

import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.sm.application.clazz.ClazzQueryService;
import com.zhezhu.sm.application.data.ClazzData;
import com.zhezhu.sm.application.school.NewSchoolCommand;
import com.zhezhu.sm.application.school.SchoolApplicationService;
import com.zhezhu.sm.application.school.SchoolData;
import com.zhezhu.sm.application.school.SchoolQueryService;
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
@RequestMapping("/school")
public class SchoolController extends AbstractHttpController {

    @Autowired(required = false)
    private SchoolApplicationService schoolApplicationService;

    @Autowired(required = false)
    private SchoolQueryService schoolQueryService;

    @Autowired(required = false)
    private ClazzQueryService clazzQueryService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewSchool(@RequestBody NewSchoolCommand command){
        logger.debug("URL /school Method=POST  {}",command);

        schoolApplicationService.newSchool(command);
        return newModelAndViewBuilder("/school/newSchoolSuccess").creat();
    }

    @RequestMapping(value = "/{page}/{size}",method = RequestMethod.GET)
    public ModelAndView onGetAllSchool(@PathVariable int page,@PathVariable int size){
        logger.debug("URL /school/{}/{} Method=GET",page,size);

        List<SchoolData> data =  schoolQueryService.findAllSchool(page,size);
        return newModelAndViewBuilder("/school/allSchoolList").withData("schools",data).creat();
    }

    @RequestMapping(value = "/grade/clazz/{schoolId}/{gradeLevel}",method = RequestMethod.GET)
    public ModelAndView onGetSchoolClazz(@PathVariable String schoolId, @PathVariable int gradeLevel){
        logger.debug("URL /school/{}/{} Method=GET",schoolId,gradeLevel);

        List<ClazzData> datas = clazzQueryService.findSchoolGradeClazzesCanBeManagedOfNow(schoolId,gradeLevel);
        return newModelAndViewBuilder("/school/gradeClazzList").withData("clazzes",datas).creat();
    }
}