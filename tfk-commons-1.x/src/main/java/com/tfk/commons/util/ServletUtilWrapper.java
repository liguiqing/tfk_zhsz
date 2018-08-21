package com.tfk.commons.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ServletUtilWrapper {


    public static HttpServletRequest getRequest() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return req;
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse resp = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
        return resp;
    }

    public static boolean isMobileApp(HttpServletRequest request) {
        //TODO
        return false;
    }

    public static boolean isAjax(HttpServletRequest request) {
        return ((request.getHeader("accept") != null
                && request.getHeader("accept").indexOf("application/json") > -1)
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)
                || (request.getHeader("Content-Type") != null
                && request.getHeader("Content-Type").indexOf("multipart/form-data") > -1));
    }
}