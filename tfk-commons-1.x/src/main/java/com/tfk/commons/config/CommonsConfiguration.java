package com.tfk.commons.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.tfk.commons.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
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
public class CommonsConfiguration {

    @Bean
    public SpringContextUtil contextUtil(ApplicationContext applicationContext){
        SpringContextUtil springContextUtil = new SpringContextUtil();
        springContextUtil.setApplicationContext(applicationContext);
        return springContextUtil;
    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        log.debug("Create Cache ");
        CompositeCacheManager cacheManager = new CompositeCacheManager();
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheCacheManager().getObject());
        cacheManager.setCacheManagers(Arrays.asList(ehCacheCacheManager));
        cacheManager.setFallbackToNoOpCache(true);
        return cacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }


    @Bean("dataSource")
    public DataSource dataSource(@Value("${jdbc.jndi.name:testJndiDs}") String jdbcJndiName,
                                 @Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password) throws SQLException {

        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        try{
            log.debug("Get DataSource from jndi {}",jdbcJndiName);
            DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/"+jdbcJndiName);
            return dataSource;
        }catch (Exception e){
            log.debug("DataSource not found with jndi {}",jdbcJndiName);
        }

        log.debug("Create DataSource {} {} {}",url,username,password);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(10);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(30000);
        druidDataSource.setValidationQuery("SELECT 'X'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(100);
        druidDataSource.setFilters("stat");
        return druidDataSource;
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(List<MappingResource> mappingResources,
                                                                       DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter,
                                                                       Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        if(mappingResources != null) {
            List<String> mappings = Lists.newArrayList();
            mappingResources.forEach(mappingResource -> mappings.addAll(Arrays.asList(mappingResource.getMappingResource())));
            String[] mr = new String[mappings.size()];
            mr = mappings.toArray(mr);
            log.debug("EntityManagerFactoryBean mapping resource {}",Arrays.toString(mr));
            entityManagerFactoryBean.setMappingResources(mr);

        }
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
//
//        DefaultPersistenceUnitManager unitManager = new DefaultPersistenceUnitManager();
//        unitManager.setPersistenceXmlLocations();
//        entityManagerFactoryBean.setPersistenceUnitManager(unitManager);
        return entityManagerFactoryBean;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
        return transactionManager;
    }

    @Bean
    public Properties jpaProperties(@Value("${hibernate.dialect:org.hibernate.dialect.MySQL5InnoDBDialect}") String dialect,
                                    @Value("${hibernate.hbm2ddl.auto:none}") String auto,
                                    @Value("${hibernate.show_sql:false}") String showSql,
                                    @Value("${hibernate.format_sql:true}") String formatSql,
                                    @Value("${hibernate.jdbc.batch_size:100}") String batchSize,
                                    @Value("${hibernate.jdbc.fetch_size:50}") String fetchSize,
                                    @Value("${hibernate.max_fetch_depth:10}") String fetchDepth
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