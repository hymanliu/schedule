package com.hyman.schedule.master.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import com.hyman.schedule.common.enums.JobState;
import com.hyman.schedule.common.util.ScheduleUtil;

@Entity
@Table(name=Job.TABLE_NAME)
public class Job implements Serializable {

	private static final long serialVersionUID = -1305067901613352551L;
	protected final static String TABLE_NAME="t_job";

	@Id
	@Column(name="id",columnDefinition="varchar(18) not null comment '实例ID'")
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@GeneratedValue(generator = "idGenerator")
	private String id;
	@Column(name="state",columnDefinition="varchar(20) not null comment '实例状态'")
	@Enumerated(EnumType.STRING)
	private JobState state;
	@Column(name="create_time",columnDefinition=("datetime default null comment '创建时间'"))
	private Date createTime;
	@Column(name="begin_time",columnDefinition=("datetime default null comment '开始时间'"))
	private Date beginTime;
	@Column(name="end_time",columnDefinition=("datetime default null comment '执行时间'"))
	private Date endTime;
	@Column(name="tries",columnDefinition=("int(4) default 0 comment '尝试次数'"))
	private Integer tries;
	@Column(name="priority",columnDefinition=("int(4) default 0 comment '优先级'"))
	private Integer priority;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public JobState getState() {
		return state;
	}
	public void setState(JobState state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setId(int taskId,String cycleBeginTime){
		this.id = toJobId(taskId,cycleBeginTime);
	}
	public Integer getTries() {
		return tries;
	}
	public void setTries(Integer tries) {
		this.tries = tries;
	}
	public static String toJobId(int taskId,String cycleBeginTime){
		return ScheduleUtil.toFormattedHex(taskId)+cycleBeginTime;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}

