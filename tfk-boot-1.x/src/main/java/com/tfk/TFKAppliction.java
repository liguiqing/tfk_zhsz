package com.tfk;

import com.tfk.boot.config.SpringMvcConfiguration;
import com.tfk.boot.config.TfkBootConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.tfk.boot.config"})
@ImportResource({"classpath*:META-INF/spring/*.xml"})
public class TFKAppliction extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception{
        log.debug("TFK Boot Starting ...");
        SpringApplication app = new SpringApplication(TFKAppliction.class);
        //app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        log.debug("Add Configurations ");
        application.sources(SpringMvcConfiguration.class);
        application.sources(TfkBootConfiguration.class);
        return super.configure(application);
    }

}