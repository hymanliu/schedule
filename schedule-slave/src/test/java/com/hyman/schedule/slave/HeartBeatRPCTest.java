package com.hyman.schedule.slave;


import org.junit.Test;

import com.hyman.schedule.common.bean.ClusterInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.slave.core.SlaveContext;

public class HeartBeatRPCTest extends BaseJunitTest {

	@Test
	public void testDoBeat(){
		Response<ClusterInfo> ret = SlaveContext.getInstance().getHeartBeatRPC().doBeat("localhost", 5577);
		System.out.println(ret.getMsg()+"\t"+ret.getData().getActiveMater().getHostname());
	}
}
