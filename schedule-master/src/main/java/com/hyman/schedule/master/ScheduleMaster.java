package com.hyman.schedule.master;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyman.schedule.master.core.JobCreateTracker;
import com.hyman.schedule.master.core.StateChangeTracker;

public class ScheduleMaster {
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		JettyEmbedServer jettyEmbedServer = (JettyEmbedServer)context.getBean("jettyEmbedServer");
		jettyEmbedServer.start();
		
		JobCreateTracker jobCreateTracker = (JobCreateTracker)context.getBean("jobCreateTracker");
		Thread jobCreateTrackerThread = new Thread(jobCreateTracker);
		jobCreateTrackerThread.start();
		
		
		StateChangeTracker stateChangeTracker =  (StateChangeTracker)context.getBean("stateChangeTracker");
		Thread stateChangeTrackerThread = new Thread(stateChangeTracker);
		stateChangeTrackerThread.start();
		
		jettyEmbedServer.join();
	}
}