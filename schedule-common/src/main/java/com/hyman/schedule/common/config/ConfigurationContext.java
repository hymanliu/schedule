package com.hyman.schedule.common.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyman.schedule.common.jdbc.Connector;
import com.hyman.schedule.common.jdbc.MysqlConnector;
import com.hyman.schedule.common.util.DateUtil;
import com.hyman.schedule.common.util.PropertiesUtil;


public class ConfigurationContext implements Runnable {
	
	static final Logger LOG = LoggerFactory.getLogger(ConfigurationContext.class);
	private static final String CONF_FILE_PREFIX = "config";
	private static final String TABLE_NAME = "t_config";
	private static ConfigurationContext instance = null;
	
	private Properties props;
	private String url;
	private String username;
	private String password;
	private String project;
	
	private ConfigurationContext(){
		props = PropertiesUtil.loadByPrefixName(CONF_FILE_PREFIX);
		this.url=props.getProperty("config.jdbc.url","").trim();
		this.username=props.getProperty("config.jdbc.username","").trim();
		this.password=props.getProperty("config.jdbc.password","").trim();
		this.project=props.getProperty("config.project","").trim();
		
		if(StringUtils.isBlank(this.url) || 
				StringUtils.isBlank(this.username) ||
				StringUtils.isBlank(this.project)){
			LOG.warn("not load the database configurations");
		}else{
			loadConfig(null);
		}
		//load the configurations from the database every 5 minutes
		new Thread(this).start();
	}
	
	public static synchronized ConfigurationContext getInstance(){
		if(instance == null){
			synchronized(ConfigurationContext.class) {
				instance = new ConfigurationContext();
			}
		}
		return instance;
	}
	
	private synchronized void loadConfig(Date date) {
		Map<String,String> configMap = new HashMap<>();
		Connector connector = null;
		
		try{
			LOG.info("config.jdbc.url is: {}",url);
			LOG.info("config.jdbc.username is: {}",username);
			LOG.info("config.jdbc.password is: {}",password);
			connector = new MysqlConnector(url, username, password);
		}
		catch(Exception e){
			throw new RuntimeException("can't connect the config database server :",e);
		}
		String updateTimeCondition = date == null ? "" :" and modify_time >='"+ DateUtil.format(date, "yyyy-MM-dd HH:mm:ss")+"'";
		String projectCondition = StringUtils.isBlank(project) ? "" : " and project='"+project+"'";
		String sql ="select prop_key,prop_value from "+TABLE_NAME+" where flag=1"+updateTimeCondition + projectCondition;
		
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rst = null;
		try{
			conn = connector.getConn();
			stm = conn.prepareStatement(sql);
			rst=stm.executeQuery();
			while (rst.next()) {
	            String prop_key = rst.getString(1);
	            String prop_value = rst.getString(2);
	            LOG.info(prop_key+"\t"+prop_value);
	            configMap.put(prop_key.trim(), prop_value==null?null:prop_value.trim());
			}
			props.putAll(configMap);
		}
		catch(Exception e){
			LOG.error("load configurations error",e);
			throw new RuntimeException("load configurations error",e);
		}
		finally{
			try {
				if(rst!=null){
					rst.close();
				}
			} catch (SQLException e) {
			}
			try {
				if(stm!=null){
					stm.close();
				}
			} catch (SQLException e) {
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	
	public static Properties getProps(){
		return getInstance().props;
	}
	
	public static String getValue(String key){
		return getInstance().props.getProperty(key);
	}
	
	public static String getValue(String key,String defaultValue){
		return getInstance().props.getProperty(key,defaultValue);
	}

	@Override
	public void run() {
		while(true){
			try{
				long duration = Long.parseLong(getValue("config.refresh.interval", "300000"));
				Thread.sleep(duration);
				LOG.info("load the configurations for {} .", project);
				//加载十分钟之前修改的配置文件
				loadConfig(new Date(System.currentTimeMillis()-duration*2));
			}
			catch(Exception e){
				LOG.error("refresh the configurations error",e);
			}
		}
	}
	
}
