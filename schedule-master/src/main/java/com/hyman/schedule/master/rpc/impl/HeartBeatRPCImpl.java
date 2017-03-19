package com.hyman.schedule.master.rpc.impl;

import org.springframework.stereotype.Component;

import com.hyman.schedule.master.rpc.HeartBeatRPC;

@Component("heartBeatRPCImpl")
public class HeartBeatRPCImpl implements HeartBeatRPC {

	@Override
	public boolean isAlive(String name) {
		return true;
	}

}
