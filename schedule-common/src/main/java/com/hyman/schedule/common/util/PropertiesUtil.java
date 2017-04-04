package com.hyman.schedule.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertiesUtil {
	
	private static final String SUFFIX=".properties";
	private static final String DEFAULT="-default";
	private static final String SITE="-site";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static Properties loadConfigraton(){
		return loadConfigraton("application.properties");
	}
	
	public static Properties loadConfigraton(String fileName){
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(in);
		} catch (Exception e) {
			LOGGER.error("load properties error: filename={}",fileName);
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	
	public static Properties loadByPrefixName(String prefixName){
		Properties properties = new Properties();
		loadByPrefixName(properties,prefixName,DEFAULT);
		loadByPrefixName(properties,prefixName,"");
		loadByPrefixName(properties,prefixName,SITE);
		return properties;
	}
	
	private static void loadByPrefixName(Properties properties ,String prefixName,String label){
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(prefixName+label+SUFFIX);
		if(in == null) return ;
		try {
			properties.load(in);
		} catch (Exception e) {
			LOGGER.error("load properties error: filename={}{}{}",prefixName,label,SUFFIX);
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
