/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.ez.test.repository;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class JNDIDataSource {
    private static Logger logger = LoggerFactory.getLogger(JNDIDataSource.class);

    private String dsConf = "applicationContext-test-ds.xml";

    private String jdbcJndiName = "tfk";

    @Autowired
    private DruidDataSource ds;

    public JNDIDataSource() {
    }

    public JNDIDataSource(String dsConf,String jdbcJndiName) {
        this.dsConf = dsConf;
        this.jdbcJndiName = jdbcJndiName;

    }

    public void newJndiDataSource() {
        try {
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.bind("java:comp/env/jdbc/" + this.jdbcJndiName, ds);
            builder.activate();
        } catch (NamingException ex) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    public void setDsConf(String dsConf) {
        this.dsConf = dsConf;
    }

    public void setJdbcJndiName(String jdbcJndiName) {
        this.jdbcJndiName = jdbcJndiName;
    }
}