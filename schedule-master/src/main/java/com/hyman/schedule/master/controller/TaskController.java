package com.hyman.schedule.master.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.common.bean.Response;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.service.JobService;
import com.hyman.schedule.master.service.TaskService;

public class TaskController {

private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired TaskService taskService;
	@Autowired JobService jobService;
	
	
	/**
	 * 
	 * @param taskId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Response<Object> add(int taskId,Date startTime,Date endTime){
		Response<Object> ret = new Response<Object>(true,"OK");;
		try {
			Task task = taskService.find(taskId);
			List<Date> cycleStartTimeList = taskService.listCycleStartTime(task, startTime, endTime);
			for(Date date : cycleStartTimeList){
				jobService.saveJobIfNotExist(task, date);
			}
		} catch (Exception e) {
			LOG.error("任务补录失败: taskId={},startTime={},endTime={}",taskId,startTime,endTime);
			LOG.error("任务补录失败:",e);
			ret = new Response<Object>(false,e.getMessage());
		}
		return ret;
	}
	
}
