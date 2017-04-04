package com.hyman.schedule.master.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.master.service.JobService;
import com.hyman.schedule.master.service.TaskService;

@Component("masterContextTracker")
public class MasterContextTracker implements Runnable {

	static final Logger LOG = LoggerFactory.getLogger(MasterContextTracker.class);
	@Autowired TaskService taskService;
	@Autowired JobService jobService;
	
	private boolean isActive = true;
	
	@Override
	public void run() {
		while(isActive){
			
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
