package com.hyman.schedule.master.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.common.util.ScheduleUtil;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.entity.JobRelation;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.repository.JobDao;
import com.hyman.schedule.master.repository.JobRelationDao;
import com.hyman.schedule.master.repository.TaskDao;
import com.hyman.schedule.master.service.JobService;

@Service
@Transactional
public class JobServiceImpl implements JobService {

	@Autowired JobDao jobDao;
	@Autowired JobRelationDao jobRelationDao;
	@Autowired TaskDao taskDao;
	
	@Override
	public Page<Job> findPage(Job job, int offset,int limit){
		
		
		//TODO
		return null;
	}
	
	@Override
	public void save(Job o){
		jobDao.save(o);
	}
	
	@Override
	public boolean isExist(int taskId,String scheduleHour){
		return jobDao.find(Job.toJobId(taskId, scheduleHour))!=null;
	}
	
	@Override
	public boolean isExist(String jobId){
		return jobDao.find(jobId)!=null;
	}
	
	private void saveJobRelation(List<JobRelation> list){
		JobRelation[] arr = list.toArray(new JobRelation[]{});
		jobRelationDao.save(arr);
	}
	
	
	/**
	 * 检查是否存在对应的Job,不存在则添加
	 * @param taskId
	 * @param formattedTime
	 */
	@Override
	public void saveJobIfNotExist(Task t,Date scheduleTime){

		String formattedTime = ScheduleUtil.toCycleBeginTime(t.getCycle(), scheduleTime);
		String jobId = Job.toJobId(t.getId(), formattedTime);
		boolean isExist = isExist(jobId);
		if(!isExist) {
			Job o = new Job();
			o.setId(jobId);
			o.setTaskId(t.getId());
			o.setTries(0);
			o.setState(JobState.INIT);
			o.setCreateTime(new Date());
			jobDao.save(o);
			
			//检查是否存在父任务，如果存在   更新job_relation表
			List<Task> preList = taskDao.findPreTask(t.getId());
			if(preList!=null && !preList.isEmpty()){
				List<JobRelation> jobRelations = new ArrayList<>();
				for(Task pre : preList){
					String preCycleBeginTime = ScheduleUtil.toCycleBeginTime(pre.getCycle(), t.getCycle(), scheduleTime);
					String preJobId = Job.toJobId(pre.getId(), preCycleBeginTime);
					Date date = new Date();
					jobRelations.add(new JobRelation(jobId,preJobId,date,date));
				}
				this.saveJobRelation(jobRelations);
			}
			
		}
	}

	@Override
	public List<Job> findPreJob(String jobId) {
		return jobDao.findPreJob(jobId);
	}

	@Override
	public List<Job> findAvailableWaitingJob(int maxtries, int limit) {
		return jobDao.findAvailableWaitingJob(maxtries, limit);
	}

	@Override
	public void save(List<Job> list) {
		if(list==null || list.isEmpty()) return;
		Job[] entities = list.toArray(new Job[]{});
		jobDao.save(entities);
	}
	
	@Override
	public List<Job> findOverTimeJob(int maxMin,int limit){
		return jobDao.findOverTimeJob(maxMin, limit);
	}
	
	@Override
	public List<Job> listJob(int limit,JobState... states){
		return jobDao.listJob(limit, states);
	}
}
