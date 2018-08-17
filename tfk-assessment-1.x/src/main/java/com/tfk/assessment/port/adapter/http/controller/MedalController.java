package com.tfk.assessment.port.adapter.http.controller;

import com.tfk.assessment.application.medal.MedalApplicationService;
import com.tfk.assessment.application.medal.NewMedalCommand;
import com.tfk.assessment.application.medal.UpdateMedalCommand;
import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */
@RequestMapping("/medal")
public class MedalController extends AbstractHttpController {

    @Autowired(required = false)
    private MedalApplicationService medalApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewMedal(@RequestBody NewMedalCommand command)throws Exception{
        logger.debug("URL /medal method=POST  {}",command);

        String medalId = medalApplicationService.newMedal(command);
        return newModelAndViewBuilder("/index/newMedalSuccess").withData("medalId",medalId).creat();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView onUpdateMedal(UpdateMedalCommand command)throws Exception{
        logger.debug("URL /medal method=PUT  {}",command);

        medalApplicationService.updateMedal(command);
        return newModelAndViewBuilder("/index/updateMedalSuccess").creat();
    }

    @RequestMapping(value="/{medalId}",method = RequestMethod.DELETE)
    public ModelAndView onDeleteMedal(@PathVariable String medalId)throws Exception{
        logger.debug("URL /medal/{} method=DELETE  {}",medalId);

        medalApplicationService.deleteMedal(medalId);
        return newModelAndViewBuilder("/medal/deleteMedalSuccess").creat();
    }
}