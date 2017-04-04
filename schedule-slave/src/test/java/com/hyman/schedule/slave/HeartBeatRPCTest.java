package com.hyman.schedule.slave;

import javax.annotation.Resource;

import org.junit.Test;

import com.hyman.schedule.common.bean.ClusterInfo;
import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

public class HeartBeatRPCTest extends BaseJunitTest {

	@Resource HeartBeatRPC heartBeatRPC;
	
	@Test
	public void testDoBeat(){
		Response<ClusterInfo> ret = heartBeatRPC.doBeat("localhost", 5577);
		System.out.println(ret.getMsg()+"\t"+ret.getData().getActiveMater().getHostname());
	}
}
