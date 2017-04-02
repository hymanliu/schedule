package com.hyman.schedule.master.repository;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.master.entity.Job;

public interface JobDao extends GenericDAO<Job, String>{

	/**
	 * 获取父任务实例
	 * @param jobId
	 * @return
	 */
	List<Job> findPreJob(String jobId);

	/**
	 * 查询可以加入就绪队列的
	 * @param maxtries
	 * @param limit
	 * @return
	 */
	List<Job> findAvailableWaitingJob(int maxtries, int limit);

	/**
	 * 获取执行中超时的任务实例
	 * @param maxMin
	 * @param limit
	 * @return
	 */
	List<Job> findOverTimeJob(int maxMin, int limit);

	List<Job> listJob(int limit,JobState... states);

	
}
