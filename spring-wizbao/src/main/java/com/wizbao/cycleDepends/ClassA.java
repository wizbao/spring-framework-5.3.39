package com.wizbao.cycleDepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zwb
 * @date 2025/9/8 23:16
 * @since 2025.0.1
 **/
@Component
public class ClassA implements IClassA{

	@Autowired
	private ClassB classB;

	public ClassA() {
		System.out.println("ClassA init");
	}

	public ClassA(ClassB classB) {
		this.classB = classB;
	}

	public ClassB getClassB() {
		return classB;
	}

	public void setClassB(ClassB classB) {
		this.classB = classB;
	}

	@Override
	public void execute() {
		System.out.println("i am class A");
	}
}
