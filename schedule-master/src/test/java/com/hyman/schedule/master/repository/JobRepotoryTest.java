package com.hyman.schedule.master.repository;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.master.entity.Job;

public class JobRepotoryTest extends DaoBaseJunitTest {
	
	@Autowired JobDao jobDao;
	
	@Test
	public void testSave(){
		Job o = new Job();
		o.setId(Job.toJobId(1, "2017031900"));
		o.setState(JobState.INIT);
		jobDao.save(o);
		jobDao.flush();
	}
	

	@Test
	public void testFindPreJob(){
		List<Job> list = jobDao.findPreJob("000000032017032023");
		for(Job j :list){
			System.out.println(j.getId());
		}
	}
	
	@Test
	public void testFindWaitingPage(){
		Page<Job> page = jobDao.findWaitingPage(1, 2);
		
		for(Job j : page.getItems()){
			
			System.out.println(j.getId());
		}
	}
	
	@Test
	public void testFindAvailableWaitingJob(){
		List<Job> list = jobDao.findAvailableWaitingJob(4, 10);
		
		for(Job j : list){
			System.out.println(j.getId());
		}
	}
}
