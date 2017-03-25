package com.hyman.schedule.master.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
	
	
	/*	
	SELECT * FROM t_job t1 LEFT JOIN t_job_relation t2 ON t1.id = t2.job_id WHERE (t1.state='INIT' OR 
	(t1.state='FAILED' AND t1.tries<4)) AND ( t2.pre_job_id IS NULL OR 
	t2.pre_job_id NOT IN (
	SELECT DISTINCT t3.id FROM t_job t3 LEFT JOIN t_job_relation t4 ON t3.id = t4.pre_job_id WHERE t3.state<>'SUCCESS'
	))
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Job> findAvailableWaitingJob(int maxtries,int limit){
		String sql = "SELECT * FROM t_job t1 LEFT JOIN t_job_relation t2 ON t1.id = t2.job_id WHERE (t1.state='INIT' OR "+
				"(t1.state='FAILED' AND t1.tries<:maxtries)) AND ( t2.pre_job_id IS NULL OR t2.pre_job_id NOT IN "
				+ "(SELECT DISTINCT t3.id FROM t_job t3 LEFT JOIN t_job_relation t4 ON t3.id = t4.pre_job_id WHERE t3.state<>'SUCCESS'))";
		
		Query query = this.getSession().createSQLQuery(sql)
				.addEntity(Job.class)
				.setInteger("maxtries", maxtries)
				.setMaxResults(limit);
		return query.list();
	}


	
}
