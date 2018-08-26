package com.tfk.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tfk.access.domain.model.wechat.config.WeChatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableCaching
@ComponentScan(basePackages = {"com.tfk.**.config"})
@PropertySource("classpath:/META-INF/spring/bootConfig.properties")
public class TfkBootConfiguration {

    @Bean(name = "cacheManager")
    @Primary
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


    @Bean("dataSource")
    public DataSource dataSource(@Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password) throws SQLException {
        log.debug("Create DataSource {} {} {}",url,username,password);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        dataSource.setValidationQuery("SELECT 'X'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(100);
        dataSource.setFilters("stat");
        return dataSource;
    }

    @Bean("jdbcTemplate")
    @DependsOn("dataSource")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean("jpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter,
                                                                       Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        String[] mappings = {
                "/hbm/School.hbm.xml",
                "/hbm/Clazz.hbm.xml",
                "/hbm/Teacher.hbm.xml",
                "/hbm/Student.hbm.xml",
                "/hbm/Assess.hbm.xml",
                "/hbm/Assessee.hbm.xml",
                "/hbm/Assessor.hbm.xml",
                "/hbm/Index.hbm.xml",
                "/hbm/Award.hbm.xml",
                "/hbm/Medal.hbm.xml",
                "/hbm/WeChat.hbm.xml",
                "/hbm/FollowApply.hbm.xml",
                "/hbm/FollowAudit.hbm.xml"
        };
        entityManagerFactoryBean.setMappingResources(mappings);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
        return transactionManager;
    }

    @Bean
    public Properties jpaProperties(@Value("${hibernate.dialect}") String dialect,
                                    @Value("${hibernate.hbm2ddl.auto}") String auto,
                                    @Value("${hibernate.show_sql}") String showSql,
                                    @Value("${hibernate.format_sql}") String formatSql,
                                    @Value("${hibernate.jdbc.batch_size}") String batchSize,
                                    @Value("${hibernate.jdbc.fetch_size}") String fetchSize,
                                    @Value("${hibernate.max_fetch_depth}") String fetchDepth
                                    ){
        Properties jpaProperties  = new Properties();
        jpaProperties.setProperty("hibernate.dialect",dialect);
        jpaProperties.setProperty("hibernate.hbm2ddl.auto",auto);
        jpaProperties.setProperty("hibernate.show_sql",showSql);
        jpaProperties.setProperty("hibernate.format_sql",formatSql);
        jpaProperties.setProperty("hibernate.jdbc.batch_size",batchSize);
        jpaProperties.setProperty("hibernate.jdbc.fetch_size",fetchSize);
        jpaProperties.setProperty("hibernate.max_fetch_depth",fetchDepth);
        return jpaProperties;
    }

}