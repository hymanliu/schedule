package com.hyman.schedule.slave.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.ServerInfo;
import com.hyman.schedule.master.rpc.HeartBeatRPC;
import com.hyman.schedule.master.rpc.MasterClientRPC;

public class SlaveContext {

	static final Logger LOG = LoggerFactory.getLogger(SlaveContext.class);
	private ServerInfo activeMaster;
	private NodeInfo nodeInfo;
	
	private MasterClientRPC masterClientRPC;
	private HeartBeatRPC heartBeatRPC;
	
	private static SlaveContext instance;
	
	private SlaveContext(){
		System.out.println("construct SlaveContext");
	}
	
	public static SlaveContext getInstance(){
		synchronized(SlaveContext.class){
			if(instance==null){
				instance = new SlaveContext();
			}
		}
		return instance;
	}
	
	public void setActiveMaster(ServerInfo serverInfo) {
		if(nodeInfo.equals(this.activeMaster)) return;
		LOG.info("set the active master...{}:{}",nodeInfo.getHostname(),nodeInfo.getPort());
		this.activeMaster = serverInfo;
		if(this.activeMaster==null){
			this.masterClientRPC = null;
			this.heartBeatRPC = null;
		}else{
			List<JacksonJsonProvider> providers = new ArrayList<>();
			JacksonJsonProvider jsonProvider = new JacksonJsonProvider ();
			providers.add (jsonProvider);
			StringBuffer sb = new StringBuffer("http://")
					.append(activeMaster.getHost())
					.append(":")
					.append(activeMaster.getPort())
					.append("/master-rpc/api");
			this.masterClientRPC = JAXRSClientFactory.create(sb.toString(), MasterClientRPC.class,providers,true);
			this.heartBeatRPC =  JAXRSClientFactory.create(sb.toString(), HeartBeatRPC.class,providers,true);
		}
	}
	public ServerInfo getActiveMaster() {
		return activeMaster;
	}

	public MasterClientRPC getMasterClientRPC() {
		return masterClientRPC;
	}

	public HeartBeatRPC getHeartBeatRPC() {
		return heartBeatRPC;
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
}
