package com.tfk.commons.spring;

import com.tfk.commons.lang.Throwables;
import com.tfk.commons.port.adaptor.http.controller.MessageSource;
import com.tfk.commons.port.adaptor.http.controller.MessageSourceFactory;
import com.tfk.commons.port.adaptor.http.controller.ModelAndViewBuilder;
import com.tfk.commons.port.adaptor.http.controller.ServletWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class SpringMvcExceptionResolver extends SimpleMappingExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static MessageSourceFactory defaultMessageSourceFactory = new MessageSourceFactory() {};

    private final static ServletWrapper defaultServletWrapper = new ServletWrapper(){};

    @Autowired(required=false)
    private MessageSourceFactory messageSourceFactory;

    @Autowired(required = false)
    private ServletWrapper servletWrapper;

    protected MessageSourceFactory getMessageSourceFactory(){
        if(this.messageSourceFactory == null){
            this.messageSourceFactory = defaultMessageSourceFactory;
        }
        return this.messageSourceFactory;
    }

    private ServletWrapper getServletWrapper(){
        if(this.servletWrapper == null){
            return defaultServletWrapper;
        }
        return this.servletWrapper;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {
        logger.error("系统异常 ：{}", Throwables.toString(ex));
        String viewName = processResponseStatus(request, response, ex);
        if (!isAjax(request)) {
        }
        return createModelAndView(viewName,request, ex);
    }

    private boolean isAjax(HttpServletRequest request) {
        return getServletWrapper().isAjax();
    }

    private String processResponseStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String viewName = determineViewName(ex, request);
        Integer statusCode = determineStatusCode(request, viewName);
        if (statusCode != null) {
            applyStatusCodeIfPossible(request, response, statusCode);
        }
        return viewName;
    }

    private ModelAndView createModelAndView(String viewName,HttpServletRequest request, Exception ex) {
        String code = ex.getMessage();
        MessageSource messageSource = getMessageSourceFactory().lookup(request.getParameter("local"));
        return new ModelAndViewBuilder(viewName,messageSource).withCode(code).failure().creat();
    }
}