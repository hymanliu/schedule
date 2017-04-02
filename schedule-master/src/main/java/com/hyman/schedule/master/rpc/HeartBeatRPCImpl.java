package com.hyman.schedule.master.rpc;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.SlaveInfo;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

@Component("heartBeatRPCImpl")
public class HeartBeatRPCImpl implements HeartBeatRPC {

	@Override
	public Response<SlaveInfo> doBeat(String hostname,Integer port) {
		
		Response<SlaveInfo> ret = null;
		
		if(StringUtils.isBlank(hostname) || port==null){
			ret = Response.error("hostname or port is null");
		}
		else{
			ret = Response.ok(new SlaveInfo(hostname,port));
		}
		return ret;
	}

}
