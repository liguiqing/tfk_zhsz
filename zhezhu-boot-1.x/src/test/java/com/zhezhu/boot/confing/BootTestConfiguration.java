package com.zhezhu.boot.confing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Configuration
@PropertySource("classpath:META-INF/spring/bootConfig.properties")
@PropertySource("classpath:bootTestConfig.properties")
//@ComponentScan(value = "com.zhezhu.boot.**",
//        includeFilters = {
//                @ComponentScan.Filter(type= FilterType.ANNOTATION,value= Service.class),
//                @ComponentScan.Filter(type=FilterType.ANNOTATION,value= Component.class)},
//        excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value= Controller.class)},
//        useDefaultFilters = false)
public class BootTestConfiguration {


}