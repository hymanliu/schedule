package com.hyman.schedule.master.rpc;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.ClusterInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.config.ConfigurationContext;
import com.hyman.schedule.master.core.MasterConext;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

@Component("heartBeatRPCImpl")
public class HeartBeatRPCImpl implements HeartBeatRPC {
	
	@Resource MasterConext masterConext;
	@Override
	public Response<ClusterInfo> doBeat(String hostname,Integer port) {
		
		if(!StringUtils.isBlank(hostname) && port!=null){
			ClusterInfo clusterInfo = new ClusterInfo();

//			Message message = PhaseInterceptorChain.getCurrentMessage();  
//			HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST); 
//			System.out.println(request.getRemotePort()+"remote port");
			
			if(isLegal(hostname, port)){
				NodeInfo slave = new NodeInfo(hostname,port);
				masterConext.getSlaveMap().put(slave, System.currentTimeMillis());
				clusterInfo.setActiveMater(masterConext.getActiveMaster());
				return Response.ok(clusterInfo);
			}
		}
		return Response.error("hostname or port is illegal");
	}
	
	private boolean isLegal(String hostname, Integer port) {
		String hostPorts = ConfigurationContext.getValue("schedule.slaves");
		String[] arr =hostPorts.split(",");
		boolean islegal = false;
		for(String hp:arr){
			if(StringUtils.equals(hp.trim(), hostname+":"+port)){
				islegal = true;
			}
		}
		return islegal;
	}

}
