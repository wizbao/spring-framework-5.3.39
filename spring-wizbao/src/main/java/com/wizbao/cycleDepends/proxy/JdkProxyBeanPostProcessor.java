package com.wizbao.cycleDepends.proxy;

import com.wizbao.cycleDepends.ClassA;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zwb
 * @date 2025/9/13 15:23
 * @since 2025.0.1
 **/
@Component
public class JdkProxyBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

	public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
		if (bean instanceof ClassA) {
			JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(bean);
			return jdkDynamicProxy.getProxy();
		}
		return bean;
	}

}
