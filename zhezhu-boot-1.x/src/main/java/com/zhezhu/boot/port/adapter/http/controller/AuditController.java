package com.zhezhu.boot.port.adapter.http.controller;

import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Controller
@RequestMapping("/a")
public class AuditController extends AbstractHttpController {

    /**
     * 老师申请审核
     *
     * @return
     */
    @RequestMapping("/s")
    public ModelAndView onAuditTeacherApply(){
        log.debug("url /a/s");
        return newModelAndViewBuilder("/audit/clazzApply").creat();
    }
}