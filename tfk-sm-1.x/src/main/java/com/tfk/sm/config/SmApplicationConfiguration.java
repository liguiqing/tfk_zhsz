package com.tfk.sm.config;

import com.tfk.share.infrastructure.validate.contact.*;
import lombok.extern.slf4j.Slf4j;
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
@EnableJpaRepositories(value = "com.tfk.sm.domain.model.**.*",
        includeFilters = {@ComponentScan.Filter(type=FilterType.ANNOTATION,value=Repository.class)})
@ComponentScan(value = "com.tfk.sm.**",
        includeFilters = {
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Service.class),
                @ComponentScan.Filter(type=FilterType.ANNOTATION,value=Component.class)},
        useDefaultFilters = false)
@PropertySource("classpath:/META-INF/spring/smConfig.properties")
public class SmApplicationConfiguration {

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