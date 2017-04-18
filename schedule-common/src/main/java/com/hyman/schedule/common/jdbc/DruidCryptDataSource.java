package com.hyman.schedule.common.jdbc;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.hyman.schedule.common.util.CryptTool;

public class DruidCryptDataSource extends DruidDataSource{
	
	private static final long serialVersionUID = 6590773089610827480L;
    static final Logger LOG = LoggerFactory.getLogger(DruidCryptDataSource.class);
    
    @Override
    public void setPassword(String password) {
        if(StringUtils.isNotBlank(password)){
            try{
                password = CryptTool.decryptAES(password);
            }catch(Exception e){
                LOG.warn("The password \"{}\" is not using the CryptTool encrypted.",password);
            }
        }
        super.setPassword(password);
    }
}
