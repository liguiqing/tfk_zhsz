package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.index.*;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/index")
public class IndexController extends AbstractHttpController {

    @Autowired(required = false)
    private IndexApplicationService indexApplicationService;

    @Autowired(required = false)
    private IndexQueryService indexQueryService;

    /**
     * 创建通用评价指标
     *
     * @param command
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/common",method = RequestMethod.POST)
    public ModelAndView onNewIndex(@RequestBody NewIndexCommand command)throws Exception{
        logger.debug("URL /index/common method=POST {}",command);

        String indexId = indexApplicationService.newStIndex(command);
        return newModelAndViewBuilder("/index/newIndexSuccess").withData("indexId",indexId).creat();
    }

    /**
     * 创建学校评价指标
     *
     * @param command
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tenant",method = RequestMethod.POST)
    public ModelAndView onNewTenantIndex(@RequestBody NewIndexCommand command)throws Exception{
        logger.debug("URL /index/tenant method=POST {}",command);

        String indexId = indexApplicationService.newTenantIndex(command);
        return newModelAndViewBuilder("/index/newIndexSuccess").withData("indexId",indexId).creat();
    }

    /**
     * 更新评价指标
     *
     * @param command
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView onUpdateIndex(@RequestBody UpdateIndexCommand command)throws Exception{
        logger.debug("URL /index method=PUT {}",command);

        indexApplicationService.updateIndex(command);
        return newModelAndViewBuilder("/index/updateIndexSuccess").withData("indexId",command.getIndexId()).creat();
    }


    @RequestMapping(value = "/owner/{ownerId}/{group}",method = RequestMethod.GET)
    public ModelAndView onGetOwnerIndexes(@PathVariable String ownerId, @PathVariable String group,
                                          @RequestParam(required = false) boolean withChildren)throws Exception{
        logger.debug("URL /index/owner/{}/{}?withChildren={} method=GET",ownerId,group,withChildren);

        List<IndexData> data = indexQueryService.getOwnerIndexes(ownerId, group, withChildren);
        return newModelAndViewBuilder("/index/updateIndexSuccess").withData("indexes",data).creat();
    }

}