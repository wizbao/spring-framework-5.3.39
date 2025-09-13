package com.wizbao.cycleDepends;

import com.wizbao.cycleDepends.proxy.JdkProxyBeanPostProcessor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zwb
 * @date 2025/9/8 23:21
 * @since 2025.0.1
 **/
public class CycleDependsMainTest {

	private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	// 加入一级缓存（存放完整对象）
	private static Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	// 加入二级缓存（存放不完整对象）
	private static Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

	// spring的生命周期顺序是实例化=》属性填充=》初始化=》方法增强AOP，AOP每次可能生成不同的代理对象，由于AOP在属性填充之后，所以属性填充过程中
	// 没办法用到AOP生成的代理对象，导致依赖注入的错误。所以需要引入可以将AOP提前到实例化之后，属性填充之前的三级缓存
	private static Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

	// 当前循环依赖的bean，是否正在创建中
	private static Set<String> singletonCurrentlyInCreation = new HashSet<>();

	private static void loadBeanDefinitions() {
		RootBeanDefinition abd = new RootBeanDefinition(ClassA.class);
		RootBeanDefinition bbd = new RootBeanDefinition(ClassB.class);
		beanDefinitionMap.put("classA", abd);
		beanDefinitionMap.put("classB", bbd);
	}

	public static void main(String[] args) throws Exception {
		loadBeanDefinitions();
		for (String beanName : beanDefinitionMap.keySet()) {
			getBean(beanName);
		}
		ClassA classA = (ClassA) getBean("classA");
		classA.execute();
	}

	private static Object getBean(String beanName) throws InstantiationException, IllegalAccessException {
		Object singleton = getSingleton(beanName);
		if (singleton != null) {
			return singleton;
		}

		if (!singletonCurrentlyInCreation.contains(beanName)) {
			singletonCurrentlyInCreation.add(beanName);
		}

		// 1.实例化
		RootBeanDefinition bd = (RootBeanDefinition) beanDefinitionMap.get(beanName);
		Class<?> clazz = bd.getBeanClass();
		// 调用无参构造器创建对象
		Object instance = clazz.newInstance();

		// 加入二级缓存（存放不完整对象）
		earlySingletonObjects.put(beanName, instance);


		// 加入三级缓存
		if (singletonCurrentlyInCreation.contains(beanName)) {
			singletonFactories.put(beanName, () -> new JdkProxyBeanPostProcessor().getEarlyBeanReference(earlySingletonObjects.get(beanName), beanName));
		}

		// 2.属性填充
		for (Field field : clazz.getDeclaredFields()) {
			Autowired annotation = field.getAnnotation(Autowired.class);
			if (annotation != null) {
				field.setAccessible(true);
				// 递归调用getBean方法
				Object dep = getBean(field.getName());
				field.set(instance, dep);
			}
		}

		// 3.初始化（调用一些 init method）

		// 4.方法增强 AOP

		// 加入一级缓存（存放完整对象）
		singletonObjects.put(beanName, instance);
		earlySingletonObjects.remove(beanName);
		singletonFactories.remove(beanName);
		return instance;
	}

	private static Object getSingleton(String beanName) {
		Object singleton = singletonObjects.get(beanName);
		if (singleton == null && singletonCurrentlyInCreation.contains(beanName)) {
			singleton = earlySingletonObjects.get(beanName);
			if (singleton == null) {
				ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
				if (objectFactory != null) {
					singleton = objectFactory.getObject();
					earlySingletonObjects.put(beanName,singleton);
					singletonFactories.remove(beanName);
				}
			}
		}
		return singleton;
	}

}
