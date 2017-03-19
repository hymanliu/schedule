package com.hyman.schedule.master.service;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Task;

public interface TaskService {

	Page<Task> findPage(int offset, int limit);

}
