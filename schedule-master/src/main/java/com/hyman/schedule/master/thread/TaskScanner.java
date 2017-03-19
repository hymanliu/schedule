package com.hyman.schedule.master.thread;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyman.schedule.common.bean.Page;
import com.hyman.schedule.common.enums.Cycle;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.common.util.ScheduleUtil;
import com.hyman.schedule.master.entity.Job;
import com.hyman.schedule.master.entity.Task;
import com.hyman.schedule.master.service.JobService;
import com.hyman.schedule.master.service.TaskService;

@Component("taskScanner")
public class TaskScanner implements Runnable {

	@Autowired TaskService taskService;
	@Autowired JobService jobService;
	private int currentOffset = 0;
	private static final int BATCH_SIZE=2;
	@Override
	public void run() {
		while(true){
			Page<Task> page = taskService.findPage(currentOffset, BATCH_SIZE);
			currentOffset = page.getNextIndex();
			for(Task t:page.getItems()){
				System.out.println(t.getName());
				String cronExpress = t.getCrontab();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MINUTE, 0);
				Date currentTime = cal.getTime();
				String formattedTime = ScheduleUtil.toCycleBeginTime(t.getCycle(), currentTime);
				if(t.getCycle() == Cycle.HOURLY){
					//no need to check the cronExpress 
				}
				else if(t.getCycle() == Cycle.DAILY){
					boolean isMatch = ScheduleUtil.isMatch(cronExpress, currentTime);
					if(!isMatch) {continue;}
				}
				else if(t.getCycle() == Cycle.WEEKLY){
					boolean isMatch = ScheduleUtil.isMatch(cronExpress, currentTime);
					if(!isMatch) {continue;}
				}
				else if(t.getCycle() == Cycle.MONTHLY){
					boolean isMatch = ScheduleUtil.isMatch(cronExpress, currentTime);
					if(!isMatch) {continue;}
				}
				putIntoJobIfNotExist(t.getId(),formattedTime);
			}
			
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 检查是否存在对应的Job,不存在则添加
	 * @param taskId
	 * @param formattedTime
	 */
	public void putIntoJobIfNotExist(int taskId,String formattedTime){
		String jobId = Job.toJobId(taskId, formattedTime);
		boolean isExist = jobService.isExist(jobId);
		if(!isExist) {
			Job o = new Job();
			o.setId(jobId);
			o.setState(JobState.INIT);
			o.setCreateTime(new Date());
			jobService.save(o);
			
			//TODO 检查是否存在父任务，如果存在   更新job_relation表
		}
	}
	
}
