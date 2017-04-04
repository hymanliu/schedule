package com.hyman.schedule.common.util;

import org.junit.Test;

import com.hyman.schedule.common.config.ConfigurationContext;

public class ConfigurationContextTest {

	@Test
	public void testLoadConfig(){
		
		String val = ConfigurationContext.getValue("schedule.slaves");
		System.out.println(val);
	}
}
