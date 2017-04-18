package com.hyman.schedule.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import com.hyman.schedule.common.enums.SysType;

public class SysUtil {
	static final Properties prop = System.getProperties();
	
	public static SysType getSystemType(){
		String os = prop.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")){
			return SysType.WINDOWS;
		}
		else if(os.toLowerCase().startsWith("linux")){
			return SysType.LINUX;
		}
		else{
			return SysType.MAC;
		}
	}
	
	public static String getSystemCharset(){
		return prop.getProperty("sun.jnu.encoding");
	}
	
	public static String getHostName(){
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			return inetAddress.getHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException("can't get the hostname");
		}
	}
}
