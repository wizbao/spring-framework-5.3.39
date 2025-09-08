package com.wizbao.cycleDepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zwb
 * @date 2025/9/8 23:21
 * @since 2025.0.1
 **/
public class CycleDependsMainTest {

	private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	private static Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	private static void loadBeanDefinitions() {
		RootBeanDefinition abd = new RootBeanDefinition(ClassA.class);
		RootBeanDefinition bbd = new RootBeanDefinition(ClassB.class);
		beanDefinitionMap.put("classA", abd);
		beanDefinitionMap.put("classB", bbd);
	}

	public static void main(String[] args) throws Exception{
		loadBeanDefinitions();
		for (String beanName : beanDefinitionMap.keySet()) {
			getBean(beanName);
		}
	}

	private static Object getBean(String beanName) throws InstantiationException, IllegalAccessException {
		// 1.实例化
		RootBeanDefinition bd = (RootBeanDefinition) beanDefinitionMap.get(beanName);
		Class<?> clazz = bd.getBeanClass();
		// 调用无参构造器创建对象
		Object instance = clazz.newInstance();

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

		// 加入缓存
		singletonObjects.put(beanName, instance);
		return instance;
	}

}
