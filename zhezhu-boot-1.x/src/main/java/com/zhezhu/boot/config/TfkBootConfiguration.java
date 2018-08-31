package com.zhezhu.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableCaching
@ComponentScan(basePackages = {"com.zhezhu.**.config"})
@PropertySource("classpath:/META-INF/spring/bootConfig.properties")
public class TfkBootConfiguration {


}