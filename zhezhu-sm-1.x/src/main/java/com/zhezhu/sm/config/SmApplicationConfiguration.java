package com.zhezhu.sm.config;

import com.zhezhu.commons.config.MappingResource;
import com.zhezhu.share.infrastructure.validate.contact.*;
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
@EnableJpaRepositories(value = "com.zhezhu.sm.domain.model.**.*",
        includeFilters = {@ComponentScan.Filter(type=FilterType.ANNOTATION,value=Repository.class)})
@ComponentScan(value = "com.zhezhu.sm.**",
        includeFilters = {
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Service.class),
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Component.class)},
        useDefaultFilters = false)
@PropertySource("classpath:/META-INF/spring/smConfig.properties")
public class SmApplicationConfiguration {

    @Bean("SMMapping")
    MappingResource mappingResource(){
        return ()->new String[]{
                "/hbm/Clazz.hbm.xml",
                "/hbm/School.hbm.xml",
                "/hbm/Student.hbm.xml",
                "/hbm/Teacher.hbm.xml"
        };
    }

    @Bean("SMContactValidations")
    public ContactValidations contactValidations(){
        ContactValidations contactValidations = new ContactValidations();
        contactValidations
                .addValidator(new EmailValidator())
                .addValidator(new MobileValidator())
                .addValidator(new PhoneValidator())
                .addValidator(new QQValidator())
                .addValidator(new WeixinValidator());
        return  contactValidations;
    }

}