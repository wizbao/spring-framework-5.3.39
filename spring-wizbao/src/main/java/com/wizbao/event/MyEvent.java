package com.wizbao.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zwb
 * @date 2025/9/7 14:20
 * @since 2025.0.1
 **/
public class MyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String msg;

	public MyEvent(Object source) {
		super(source);
	}

	public MyEvent(Object source, String msg) {
		super(source);
		this.msg = msg;
	}

	public void printMsg() {
		System.out.println("===================");
		System.out.println("msg = " + msg);
		System.out.println("===================");
	}
}
