package com.hyman.schedule.master.rpc;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.ClusterInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

@Component("heartBeatRPCImpl")
public class HeartBeatRPCImpl implements HeartBeatRPC {
	
	
	@Override
	public Response<ClusterInfo> doBeat(String hostname,Integer port) {
		
		Response<ClusterInfo> ret = null;
		
		if(StringUtils.isBlank(hostname) || port==null){
			ret = Response.error("hostname or port is null");
		}
		else{
			ClusterInfo clusterInfo = new ClusterInfo();
			//TODO read from the configuration rather than using the har code.
			clusterInfo.setActiveMater(new NodeInfo("localhost",5566));
			
			ret = Response.ok(clusterInfo);
		}
		return ret;
	}

}
