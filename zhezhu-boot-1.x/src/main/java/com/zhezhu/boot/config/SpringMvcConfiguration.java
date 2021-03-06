package com.zhezhu.boot.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.zhezhu.commons.spring.SpringMvcExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Configuration
public class SpringMvcConfiguration extends WebMvcConfigurationSupport {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.debug("Configure Message Converters");
        super.configureMessageConverters(converters);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteEnumUsingToString);
        FastJsonHttpMessageConverter c1 = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_STREAM_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        c1.setSupportedMediaTypes(supportedMediaTypes);
        c1.setFastJsonConfig(fastJsonConfig);
        converters.add(c1);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("Configure Resourece Handlers");
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/resources/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //TODO添加自定义的拦截器
        super.addInterceptors(registry);
    }
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }

    @Bean(name = "viewResolver")
    @Primary
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        List<View> views = new ArrayList<View>();
        views.add(fastJsonJsonView());

        //ArrayList<ViewResolver> viewResolvers = new ArrayList<>();
        //viewResolvers.add(freeMarkerViewResolver);

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        //viewResolver.setViewResolvers(viewResolvers);
        viewResolver.setDefaultViews(views);

        return viewResolver;
    }

    @Bean
    public FastJsonJsonView fastJsonJsonView() {

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteEnumUsingToString);
        fastJsonConfig.setDateFormat("yyyy-MM-dd");
        FastJsonJsonView view = new FastJsonJsonView();
        view.setFastJsonConfig(fastJsonConfig);
        return view;
    }

    @Bean("exceptionResolver")
    public SpringMvcExceptionResolver exceptionResolver(){
        SpringMvcExceptionResolver exceptionResolver = new SpringMvcExceptionResolver();
        exceptionResolver.setDefaultErrorView("/404");
        Properties mappings = new Properties();
        mappings.setProperty("java.lang.Exception", "505");
        exceptionResolver.setExceptionMappings(mappings);
        Properties statusCodes = new Properties();
        statusCodes.setProperty("/500","500");
        statusCodes.setProperty("/401","401");
        statusCodes.setProperty("/403","403");
        statusCodes.setProperty("/404","404");
        statusCodes.setProperty("/405","405");
        exceptionResolver.setStatusCodes(statusCodes);
        return  exceptionResolver;
    }
}