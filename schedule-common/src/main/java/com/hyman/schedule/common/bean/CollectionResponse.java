package com.hyman.schedule.common.bean;

import java.util.Collection;

public class CollectionResponse<T> {
	private boolean success;
	private String msg;
	private Collection<T> data;
	
	public CollectionResponse(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}
	public CollectionResponse(boolean success,String msg,Collection<T> data){
		this.success = success;
		this.msg = msg;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Collection<T> getData() {
		return data;
	}
	public void setData(Collection<T> data) {
		this.data = data;
	}
}
