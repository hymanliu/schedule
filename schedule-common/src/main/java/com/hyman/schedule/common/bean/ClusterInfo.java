package com.hyman.schedule.common.bean;

public class ClusterInfo {

	private NodeInfo activeMater;
	private NodeInfo standyMaster;
	
	
	public NodeInfo getActiveMater() {
		return activeMater;
	}
	public void setActiveMater(NodeInfo activeMater) {
		this.activeMater = activeMater;
	}
	public NodeInfo getStandyMaster() {
		return standyMaster;
	}
	public void setStandyMaster(NodeInfo standyMaster) {
		this.standyMaster = standyMaster;
	}
}
