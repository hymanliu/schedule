package com.hyman.schedule.slave;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.master.rpc.HeartBeatRPC;

public class HeartBeatRPCTest extends BaseJunitTest {

	@Autowired HeartBeatRPC heartBeatRPC;
	
	@Test
	public void testIsAlive(){
		boolean ret = heartBeatRPC.isAlive("aa");
		System.out.println(ret);
	}
}
