package com.tfk.commons.port.adaptor.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liguiqing
 * @since V3.0
 */


public abstract class AbstractHttpController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final static ServletWrapper defaultServletWrapper = new ServletWrapper() {};

    private final static MessageSourceFactory defaultMessageSourceFactory = new MessageSourceFactory() {};

    @Autowired(required=false)
    private ServletWrapper servletWrapper;

    @Autowired(required=false)
    private MessageSourceFactory messageSourceFactory;



    protected ServletWrapper getServletWrapper(){
        if(this.servletWrapper == null){
            this.servletWrapper = defaultServletWrapper;
        }
        return this.servletWrapper;
    }

    protected MessageSourceFactory getMessageSourceFactory(){
        if(this.messageSourceFactory == null){
            this.messageSourceFactory = defaultMessageSourceFactory;
        }
        return this.messageSourceFactory;
    }

    protected HttpServletRequest getRequest(){
        return this.getServletWrapper().getRequest();
    }

    protected HttpServletResponse getResponse(){
        return this.getServletWrapper().getResponse();
    }

    protected ModelAndViewBuilder newModelAndViewBuilder(String view){
        return this.newModelAndViewBuilder(view,getRequest());
    }

    protected ModelAndViewBuilder newModelAndViewBuilder(String view,HttpServletRequest request){
        MessageSource messageSource = getMessageSourceFactory().lookup(request.getParameter("local"));
        return new ModelAndViewBuilder(view,messageSource);
    }
}