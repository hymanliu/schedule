package com.hyman.schedule.master.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
	

}
