package com.hyman.schedule.master.repository;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.master.entity.TaskRelation;
import com.hyman.schedule.master.entity.TaskRelation.PK;

public class TaskRelationRepotoryTest extends DaoBaseJunitTest {
	
	@Autowired TaskRelationDao relationDao;
	
	@Test
	public void testSave(){
		TaskRelation o = new TaskRelation();
		o.setPk(new PK(2,1));
		o.setCreateTime(new Date());
		relationDao.save(o);
		relationDao.flush();
	}
	

}
