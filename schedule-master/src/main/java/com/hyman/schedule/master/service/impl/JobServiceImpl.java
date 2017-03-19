package com.hyman.schedule.master.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.repository.JobDao;
import com.hyman.schedule.master.service.JobService;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	@Autowired JobDao jobDao;
	
	@Override
	public Page<Job> findPage(int offset,int limit){
		return null;
	}
	
	@Override
	public void save(Job o){
		jobDao.save(o);
	}
	
	@Override
	public boolean isExist(int taskId,String scheduleHour){
		return jobDao.find(Job.toJobId(taskId, scheduleHour))!=null;
	}
	
	@Override
	public boolean isExist(String jobId){
		return jobDao.find(jobId)!=null;
	}
}
