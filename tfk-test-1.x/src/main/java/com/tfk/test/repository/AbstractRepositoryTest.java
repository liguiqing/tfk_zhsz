/**
 * 
 * 
 **/
package com.ez.test.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public abstract class AbstractRepositoryTest {
	protected Logger logger =  LoggerFactory.getLogger(getClass());
	
	protected static final String JdbcDriverClassName = "jdbc.driverClassName";
	
	protected static final String JdbcUrl = "jdb.url";
	
	protected static final String JdbcUserName = "jdbc.username";
	
	protected static final String JdbcPassword = "jdbc.password";
	
	protected static Properties properties = new Properties();
	
	public AbstractRepositoryTest() {
		this.loadConf("/conf.properties");
	}
	
	protected void loadConf(String propertiesFileName) {
		try {
			properties.load(getClass().getResourceAsStream(propertiesFileName));
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
