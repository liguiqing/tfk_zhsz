package com.zhezhu.commons.port.adaptor.http.controller;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ModelAndViewBuilder {

    private HashMap<String, Object> model;

    private String viewName;

    private String code;

    private boolean success = true;

    private MessageSource messageSource;

    private ModelAndViewBuilder(){
        this.model = new HashMap<>();

    }

    public ModelAndViewBuilder(String viewName){
        this();
        this.viewName = viewName;
    }

    public ModelAndViewBuilder(String viewName,MessageSource messageSource){
        this(viewName);
        this.messageSource = messageSource;
    }

    public ModelAndViewBuilder withView(String view){
        this.viewName = view;
        return this;
    }

    public ModelAndViewBuilder withData(String name,Object data){
        this.model.put(name,data);
        return this;
    }

    public ModelAndViewBuilder withCode(String code){
        this.code = code;
        return this;
    }

    public ModelAndViewBuilder failure(){
        this.success = false;
        return this;
    }

    public ModelAndView creat(){
        HttpAdaptorResponse response = createResponse();

        this.model.put(HttpAdaptorResponse.ModelName, response);
        return new ModelAndView(this.viewName, this.model);
    }

    private HttpAdaptorResponse createResponse(){
        if(this.success){
            return new HttpAdaptorResponse.Builder().code(this.code).msg(this.messageSource).success().create();
        }

        return new HttpAdaptorResponse.Builder().code(this.code).msg(this.messageSource).failure().create();
    }
}