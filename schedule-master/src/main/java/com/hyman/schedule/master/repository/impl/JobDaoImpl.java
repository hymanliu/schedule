package com.hyman.schedule.master.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.repository.JobDao;

@Repository
public class JobDaoImpl extends BaseDaoImpl<Job, String> implements JobDao {
	static final Logger LOG = LoggerFactory.getLogger(JobDaoImpl.class);
}
