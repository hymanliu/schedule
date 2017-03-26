package com.hyman.schedule.master.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.common.util.DateUtil;
import com.hyman.schedule.master.BaseJunitTest;
import com.hyman.schedule.master.entity.Task;

public class TaskServiceTest extends BaseJunitTest {

	@Autowired TaskService taskService;
	
	@Test
	public void testListCycleStartTime(){
		Task task = taskService.find(4);
		System.out.println(task.getName());
		
		Date startTime = DateUtil.parse("2016-11-27 12:16:00", "yyyy-MM-dd HH:mm:ss");
		Date endTime = DateUtil.parse("2017-03-26 12:08:12", "yyyy-MM-dd HH:mm:ss");
		List<Date> list = taskService.listCycleStartTime(task, startTime, endTime);
		for(Date date : list){
			System.out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
		}
	}
}
