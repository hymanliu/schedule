package com.hyman.schedule.master.repository;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Job;

public interface JobDao extends GenericDAO<Job, String>{

	List<Job> findPreJob(String jobId);

	Page<Job> findWaitingPage(int offset, int limit);

}
