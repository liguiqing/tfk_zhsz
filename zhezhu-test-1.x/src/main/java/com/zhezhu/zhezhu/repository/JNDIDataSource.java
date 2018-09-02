/*
 * Copyright (c) 2016,2017, zhezhu All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.zhezhu.zhezhu.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class JNDIDataSource {
    private static Logger logger = LoggerFactory.getLogger(JNDIDataSource.class);


    private String jdbcJndiName = "testJndiDs";

    @Autowired
    private DataSourceProperty dataSourceProperty;

    public JNDIDataSource() {
    }

    public void newJndiDataSource() {
        try {
            DataSource dataSource = dataSourceProperty.getDataSource();
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.bind("java:comp/env/jdbc/" + this.jdbcJndiName, dataSource);
            builder.activate();
        } catch (NamingException ex) {
            logger.error(ex.getLocalizedMessage());
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    public void setJdbcJndiName(String jdbcJndiName) {
        this.jdbcJndiName = jdbcJndiName;
    }
}