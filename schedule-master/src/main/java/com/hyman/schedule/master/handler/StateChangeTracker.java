package com.hyman.schedule.master.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.master.service.JobService;

@Component("stateChangeTracker")
public class StateChangeTracker implements Runnable {

	static final Logger LOG = LoggerFactory.getLogger(StateChangeTracker.class);
	@Autowired JobService jobService;
	private int currentOffset = 0;
	private static final int BATCH_SIZE=2;
	@Override
	public void run() {
		
		
	}
	
}
