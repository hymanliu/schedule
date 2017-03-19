package com.hyman.schedule.master.repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Task;

public interface TaskDao extends GenericDAO<Task, Integer>{

	Page<Task> findPage(int offset, int limit);


}
