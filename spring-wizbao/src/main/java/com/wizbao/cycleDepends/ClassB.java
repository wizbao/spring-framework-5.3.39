package com.wizbao.cycleDepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zwb
 * @date 2025/9/8 23:16
 * @since 2025.0.1
 **/
@Component
public class ClassB {

	@Autowired
	private ClassA classA;

	public ClassB() {
		System.out.println("ClassB init");
	}

	public ClassB(ClassA classA) {
		this.classA = classA;
	}

	public ClassA getClassA(){
		return classA;
	}

	public void setClassA(ClassA classA) {
		this.classA = classA;
	}
}
