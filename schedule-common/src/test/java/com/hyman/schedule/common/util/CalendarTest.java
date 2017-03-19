package com.hyman.schedule.common.util;

import java.util.Calendar;

import org.junit.Test;

public class CalendarTest {

	@Test
	public void testWeek(){
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		System.out.println(DateUtil.format(cal.getTime(), "yyyyMMdd00"));
	}
	
	@Test
	public void testIntToHex(){
		System.out.println(Integer.MAX_VALUE);
		
		String hex = Integer.toHexString(Integer.MAX_VALUE);
		System.out.println(hex);
		
		int val = Integer.parseInt("0000000F", 16);
		System.out.println(val);
	}
}
