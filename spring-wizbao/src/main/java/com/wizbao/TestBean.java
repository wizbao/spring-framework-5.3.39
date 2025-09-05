package com.wizbao;


import org.springframework.stereotype.Component;

/**
 * @author zwb
 * @date 2025/4/20 15:44
 * @since 2024.0.1
 **/
@Component
public class TestBean {
	private String name = "Hello World!!!";

	public TestBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
