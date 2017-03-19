package com.hyman.schedule.master.repository;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.common.enums.Cycle;
import com.hyman.schedule.master.entity.Task;

public class TaskRepotoryTest extends DaoBaseJunitTest {
	
	@Autowired TaskDao taskDao;
	
	@Test
	public void testSave(){
		
		for(int i =1;i<10;i++){
			Task o = new Task();
			o.setName("task"+i);
			o.setCreateTime(new Date());
			o.setIsUse(true);
			o.setCrontab("0 0 0/1 * * ?");
			o.setCycle(Cycle.HOURLY);
			taskDao.save(o);
			taskDao.flush();
		}
		
	}
	
	@Test
	public void testFindPage(){
		Page<Task> page = taskDao.findPage(0, 2);
		for(Task t : page.getItems()){
			System.out.println(t.getName());
		}
	}
	
	@Test
	public void testFindParent(){
		List<Task> list = taskDao.findPreTask(1);
		
		for(Task t : list){
			System.out.println(t.getName());
		}
		
	}

}
