/**
 * <p><b>© 2016 深圳市天定康科技有限公司</b></p>
 * 
 **/
package com.zhezhu.zhezhu.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 * @author liguiqing
 * @Date 2016年9月28日
 * @Version 
 */

@RunWith(PowerMockRunner.class) 
@TestExecutionListeners({ ServletTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class,
	DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@WebAppConfiguration
@EnableWebMvc
public abstract class AbstractPowerMockitoMixedInSpringMvcControllerTest {
	private final TestContextManager testContextManager;
	
	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mvc;
	
	public AbstractPowerMockitoMixedInSpringMvcControllerTest(){
		this.testContextManager = new TestContextManager(getClass());
	}
	
	@Before
	public void injectDependencies() throws Throwable {
		this.testContextManager.prepareTestInstance(this);
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
}
