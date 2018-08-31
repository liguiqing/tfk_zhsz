package com.zhezhu.sm.port.adapter.http.controller;

import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.sm.application.clazz.ClazzApplicationService;
import com.zhezhu.sm.application.clazz.ClazzQueryService;
import com.zhezhu.sm.application.clazz.NewClazzCommand;
import com.zhezhu.sm.application.data.ClazzData;
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

    @Autowired(required = false)
    private ClazzQueryService clazzQueryService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewClazz(@RequestBody NewClazzCommand command){
        logger.debug("URL /clazz Method=POST  {}",command.getClazzName(),command.getSchoolId());
        clazzApplicationService.newClazz(command);
        return newModelAndViewBuilder("/clazz/newClazzSuccess").creat();
    }


    @RequestMapping(value = "/grade/{schoolId}/{gradeLevel}",method = RequestMethod.GET)
    public ModelAndView onGetSchoolClazz(@PathVariable String schoolId, @PathVariable int gradeLevel){
        logger.debug("URL /clazz/grade/{}/{} Method=GET",schoolId,gradeLevel);
        List<ClazzData> datas = clazzQueryService.findSchoolGradeClazzesCanBeManagedOfNow(schoolId,gradeLevel);
        return newModelAndViewBuilder("/clazz/gradeClazzList").withData("clazzes",datas).creat();
    }

}