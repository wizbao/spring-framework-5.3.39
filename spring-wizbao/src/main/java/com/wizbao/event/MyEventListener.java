package com.wizbao.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zwb
 * @date 2025/9/7 14:25
 * @since 2025.0.1
 **/
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {

	@Override
	public void onApplicationEvent(MyEvent event) {
		event.printMsg();
	}
}
