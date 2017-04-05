package com.hyman.schedule.master.rpc;

import org.springframework.stereotype.Component;

import com.hyman.schedule.master.rpc.MasterClientRPC;


@Component("masterClientRPCImpl")
public class MasterClientRPCImpl implements MasterClientRPC {

	@Override
	public String say(String name) {
		return null;
	}
}
