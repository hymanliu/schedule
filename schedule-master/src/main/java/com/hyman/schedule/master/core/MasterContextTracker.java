package com.hyman.schedule.master.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.hyman.schedule.common.bean.JobInfo;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.config.ConfigurationContext;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.service.JobService;
import com.hyman.schedule.master.service.TaskService;
import com.hyman.schedule.slave.rpc.JobClientRPC;

@Component
public class MasterContextTracker implements Runnable {

	static final Logger LOG = LoggerFactory.getLogger(MasterContextTracker.class);
	static final long LOAD_READY_JOB_DURATION = 20*1000L;
	@Resource TaskService taskService;
	@Resource JobService jobService;
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
			NodeInfo node = randomAquireSlaveNode();
			distributeJob(node);
			
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public NodeInfo randomAquireSlaveNode(){
		Map<NodeInfo,Long> slaveMap = masterContext.getSlaveMap();
		List<NodeInfo> slaves = new ArrayList<>();
		for(NodeInfo k : slaveMap.keySet()){
			Long time = slaveMap.get(k);
			long timeOut = Long.parseLong(ConfigurationContext.getValue("schedule.node.timeout","0"));
			if(System.currentTimeMillis() - time < timeOut){
				slaves.add(k);
			}
		}
		if(slaves.size()>0){
			Random random = new Random();
			int index = random.nextInt(slaves.size());
			return slaves.get(index);
		}
		return null;
	}
	
	public Job distributeJob(NodeInfo node) {
		if(node==null) return null;
		List<JacksonJsonProvider> providers = new ArrayList<>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider ();
		providers.add (jsonProvider);
		StringBuffer sb = new StringBuffer("http://")
				.append(node.getHostname())
				.append(":")
				.append(node.getPort())
				.append("/slave-rpc/api");
		JobClientRPC jobClientRPC = JAXRSClientFactory.create(sb.toString(), JobClientRPC.class,providers,true);
		Job job = masterContext.poll();
		if(job!=null){
			JobInfo jobInfo = toJobInfo(job);
			try{
				boolean isSuccess = jobClientRPC.distribute(jobInfo);
				if(isSuccess){
					job.setState(JobState.EXEC);
					job.setBeginTime(new Date());
					job.setTries(job.getTries()==null?1:job.getTries()+1);
					jobService.save(job);
					
					//TODO 以下代码测试用的 ，到时候删除
					try {
						Thread.sleep(1000*1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					////////////////////////////
					return job;
				}
			}
			catch(Exception e){
				LOG.error("JobClientRPC distribute jobInfo error,commnad:{}, params:{}",jobInfo.getCommand(),jobInfo.getParams());
				//节点异常
				masterContext.getSlaveMap().remove(node);
			}
		}
		return null;
	}

	//TODO
	private JobInfo toJobInfo(Job job) {
		JobInfo jobInfo = new JobInfo();
		//TODO
		jobInfo.setCommand("notepad");
		return jobInfo;
	}
}
