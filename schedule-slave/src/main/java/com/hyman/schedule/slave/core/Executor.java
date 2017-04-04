package com.hyman.schedule.slave.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyman.schedule.common.bean.JobInfo;
import com.hyman.schedule.common.enums.SysType;
import com.hyman.schedule.common.util.SysUtil;

public class Executor extends Thread {

	static final Logger LOG = LoggerFactory.getLogger(Executor.class);
	
	private JobInfo jobInfo;
	
	public Executor(JobInfo jobInfo) {
		super();
		this.jobInfo = jobInfo;
	}

	protected int exec(){
		int exitCode = 1;
		String[] args = jobInfo.getParams() == null? null : jobInfo.getParams().toArray(new String[0]);
		String[] cmd = toScriptParams(jobInfo.getCommand(), args);
		ProcessBuilder builder = new ProcessBuilder(cmd).redirectErrorStream(true);
		try {
			Process proc = builder.start();
			 // 注意下面的操作 
			 String msg;
			 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), Charset.forName("GBK")));
			 while ( (msg=bufferedReader.readLine()) != null){
				 System.out.println(msg);
			 }
			 bufferedReader.close();
			 //阻塞，直到上述命令执行完
			 exitCode = proc.waitFor(); 
			 proc.destroy();
			 
		} catch (Exception e) {
			LOG.error("exec command error command :{} args:{}",jobInfo.getCommand(),args);
			LOG.error("exec command error",e);
			exitCode = 1;
		}
		return exitCode;
	}

	private String[] toScriptParams(String command, String... args) {
		List<String> list = new ArrayList<>();
		if(SysUtil.getSystemType() == SysType.WINDOWS){
			list.add("cmd");
			list.add("/c");
		}else if(SysUtil.getSystemType() == SysType.LINUX){
			list.add("/bin/bash");
			list.add("-c");
		}else{
			throw new RuntimeException("shell is not unsupported for the System :"+SysUtil.getSystemType());
		}
		list.add(command);
		if(args!=null)
		for(String arg : args){
			if(args!=null){
				list.add(arg);
			}
		}
		String[] cmd = list.toArray(new String[]{});
		return cmd;
	}
}
