package com.hyman.schedule.slave;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyman.schedule.slave.core.HeartBeatTracker;



public class ScheduleSlave {

	public static void main(String[] args) throws Exception {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		JettyEmbedServer jettyEmbedServer = (JettyEmbedServer)context.getBean("jettyEmbedServer");
		
		HeartBeatTracker heartBeatTracker = (HeartBeatTracker)context.getBean("heartBeatTracker");
		Thread heartBeatTrackerThread = new Thread(heartBeatTracker);
		heartBeatTrackerThread.start();
		
		jettyEmbedServer.start();
	}
}
