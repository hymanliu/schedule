package com.hyman.schedule.common.bean;

import java.io.Serializable;

public class Response<T> implements Serializable {
	
	private static final long serialVersionUID = 9028163396962707842L;
	private boolean success;
	private String msg;
	private T data;
	
	public Response(){}
	public Response(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}
	public Response(boolean success,String msg,T data){
		this.success = success;
		this.msg = msg;
		this.data = data;
	}
	
	public static<T> Response<T> ok(T o){
		return new Response<T>(true,"OK",o);
	}
	
	public static<T> Response<T> error(String msg){
		return new Response<T>(false,msg);
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
