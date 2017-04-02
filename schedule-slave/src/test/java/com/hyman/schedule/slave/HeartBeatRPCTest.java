package com.hyman.schedule.slave;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.common.bean.SlaveInfo;
import com.hyman.schedule.master.rpc.HeartBeatRPC;

public class HeartBeatRPCTest extends BaseJunitTest {

	@Autowired HeartBeatRPC heartBeatRPC;
	
	@Test
	public void testDoBeat(){
		Response<SlaveInfo> ret = heartBeatRPC.doBeat("localhost", 5577);
		System.out.println(ret.getMsg()+"\t"+ret.getData().getHostname()+"\t"+ret.getData().getPort());
	}
}
