package com.wizbao;

import com.wizbao.cycleDepends.ClassA;
import com.wizbao.event.MyEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeanTestMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		TestBean testBean = (TestBean) applicationContext.getBean("testBean");
		System.out.println("testBean = " + testBean.getName());

		applicationContext.publishEvent(new MyEvent("", "Hello World"));
	}
}