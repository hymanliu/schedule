package com.hyman.schedule.master.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.master.entity.Job;

@Service
public class MasterContext {
	
	private ConcurrentLinkedQueue<Job> jobQueue;
	private Map<NodeInfo,Long> slaveMap;
	private NodeInfo activeMaster;
	
	@Value("#{configProperties['master.host']}")
	private String masterHost;
	@Value("#{configProperties['master.port']}")
	private String masterPort;

	public MasterContext(){
		this.jobQueue = new ConcurrentLinkedQueue<Job>();
		this.slaveMap = new HashMap<>();
	}
	
	public boolean push(Job o){
		if(!jobQueue.contains(o)){
			return jobQueue.offer(o);
		}
		return false;
	}
	
	public boolean isQueueEmpty(){
		return jobQueue.isEmpty();
	}
	
	public Job poll(){
		return jobQueue.poll();
	}
	
	public Map<NodeInfo, Long> getSlaveMap() {
		return slaveMap;
	}

	public NodeInfo getActiveMaster() {
		synchronized(MasterContext.class){
			if(activeMaster==null){
				activeMaster = new NodeInfo(masterHost,Integer.parseInt(masterPort));
			}
		}
		return activeMaster;
	}
	
}
