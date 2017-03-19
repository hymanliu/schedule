package com.hyman.schedule.master.service.impl;

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
}
