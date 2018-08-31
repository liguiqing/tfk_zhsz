package com.zhezhu.commons.port.adaptor.http.controller;

/**
 * @author Liguiqing
 * @since V3.0
 */

public interface MessageSource {
     default String getMessage(String code){
         return code;
     }
}