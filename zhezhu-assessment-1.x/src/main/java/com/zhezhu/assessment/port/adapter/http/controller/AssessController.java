package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.assess.AssessApplicationService;
import com.zhezhu.assessment.application.assess.AssessData;
import com.zhezhu.assessment.application.assess.AssessQueryService;
import com.zhezhu.assessment.application.assess.NewAssessCommand;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/assess")
public class AssessController extends AbstractHttpController {

    @Autowired(required = false)
    private AssessApplicationService assessApplicationService;

    @Autowired(required = false)
    private AssessQueryService assessQueryService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onAssess(@RequestBody NewAssessCommand command)throws Exception{
        logger.debug("URL /assess method=POST {}",command);

        assessApplicationService.assess(command);
        return newModelAndViewBuilder("/assess/newAssessSuccess").creat();
    }

    @RequestMapping(value="/assessee/{assesseeId}",method = RequestMethod.GET)
    public ModelAndView onGetAssess(@PathVariable String assesseeId,
                                    @RequestParam(required = false) Date from,
                                    @RequestParam(required = false) Date to)throws Exception{
        logger.debug("URL /assess/assessee/{} method=GET {}",assesseeId);

        List<AssessData> data = assessQueryService.getAssessOf(assesseeId,from,to);
        return newModelAndViewBuilder("/assess/newAssessSuccess").creat();
    }
}