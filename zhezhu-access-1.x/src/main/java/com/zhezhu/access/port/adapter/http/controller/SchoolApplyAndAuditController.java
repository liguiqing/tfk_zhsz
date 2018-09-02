package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.school.*;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
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
public class SchoolApplyAndAuditController extends AbstractHttpController {

    @Autowired(required = false)
    private SchoolApplyAndAuditApplicationService applyAndAuditApplicationService;

    @Autowired(required = false)
    private SchoolApplyAndAuditQueryService applyAndAuditQueryService;

    /**
     * 班级关注申请
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/apply/school",method = RequestMethod.POST)
    public ModelAndView onClazzFollowApply(@RequestBody ClazzFollowApplyCommand command){
        logger.debug("URL /apply/school Method=POST {}",command);

        String applyId = applyAndAuditApplicationService.followClazzApply(command);
        return newModelAndViewBuilder("/apply/clazzFollowApplySuccess").withData("applyId",applyId).creat();
    }

    /**
     * 取消班级关注申请
     *
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/apply/school/{applyId}",method = RequestMethod.DELETE)
    public ModelAndView onClazzFollowApplyCancel(@PathVariable String applyId){
        logger.debug("URL /apply/school/{} Method=DELETE ",applyId);

        applyAndAuditApplicationService.followClazzApplyCancel(applyId);
        return newModelAndViewBuilder("/apply/clazzFollowApplyCancel").creat();
    }

    /**
     * 查询申请人已经通过审核的班级关注申请
     *
     * @param applierId
     * @return
     */
    @RequestMapping(value = "/apply/audited/{applierId}",method = RequestMethod.GET)
    public ModelAndView onGetClazzFollowApplyAudited(@PathVariable String applierId){
        logger.debug("URL /apply/audited/{} Method=GET ",applierId);

        List<ClazzFollowApplyAndAuditData> auditedClazzs = applyAndAuditQueryService.getAuditedClazzs(applierId);
        return newModelAndViewBuilder("/apply/clazzFollowApplyAuditedList").withData("clazzs",auditedClazzs).creat();
    }

    /**
     * 班级关注申请审核
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/audit/school",method = RequestMethod.POST)
    public ModelAndView onFollowClazzAudit(@RequestBody ClazzFollowAuditCommand command){
        logger.debug("URL /audit/school Method=POST {}",command);

        command.setAuditorId(getUser().getUserPersonId());
        String auditId = applyAndAuditApplicationService.followClazzAudit(command);
        return newModelAndViewBuilder("/apply/clazzFollowAuditSuccess").withData("auditId",auditId).creat();
    }

    /**
     * 取消班级关注申请审核
     *
     * @param auditId
     * @return
     */
    @RequestMapping(value = "/audit/school/{auditId}",method = RequestMethod.DELETE)
    public ModelAndView onFollowClazzAuditCancel(@PathVariable String auditId){
        logger.debug("URL /audit/school/{} Method=DELETE",auditId);

        applyAndAuditApplicationService.followClazzAuditCancel(auditId);
        return newModelAndViewBuilder("/audit/clazzFollowAuditCancel").creat();
    }


}