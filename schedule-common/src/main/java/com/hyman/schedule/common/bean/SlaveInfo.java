package com.hyman.schedule.common.bean;

import java.io.Serializable;

public class SlaveInfo implements Serializable {

	private static final long serialVersionUID = 3252808212252842945L;
	private String hostname;
	private int port;
	
	public SlaveInfo(){}
	
	public SlaveInfo(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
