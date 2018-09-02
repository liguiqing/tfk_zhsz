/**
 * <p><b>© 2015</b></p>
 * 
 **/

package com.zhezhu.zhezhu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
@WebAppConfiguration
@EnableWebMvc
public abstract class AbstractControllerTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mvc;
	
	@Before
	public void before() throws Exception{
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@After
	public void after() throws Exception{
		
	}

	protected String toJsonString(Object o)throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(o);
		return content;
	}
}

