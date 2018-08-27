package com.tfk.assessment.config;

import com.tfk.commons.config.MappingResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Configuration
@EnableCaching
@EnableJpaRepositories(value = "com.tfk.assessment.domain.model.**.*",
        includeFilters = {@ComponentScan.Filter(type=FilterType.ANNOTATION,value=Repository.class)})
@ComponentScan(value = "com.tfk.assessment.**",
        includeFilters = {
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Service.class),
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Component.class)},
        useDefaultFilters = false)
@PropertySource("classpath:/META-INF/spring/asConfig.properties")
public class AssessmentApplicationConfiguration {

    @Bean
    MappingResource mappingResource(){
        return ()->new String[]{
                "/hbm/Assess.hbm.xml",
                "/hbm/Assessee.hbm.xml",
                "/hbm/Assessor.hbm.xml",
                "/hbm/Award.hbm.xml",
                "/hbm/Index.hbm.xml",
                "/hbm/Medal.hbm.xml"
        };
    }
}