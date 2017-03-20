package com.hyman.schedule.master.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hyman.schedule.master.entity.TaskRelation;
import com.hyman.schedule.master.entity.TaskRelation.PK;
import com.hyman.schedule.master.repository.TaskRelationDao;

@Repository
public class TaskRelationDaoImpl extends BaseDaoImpl<TaskRelation, PK> implements TaskRelationDao {
	static final Logger LOG = LoggerFactory.getLogger(TaskRelationDaoImpl.class);
	
	
}
