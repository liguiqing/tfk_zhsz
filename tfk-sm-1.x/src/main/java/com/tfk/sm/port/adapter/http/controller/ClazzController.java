package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import com.tfk.sm.application.clazz.ClazzApplicationService;
import com.tfk.sm.application.data.ClazzData;
import com.tfk.sm.application.clazz.ClazzQueryService;
import com.tfk.sm.application.clazz.NewClazzCommand;
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
@RequestMapping("/clazz")
public class ClazzController extends AbstractHttpController {

    @Autowired(required = false)
    private ClazzApplicationService clazzApplicationService;

    private ClazzQueryService clazzQueryService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewClazz(@RequestBody NewClazzCommand command){
        logger.debug("New Clazz with name {} of school {}",command.getClazzName(),command.getSchoolId());
        clazzApplicationService.newClazz(command);
        return newModelAndViewBuilder("/clazz/newClazzSuccess").creat();
    }


    @RequestMapping(value = "/school/{schoolId}/{gradeLevel}",method = RequestMethod.GET)
    public ModelAndView onGetSchoolClazz(@PathVariable String schoolId, @PathVariable int gradeLevel){
        logger.debug("Get Clazz of School {} in Grade {} ",gradeLevel,schoolId);
        List<ClazzData> datas = clazzQueryService.findSchoolGradeClazzesCanBeManagedOfNow(schoolId,gradeLevel);
        return newModelAndViewBuilder("/clazz/schoolClazzList").withData("clazzes",datas).creat();
    }

}