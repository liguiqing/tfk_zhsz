package com.zhezhu.boot.port.adapter.http.controller;

import com.zhezhu.boot.infrastructure.init.DbInitService;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
public class MainController extends AbstractHttpController {

    @Autowired(required = false)
    private DbInitService dbInitService;

    @RequestMapping("/index")
    public ModelAndView onIndex(){
        logger.debug("URL /index Method=GET");

        return newModelAndViewBuilder("/index").creat();
    }

    @RequestMapping("/login")
    public ModelAndView onLogin(){
        logger.debug("URL /login Method=GET");

        return newModelAndViewBuilder("/index").creat();
    }

    @RequestMapping(value = "/init/db")
    public ModelAndView onShowInit(){
        logger.debug("URL /init/db Method=GET");
        return newModelAndViewBuilder("/dbInit").creat();
    }

    @RequestMapping(value = "/init/db",method = RequestMethod.POST)
    public ModelAndView onDoInit()throws Exception{
        logger.debug("URL /init/db Method=POST");
        dbInitService.doInit();
        return newModelAndViewBuilder("/dbInit").creat();
    }
}