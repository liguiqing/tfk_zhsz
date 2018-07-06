/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */
package com.tfk.commons.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ClassName: SpringContextUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年3月25日 下午2:35:28 <br/>
 * 
 * @author 刘海林
 * @version
 * @since JDK 1.7+
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class clazz) {
		return (T) applicationContext.getBean(clazz);
	}

	public static void registerBean(String beanId,String beanClassName,Map<String,Object> pvs) {
		BeanDefinition bdef = new GenericBeanDefinition();
		bdef.setBeanClassName(beanClassName);
		if(pvs != null) {
			for (String p : pvs.keySet()) {
				bdef.getPropertyValues().add(p, pvs.get(p));
			}
		}

		DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
		fty.registerBeanDefinition(beanId, bdef);
	}
}