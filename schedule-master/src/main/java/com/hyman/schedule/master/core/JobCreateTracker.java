package com.hyman.schedule.master.core;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.common.enums.Cycle;
import com.hyman.schedule.common.util.DateUtil;
import com.hyman.schedule.common.util.ScheduleUtil;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.service.JobService;
import com.hyman.schedule.master.service.TaskService;

@Component("jobCreateTracker")
public class JobCreateTracker implements Runnable {

	static final Logger LOG = LoggerFactory.getLogger(JobCreateTracker.class);
	@Autowired TaskService taskService;
	@Autowired JobService jobService;
	private int currentOffset = 0;
	private static final int BATCH_SIZE=10;
	@Override
	public void run() {
		while(true){
			Page<Task> page = taskService.findPage(currentOffset, BATCH_SIZE);
			currentOffset = page.getNextIndex();
			for(Task t:page.getItems()){
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MINUTE, 0);
				Date currentTime = cal.getTime();
				try{
					if(isMathCronExpress(t,currentTime)){
						jobService.saveJobIfNotExist(t, currentTime);
					}
				}
				catch(Exception e){
					LOG.error("convert task to job error taskid is :{} ,and the schedule time is :{}",t.getId(),DateUtil.format(currentTime, "yyyyMMddHH"));
				}
			}
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isMathCronExpress(Task t,Date currentTime){
		String cronExpress = t.getCrontab();
		boolean ret = false;
		if(t.getCycle() == Cycle.HOURLY){
			ret = true;
		}
		else if(t.getCycle() == Cycle.DAILY){
			ret = ScheduleUtil.isMatch(cronExpress, currentTime);
		}
		else if(t.getCycle() == Cycle.WEEKLY){
			ret = ScheduleUtil.isMatch(cronExpress, currentTime);
		}
		else if(t.getCycle() == Cycle.MONTHLY){
			ret = ScheduleUtil.isMatch(cronExpress, currentTime);
		}
		return ret;
	}
}
