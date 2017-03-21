package com.hyman.schedule.common.util;

import java.util.Date;

import org.junit.Test;

import com.hyman.schedule.common.enums.Cycle;

public class ScheduleUtilTest {

	@Test
	public void testHour(){
		
		Cycle preCycle = Cycle.HOURLY;
		Date subScheduleDate = DateUtil.parse("2017032123", "yyyyMMddHH");
		
		Cycle subCycle = Cycle.HOURLY;
		String val = ScheduleUtil.toCycleBeginTime(preCycle, subCycle, subScheduleDate);
		System.out.println(val);
		
		subCycle = Cycle.DAILY;
		val = ScheduleUtil.toCycleBeginTime(preCycle, subCycle, subScheduleDate);
		System.out.println(val);
		
		subCycle = Cycle.WEEKLY;
		val = ScheduleUtil.toCycleBeginTime(preCycle, subCycle, subScheduleDate);
		System.out.println(val);
		
		subCycle = Cycle.MONTHLY;
		val = ScheduleUtil.toCycleBeginTime(preCycle, subCycle, subScheduleDate);
		System.out.println(val);
		
	}
}
