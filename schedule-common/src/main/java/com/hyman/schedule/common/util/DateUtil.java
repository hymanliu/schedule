package com.hyman.schedule.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String format(Date date ,String parten){
		DateFormat df = new SimpleDateFormat(parten);
		return df.format(date);
	}
	
	public static Date parse(String date ,String parten){
		DateFormat df = new SimpleDateFormat(parten);
		Date ret = null;
		try {
			ret = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
