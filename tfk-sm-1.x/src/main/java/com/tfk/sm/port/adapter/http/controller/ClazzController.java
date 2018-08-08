package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import com.tfk.sm.application.clazz.ClazzApplicationService;
import com.tfk.sm.application.clazz.NewClazzCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */

@RequestMapping("/clazz")
public class ClazzController extends AbstractHttpController {

    @Autowired(required = false)
    private ClazzApplicationService clazzApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewClazz(@RequestBody NewClazzCommand command){
        logger.debug("New Clazz with name {} of school {}",command.getClazzName(),command.getSchoolId());
        clazzApplicationService.newClazz(command);
        return newModelAndViewBuilder("/clazz/newClazzSuccess").creat();
    }
}