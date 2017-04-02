package com.hyman.schedule.master;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.hyman.schedule.master.entity.Job;

public class MasterConext {

	private MasterConext(){}
	
	private ConcurrentLinkedQueue<Job> queue;
	
	private static MasterConext instance;
	
	public static MasterConext getInstance(){
		if(instance==null){
			synchronized(MasterConext.class){
				instance = new MasterConext();
			}
		}
		return instance;
	}
}
