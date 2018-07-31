/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.test.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class JNDIDataSource {
    private static Logger logger = LoggerFactory.getLogger(JNDIDataSource.class);


    private String jdbcJndiName = "testJndiDs";

    @Autowired
    private DataSource ds;

    public JNDIDataSource() {
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

    public void setJdbcJndiName(String jdbcJndiName) {
        this.jdbcJndiName = jdbcJndiName;
    }
}