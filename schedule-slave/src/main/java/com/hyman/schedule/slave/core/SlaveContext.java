package com.hyman.schedule.slave.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.master.rpc.HeartBeatRPC;
import com.hyman.schedule.master.rpc.MasterClientRPC;

public class SlaveContext {

	static final Logger LOG = LoggerFactory.getLogger(SlaveContext.class);
	private NodeInfo activeMaster;
	private MasterClientRPC masterClientRPC;
	private HeartBeatRPC heartBeatRPC;
	
	private static SlaveContext instance;
	
	private SlaveContext(){}
	
	public static SlaveContext getInstance(){
		synchronized(SlaveContext.class){
			if(instance==null){
				instance = new SlaveContext();
				instance.setActiveMaster(new NodeInfo("localhost",5566));
			}
		}
		return instance;
	}
	
	public void setActiveMaster(NodeInfo nodeInfo) {
		LOG.info("set the active master...{}:{}",nodeInfo.getHostname(),nodeInfo.getPort());
		this.activeMaster = nodeInfo;
		List<JacksonJsonProvider> providers1 = new ArrayList<>();
		JacksonJsonProvider jsonProvider1 = new JacksonJsonProvider ();
		providers1.add (jsonProvider1);
		StringBuffer sb = new StringBuffer("http://")
				.append(activeMaster.getHostname())
				.append(":")
				.append(activeMaster.getPort())
				.append("/master-rpc/api");
		this.masterClientRPC = JAXRSClientFactory.create(sb.toString(), MasterClientRPC.class,providers1,true);
		
		List<JacksonJsonProvider> providers2 = new ArrayList<>();
		JacksonJsonProvider jsonProvider2 = new JacksonJsonProvider ();
		providers2.add (jsonProvider2);
		this.heartBeatRPC =  JAXRSClientFactory.create(sb.toString(), HeartBeatRPC.class,providers2,true);
	}
	public NodeInfo getActiveMaster() {
		return activeMaster;
	}

	public MasterClientRPC getMasterClientRPC() {
		return masterClientRPC;
	}

	public HeartBeatRPC getHeartBeatRPC() {
		return heartBeatRPC;
	}
	
}
