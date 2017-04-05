package com.hyman.schedule.master.core;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.service.JobService;

@Component("stateChangeTracker")
public class StateChangeTracker implements Runnable {

	static final Logger LOG = LoggerFactory.getLogger(StateChangeTracker.class);
	static final int BATCH_SIZE=10;
	static final int MAX_TRIES=3;
	static final int MAX_OVERTIME_MINUTE=60;
	
	@Autowired JobService jobService;
	
	@Override
	public void run() {
		
		while(true){
			
			try{
				List<Job> waitingJobs = jobService.findAvailableWaitingJob(MAX_TRIES, BATCH_SIZE);
				for(Job job : waitingJobs){
					job.setState(JobState.READY);
				}
				jobService.save(waitingJobs);
				
				List<Job> overtimeJobs = jobService.findOverTimeJob(MAX_OVERTIME_MINUTE, BATCH_SIZE);
				for(Job job : overtimeJobs){
					
					//TODO Call the api to kill the Job process.
					//JobService.killJob(job);
					
					job.setState(JobState.FAILED);
					job.setEndTime(new Date());
				}
				jobService.save(overtimeJobs);
				
				if(waitingJobs.size() < BATCH_SIZE){
					Thread.sleep(10000);
				}
			}
			catch(Exception e){
				LOG.error("State Change Tracker error",e);
			}
		}
	}
	
}
