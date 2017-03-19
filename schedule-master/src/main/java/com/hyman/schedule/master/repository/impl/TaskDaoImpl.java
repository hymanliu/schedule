package com.hyman.schedule.master.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.repository.TaskDao;

@Repository
public class TaskDaoImpl extends BaseDaoImpl<Task, Integer> implements TaskDao {
	static final Logger LOG = LoggerFactory.getLogger(TaskDaoImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<Task> findPage(int offset,int limit){
		StringBuffer whereCondition = new StringBuffer(" WHERE isUse=true");
		String countHql = "SELECT count(*) FROM Task t"+whereCondition.toString();
		String contentHql = "FROM Task t"+whereCondition.toString() +" ORDER BY t.createTime";
		Query countQuery=this.getSession().createQuery(countHql);
		Query query=this.getSession().createQuery(contentHql);
		Object totalCount = countQuery.uniqueResult();
		int total = Integer.parseInt(totalCount.toString());
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		List<Task> items = query.list();
		return new Page(items, total, limit,offset);
	}
}
