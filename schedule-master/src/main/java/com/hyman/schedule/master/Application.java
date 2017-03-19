package com.hyman.schedule.master;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyman.schedule.master.thread.TaskScanner;

public class Application {
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		JettyEmbedServer jettyEmbedServer = (JettyEmbedServer)context.getBean("jettyEmbedServer");
		jettyEmbedServer.start();
		
		TaskScanner taskScanner = (TaskScanner)context.getBean("taskScanner");
		Thread thread = new Thread(taskScanner);
		thread.start();
		
		jettyEmbedServer.join();
	}
}