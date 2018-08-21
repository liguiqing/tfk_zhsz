package com.tfk.boot.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
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
@RequestMapping("/")
public class MainController extends AbstractHttpController {


    @RequestMapping("/index")
    public ModelAndView onIndex(){
        log.debug("url index");
        return newModelAndViewBuilder("/index").creat();
    }
}