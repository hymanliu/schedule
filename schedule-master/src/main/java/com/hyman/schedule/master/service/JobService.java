package com.hyman.schedule.master.service;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Job;

public interface JobService {

	Page<Job> findPage(int offset, int limit);

	boolean isExist(int taskId, String scheduleHour);
	
	boolean isExist(String id);

	void save(Job o);

}
