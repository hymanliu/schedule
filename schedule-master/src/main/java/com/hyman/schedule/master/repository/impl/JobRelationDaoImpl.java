package com.hyman.schedule.master.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hyman.schedule.master.entity.JobRelation;
import com.hyman.schedule.master.entity.JobRelation.PK;
import com.hyman.schedule.master.repository.JobRelationDao;

@Repository
public class JobRelationDaoImpl extends BaseDaoImpl<JobRelation, PK> implements JobRelationDao {
	static final Logger LOG = LoggerFactory.getLogger(JobRelationDaoImpl.class);
	
	
}
