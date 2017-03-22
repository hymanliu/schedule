package com.hyman.schedule.master.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.repository.JobDao;

@Repository
public class JobDaoImpl extends BaseDaoImpl<Job, String> implements JobDao {
	static final Logger LOG = LoggerFactory.getLogger(JobDaoImpl.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Job> findPreJob(String jobId){
		String hql ="select t1 FROM Job t1,JobRelation t2 where t1.id=t2.pk.preJobId and t2.pk.jobId=:jobId";
		Query query=getSession().createQuery(hql).setString("jobId", jobId);
		return query.list();
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<Job> findWaitingPage(int offset, int limit){
		StringBuffer whereCondition = new StringBuffer(" WHERE t.state=:initState or (t.state=:failedState and t.tries<4)");
		String countHql = "SELECT count(*) FROM Job t"+whereCondition.toString();
		String contentHql = "FROM Job t"+whereCondition.toString() +" ORDER BY t.createTime,t.id";
		Query countQuery=this.getSession().createQuery(countHql)
				.setString("initState", JobState.INIT.name())
				.setString("failedState", JobState.FAILED.name());
		Query query=this.getSession().createQuery(contentHql)
				.setString("initState", JobState.INIT.name())
				.setString("failedState", JobState.FAILED.name());
		Object totalCount = countQuery.uniqueResult();
		int total = Integer.parseInt(totalCount.toString());
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		List<Task> items = query.list();
		return new Page(items, total, limit,offset);
		
	}
}
