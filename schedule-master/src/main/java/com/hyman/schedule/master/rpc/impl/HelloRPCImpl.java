package com.hyman.schedule.master.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.master.rpc.HelloRPC;
import com.hyman.schedule.master.service.TestService;


@Component("helloRPCImpl")
public class HelloRPCImpl implements HelloRPC {

	@Autowired TestService testService;
	
	@Override
	public String say(String name) {
		return testService.hellworld()+":"+name;
	}
}
