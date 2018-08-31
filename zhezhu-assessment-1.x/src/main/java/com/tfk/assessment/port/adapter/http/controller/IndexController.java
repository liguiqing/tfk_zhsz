package com.tfk.assessment.port.adapter.http.controller;

import com.tfk.assessment.application.index.IndexApplicationService;
import com.tfk.assessment.application.index.NewIndexCommand;
import com.tfk.assessment.application.index.UpdateIndexCommand;
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
@RequestMapping("/index")
public class IndexController extends AbstractHttpController {

    @Autowired(required = false)
    private IndexApplicationService indexApplicationService;

    @RequestMapping(value = "/common",method = RequestMethod.POST)
    public ModelAndView onNewIndex(@RequestBody NewIndexCommand command)throws Exception{
        logger.debug("URL /index/common method=POST {}",command);

        String indexId = indexApplicationService.newStIndex(command);
        return newModelAndViewBuilder("/index/newIndexSuccess").withData("indexId",indexId).creat();
    }

    @RequestMapping(value = "/tenant",method = RequestMethod.POST)
    public ModelAndView onNewTenantIndex(@RequestBody NewIndexCommand command)throws Exception{
        logger.debug("URL /index/tenant method=POST {}",command);

        String indexId = indexApplicationService.newTenantIndex(command);
        return newModelAndViewBuilder("/index/newIndexSuccess").withData("indexId",indexId).creat();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView onUpdateIndex(@RequestBody UpdateIndexCommand command)throws Exception{
        logger.debug("URL /index method=PUT {}",command);

        indexApplicationService.updateIndex(command);
        return newModelAndViewBuilder("/index/updateIndexSuccess").withData("indexId",command.getIndexId()).creat();
    }
}