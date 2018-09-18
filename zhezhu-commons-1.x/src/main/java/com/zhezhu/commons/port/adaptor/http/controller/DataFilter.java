package com.zhezhu.commons.port.adaptor.http.controller;

/**
 * 返回给客户的数据过滤器
 *
 * @author Liguiqing
 * @since V3.0
 */
@FunctionalInterface
public interface DataFilter<T> {
    void  filter(T t);
}