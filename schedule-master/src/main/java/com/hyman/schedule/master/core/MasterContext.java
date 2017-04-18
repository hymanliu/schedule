package com.hyman.schedule.master.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.ServerInfo;
import com.hyman.schedule.master.entity.Job;

public class MasterContext {
	
	private ConcurrentLinkedQueue<Job> jobQueue;
	private Map<NodeInfo,Long> slaveMap;
	private ServerInfo activeMaster;
	
	private static MasterContext instance;

	private MasterContext(){
		this.jobQueue = new ConcurrentLinkedQueue<Job>();
		this.slaveMap = new ConcurrentHashMap<>();
	}
	
	public static MasterContext getInstance(){
		synchronized(MasterContext.class){
			if(instance==null){
				instance = new MasterContext();
			}
		}
		return instance;
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
	
	public Long putIntoSlaveMap(NodeInfo nodeInfo){
		return slaveMap.put(nodeInfo, System.currentTimeMillis());
	}

	public ServerInfo getActiveMaster() {
		return activeMaster;
	}
	public void setActiveMaster(ServerInfo activeMaster) {
		this.activeMaster = activeMaster;
	}
	
}
