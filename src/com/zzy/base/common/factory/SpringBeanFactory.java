package com.zzy.base.common.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
@Service
public class SpringBeanFactory implements ApplicationContextAware {

	
	public static ApplicationContext context;
	
	/**
	 * 
	 * 根据Bean名称获取对象实例
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 清空spring框架配置信息
	 */
	public static void clear() {
		if (context != null) {
			context = null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext app)throws BeansException {
		context = app;
	}

}
