package com.hyman.schedule.master.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.hyman.schedule.common.bean.JobInfo;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.service.JobService;
import com.hyman.schedule.master.service.TaskService;
import com.hyman.schedule.slave.rpc.JobClientRPC;

@Component("masterContextTracker")
public class MasterContextTracker implements Runnable {

	static final Logger LOG = LoggerFactory.getLogger(MasterContextTracker.class);
	static final long LOAD_READY_JOB_DURATION = 20*1000L;
	@Autowired TaskService taskService;
	@Autowired JobService jobService;
	@Autowired MasterContext masterContext;
	
	private long lastestLoadTime = 0L;
	
	
	private boolean isActive = true;
	
	@Override
	public void run() {
		while(isActive){
			long duration = System.currentTimeMillis() - lastestLoadTime;
			if(masterContext.isQueueEmpty() && duration>=LOAD_READY_JOB_DURATION){
				List<Job> jobs = jobService.listJob(20, JobState.READY);
				for(Job job : jobs){
					masterContext.push(job);
				}
			}
			List<JacksonJsonProvider> providers = new ArrayList<>();
			JacksonJsonProvider jsonProvider = new JacksonJsonProvider ();
			providers.add (jsonProvider);
			//TODO
			JobClientRPC jobClientRPC = JAXRSClientFactory.create("http://localhost:5577/slave-rpc/api", JobClientRPC.class,providers,true);
			
			
			Job job = masterContext.poll();
			if(job!=null){
				JobInfo jobInfo = toJobInfo(job);
				try{
					boolean isSuccess = jobClientRPC.distribute(jobInfo);
					if(isSuccess){
						job.setState(JobState.EXEC);
						jobService.save(job);
						try {
							Thread.sleep(1000*1000L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				catch(Exception e){
					LOG.error("JobClientRPC distribute jobInfo error,commnad:{}, params:{}",jobInfo.getCommand(),jobInfo.getParams());
				}
			}
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//TODO
	private JobInfo toJobInfo(Job job) {
		JobInfo jobInfo = new JobInfo();
		//TODO
		jobInfo.setCommand("notepad");
		return jobInfo;
	}
}
