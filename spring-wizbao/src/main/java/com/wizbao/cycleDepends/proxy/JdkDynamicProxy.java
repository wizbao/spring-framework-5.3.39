package com.wizbao.cycleDepends.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zwb
 * @date 2025/9/13 15:20
 * @since 2025.0.1
 **/
public class JdkDynamicProxy implements InvocationHandler {

	private Object target;

	public JdkDynamicProxy(Object target) {
		this.target = target;
	}

	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}
}
