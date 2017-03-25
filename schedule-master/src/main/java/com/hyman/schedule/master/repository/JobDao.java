package com.hyman.schedule.master.repository;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Job;

public interface JobDao extends GenericDAO<Job, String>{

	List<Job> findPreJob(String jobId);

	@Deprecated
	Page<Job> findWaitingPage(int offset, int limit);

	/**
	 * 查询可以加入就绪队列的
	 * @param maxtries
	 * @param limit
	 * @return
	 */
	List<Job> findAvailableWaitingJob(int maxtries, int limit);

}
