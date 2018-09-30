package com.zhezhu.boot.config;

import com.zhezhu.commons.security.UserFaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableCaching
@ComponentScan(basePackages = {"com.zhezhu.**.config"})
@ComponentScan(basePackages = {"com.zhezhu.**.controller"})
@PropertySource("classpath:/META-INF/spring/bootConfig.properties")
public class TfkBootConfiguration {

    @Bean
    public UserFaceService userFaceService(){
        return new UserFaceService();
    }
}