package com.hyman.schedule.slave.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.NodeInfo;

@Component
public class SlaveContext {

	static final Logger LOG = LoggerFactory.getLogger(SlaveContext.class);
	
	private NodeInfo activeMaster;

	public void setActiveMaster(NodeInfo nodeInfo) {
		LOG.info("set the active master...{}:{}",nodeInfo.getHostname(),nodeInfo.getPort());
		this.activeMaster = nodeInfo;
	}
	public NodeInfo getActiveMaster() {
		return activeMaster;
	}
	
}
