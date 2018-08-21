package com.tfk.sm.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import com.tfk.sm.application.school.NewSchoolCommand;
import com.tfk.sm.application.school.SchoolApplicationService;
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
@RequestMapping("/school")
public class SchoolController extends AbstractHttpController {

    @Autowired(required = false)
    private SchoolApplicationService schoolApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewSchool(@RequestBody NewSchoolCommand command){
        logger.debug("New School with name {}",command.getName());
        schoolApplicationService.newSchool(command);
        return newModelAndViewBuilder("/school/newSchoolSuccess").creat();
    }
}