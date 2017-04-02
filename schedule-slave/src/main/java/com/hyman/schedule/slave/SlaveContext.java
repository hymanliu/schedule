package com.hyman.schedule.slave;


public class SlaveContext {

private SlaveContext(){}
	
	private static SlaveContext instance;
	
	public static SlaveContext getInstance(){
		if(instance==null){
			synchronized(SlaveContext.class){
				instance = new SlaveContext();
			}
		}
		return instance;
	}
}
