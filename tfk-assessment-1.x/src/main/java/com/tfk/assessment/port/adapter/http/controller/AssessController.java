package com.tfk.assessment.port.adapter.http.controller;

import com.tfk.assessment.application.assess.AssessApplicationService;
import com.tfk.assessment.application.assess.NewAssessCommand;
import com.tfk.assessment.application.index.NewIndexCommand;
import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
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
@RequestMapping("/assess")
public class AssessController extends AbstractHttpController {

    @Autowired(required = false)
    private AssessApplicationService assessApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onAssess(@RequestBody NewAssessCommand command)throws Exception{
        logger.debug("URL /assess method=POST {}",command);

        assessApplicationService.assess(command);
        return newModelAndViewBuilder("/assess/newAssessSuccess").creat();
    }
}