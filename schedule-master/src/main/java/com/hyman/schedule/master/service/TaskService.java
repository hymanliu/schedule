package com.hyman.schedule.master.service;

import java.util.List;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Task;

public interface TaskService {

	Page<Task> findPage(int offset, int limit);

	List<Task> findPreTask(int id);

	List<Task> findSubTask(int id);

}
