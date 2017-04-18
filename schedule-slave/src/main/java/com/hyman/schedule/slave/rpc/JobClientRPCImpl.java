package com.hyman.schedule.slave.rpc;

import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.JobInfo;

@Component("jobClientRPCImpl")
public class JobClientRPCImpl implements JobClientRPC {

	@Override
	public boolean distribute(JobInfo jobInfo) {
		
		System.out.println(jobInfo);
		
		return false;
	}

}
