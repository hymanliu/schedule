package com.hyman.schedule.slave.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.ServerInfo;
import com.hyman.schedule.common.config.ConfigurationContext;

@Component
public class HeartBeatTracker implements Runnable {
	
	static final Logger LOG = LoggerFactory.getLogger(HeartBeatTracker.class);

	@Autowired SlaveContext slaveContext;
	private boolean isActive = true;
	
	@Override
	public void run() {
		while(isActive){
			while(slaveContext.getHeartBeatRPC()!=null){
				try{
					NodeInfo nodeInfo = slaveContext.getNodeInfo();
					Response<ServerInfo> resp = slaveContext.getHeartBeatRPC().doBeat(nodeInfo.getHostname(), nodeInfo.getPort());
					if(resp==null || !resp.isSuccess()){
						LOG.error("do the heart beat error");
					}else{
						LOG.info("do heart beat successfully");
					}
				}
				catch(Exception e){
					LOG.error("do the heart beat error");
				}
				finally{
					try {
						Long heatBeatInterval = Long.parseLong(ConfigurationContext.getValue("schedule.heartbeat.interval"));
						Thread.sleep(heatBeatInterval);
					} catch (InterruptedException e) {
						LOG.error("thread interrupt error",e);
					}
				}
			}
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
