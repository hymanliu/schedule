package com.hyman.schedule.slave.core;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.ClusterInfo;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

@Component("heartBeatTracker")
public class HeartBeatTracker implements Runnable {

	@Resource HeartBeatRPC heartBeatRPC;
	@Autowired SlaveContext slaveContext;
	
	private boolean isActive = true;
	static final long  HEART_BEAT_DURATION=5*1000L;
	static final Logger LOG = LoggerFactory.getLogger(HeartBeatTracker.class);
	
	@Override
	public void run() {
		while(isActive){
			try{
				Response<ClusterInfo> resp = heartBeatRPC.doBeat("localhost", 5577);
				if(resp.isSuccess()){
					ClusterInfo data = resp.getData();
					NodeInfo nodeInfo = data.getActiveMater();
					slaveContext.setActiveMaster(nodeInfo);
				}
				
			}
			catch(Exception e){
				LOG.error("do the heart beat error");
			}
			finally{
				try {
					Thread.sleep(HEART_BEAT_DURATION);
				} catch (InterruptedException e) {
					LOG.error("thread interrupt error",e);
				}
			}
		}
	}

}
