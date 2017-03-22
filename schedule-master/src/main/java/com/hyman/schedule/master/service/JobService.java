package com.hyman.schedule.master.service;


import java.util.Date;
import java.util.List;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.entity.Task;

public interface JobService {

	Page<Job> findPage(Job job, int offset, int limit);

	boolean isExist(int taskId, String scheduleHour);
	
	boolean isExist(String id);

	void save(Job o);

	void saveJobIfNotExist(Task t, Date scheduleTime);
	
	List<Job> findPreJob(String jobId);

}
