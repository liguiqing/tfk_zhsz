package com.tfk.commons.port.adaptor.http.controller;

/**
 * @author Liguiqing
 * @since V3.0
 */

/**
 * 返回给客户端信息源工厂
 */
public interface MessageSourceFactory {

    default MessageSource lookup(String local){
        return new MessageSource(){};
    }
}