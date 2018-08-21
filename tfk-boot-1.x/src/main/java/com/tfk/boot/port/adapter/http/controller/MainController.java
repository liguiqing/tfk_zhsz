package com.tfk.boot.port.adapter.http.controller;

import com.tfk.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Controller
@RequestMapping("/")
public class MainController extends AbstractHttpController {


    @RequestMapping("/index.html")
    public ModelAndView onIndex(){
        return newModelAndViewBuilder("/index").creat();
    }
}