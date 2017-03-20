package com.hyman.schedule.master.service.impl;

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
}
