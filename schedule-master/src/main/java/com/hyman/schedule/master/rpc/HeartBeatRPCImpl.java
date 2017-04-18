package com.hyman.schedule.master.rpc;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.ServerInfo;
import com.hyman.schedule.master.core.MasterContext;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

@Component("heartBeatRPCImpl")
public class HeartBeatRPCImpl implements HeartBeatRPC {
	
	@Autowired MasterContext masterConext;
	@Override
	public Response<ServerInfo> doBeat(String hostname,Integer port) {
		
		if(!StringUtils.isBlank(hostname) && port!=null){
			ServerInfo master = masterConext.getActiveMaster();
			NodeInfo slave = new NodeInfo(hostname,port);
			masterConext.putIntoSlaveMap(slave);
			return Response.ok(master);
		}
		return Response.error("hostname or port is illegal");
	}

}
