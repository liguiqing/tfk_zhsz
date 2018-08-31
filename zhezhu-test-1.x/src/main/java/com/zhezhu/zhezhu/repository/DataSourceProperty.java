package com.zhezhu.zhezhu.repository;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class DataSourceProperty {
    private String url;

    private String username;

    private String password;

    private int initialSize = 1;

    private int minIdle = 1;

    private int maxActive = 20;

    public DataSourceProperty(){}

    public DataSourceProperty(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public DataSourceProperty(String url, String username, String password, int initialSize, int minIdle, int maxActive) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.initialSize = initialSize;
        this.minIdle = minIdle;
        this.maxActive = maxActive;
    }

    public DataSource getDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMinIdle(this.minIdle);
        dataSource.setMaxActive(this.maxActive);
        dataSource.setMaxWait(6000);
        dataSource.setTimeBetweenEvictionRunsMillis(300000);
        dataSource.setValidationQuery("SELECT 'x' ");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(100);
        dataSource.setFilters("stat");
        dataSource.init();
        return dataSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }
}