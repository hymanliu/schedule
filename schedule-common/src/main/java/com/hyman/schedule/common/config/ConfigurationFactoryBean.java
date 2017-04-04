package com.hyman.schedule.common.config;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;

/**
 * Load the props from mysql config table.
 * @author HymanLiu<zhiquanliu@foxmail.com>
 */
public class ConfigurationFactoryBean extends PropertiesFactoryBean {
	
	static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactoryBean.class);

	@Override
	protected Properties mergeProperties() throws IOException {
		Properties props = super.mergeProperties();
		props.putAll(ConfigurationContext.getProps());
		return props;
	}	
	
}
