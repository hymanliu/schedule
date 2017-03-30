package com.hyman.schedule.common.bean;

import java.util.List;

public class JobInfo {

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
