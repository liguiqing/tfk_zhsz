package com.zhezhu.sm;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Configuration
@PropertySource("classpath:/smTestConfig.properties")
@ComponentScan(value = "com.zhezhu.share.**",
        includeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION,value= Service.class),
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value= Component.class)},
        useDefaultFilters = false)
public class SmTestConfiguration {


}