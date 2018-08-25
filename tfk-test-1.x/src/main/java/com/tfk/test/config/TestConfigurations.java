package com.tfk.test.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Configuration
@EnableCaching
public class TestConfigurations {

    @Bean("cacheManager")
    public CacheManager cacheManager() {
        CompositeCacheManager cacheManager = new CompositeCacheManager();
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("classpath:ehcache.xml"));
        bean.setShared(true);
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(bean.getObject());
        cacheManager.setCacheManagers(Arrays.asList(ehCacheCacheManager));
        cacheManager.setFallbackToNoOpCache(true);
        return cacheManager;
    }
}