package com.tfk.commons.port.adaptor.http.controller;

import com.tfk.commons.util.ServletUtilWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface ServletWrapper {
    default HttpServletRequest getRequest(){
        return ServletUtilWrapper.getRequest();
    }

    default HttpServletResponse getResponse(){
        return ServletUtilWrapper.getResponse();
    }

    default boolean isAjax(){
        return ServletUtilWrapper.isAjax(this.getRequest());
    }

    default boolean isMobileApp(){
        return ServletUtilWrapper.isMobileApp(this.getRequest());
    }
}