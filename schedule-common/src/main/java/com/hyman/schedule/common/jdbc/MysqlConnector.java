package com.hyman.schedule.common.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlConnector extends Connector {
	
	private static Logger LOG = LoggerFactory.getLogger(MysqlConnector.class);
	
	public MysqlConnector(String url, String user, String password) {
        super(url,user,password);
    }
    public void connect() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.doConnect();
        }catch (Exception e){
        	LOG.error("connect JDBC error",e);
        	throw new RuntimeException("connect JDBC error",e);
        }
    }


}
