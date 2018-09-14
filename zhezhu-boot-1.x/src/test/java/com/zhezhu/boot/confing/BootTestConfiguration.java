package com.zhezhu.boot.confing;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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