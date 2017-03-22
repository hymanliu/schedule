package com.hyman.schedule.master;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyman.schedule.master.handler.JobCreateTracker;

public class Application {
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		JettyEmbedServer jettyEmbedServer = (JettyEmbedServer)context.getBean("jettyEmbedServer");
		jettyEmbedServer.start();
		
		JobCreateTracker jobCreateTracker = (JobCreateTracker)context.getBean("jobCreateTracker");
		Thread thread = new Thread(jobCreateTracker);
		thread.start();
		
		jettyEmbedServer.join();
	}
}