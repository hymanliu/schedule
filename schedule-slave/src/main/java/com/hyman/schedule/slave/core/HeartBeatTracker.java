package com.hyman.schedule.slave.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.ClusterInfo;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.config.ConfigurationContext;

@Component("heartBeatTracker")
public class HeartBeatTracker implements Runnable {

	SlaveContext slaveContext = SlaveContext.getInstance();
	
	private boolean isActive = true;
	static final Logger LOG = LoggerFactory.getLogger(HeartBeatTracker.class);
	
    @Value("#{configProperties['slave.host']}")
    private String host;
    @Value("#{configProperties['slave.port']}")
    private String port;

	@Override
	public void run() {
		while(isActive){
			try{
				//TODO HeartBeatRPC 缺陷, 当activeMaster掉线了 无法切换
				Response<ClusterInfo> resp = slaveContext.getHeartBeatRPC().doBeat(host, Integer.parseInt(port));
				if(resp.isSuccess()){
					ClusterInfo data = resp.getData();
					NodeInfo nodeInfo = data.getActiveMater();
					
					//主节点切换
					if(nodeInfo!=null && !nodeInfo.equals(slaveContext.getActiveMaster())){
						slaveContext.setActiveMaster(nodeInfo);
					}
				}
				
			}
			catch(Exception e){
				LOG.error("do the heart beat error");
			}
			finally{
				try {
					Long heatBeatInterval = Long.parseLong(ConfigurationContext.getValue("schedule.heatbeat.interval"));
					Thread.sleep(heatBeatInterval);
				} catch (InterruptedException e) {
					LOG.error("thread interrupt error",e);
				}
			}
		}
	}

}
