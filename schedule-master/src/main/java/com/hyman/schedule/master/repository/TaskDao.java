package com.hyman.schedule.master.repository;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Task;

public interface TaskDao extends GenericDAO<Task, Integer>{

	Page<Task> findPage(int offset, int limit);

	List<Task> findPreTask(int id);

	List<Task> findSubTask(int id);


}
