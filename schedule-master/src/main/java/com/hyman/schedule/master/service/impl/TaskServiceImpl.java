package com.hyman.schedule.master.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.repository.TaskDao;
import com.hyman.schedule.master.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired TaskDao taskDao;
	
	@Override
	public Page<Task> findPage(int offset,int limit){
		return taskDao.findPage(offset, limit);
	}
	
	@Override
	public List<Task> findPreTask(int id){
		return taskDao.findPreTask(id);
	}
	
	@Override
	public List<Task> findSubTask(int id){
		return taskDao.findSubTask(id);
	}

	@Override
	public Task find(int taskId) {
		return taskDao.find(taskId);
	}
	
	@Override
	public List<Date> listCycleStartTime(Task task, Date startTime,Date endTime){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		List<Date> timeList = new ArrayList<>(); 
		while(cal.getTimeInMillis() <= endTime.getTime()){
			switch(task.getCycle()){
			case HOURLY:
				timeList.add(cal.getTime());
				cal.add(Calendar.HOUR_OF_DAY, 1);
				break;
			case DAILY:
				cal.set(Calendar.HOUR_OF_DAY, 0);
				timeList.add(cal.getTime());
				cal.add(Calendar.DAY_OF_YEAR, 1);
				break;
			case WEEKLY:
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.DAY_OF_WEEK, 1);
				timeList.add(cal.getTime());
				cal.add(Calendar.WEEK_OF_YEAR, 1);
				break;
			case MONTHLY:
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				timeList.add(cal.getTime());
				cal.add(Calendar.MONTH, 1);
				break;
			}
		}
		return timeList;
	}
}
