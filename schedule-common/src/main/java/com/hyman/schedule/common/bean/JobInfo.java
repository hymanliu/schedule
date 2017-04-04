package com.hyman.schedule.common.bean;

import java.io.Serializable;
import java.util.List;


public class JobInfo implements Serializable{

	private static final long serialVersionUID = 6352352844364972432L;
	private String command;
	private List<String> params;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
}
