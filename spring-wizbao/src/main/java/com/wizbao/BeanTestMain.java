package com.wizbao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanTestMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		TestBean testBean = (TestBean) annotationConfigApplicationContext.getBean("testBean");
		System.out.println("testBean = " + testBean.getName());
	}
}