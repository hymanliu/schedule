package com.hyman.schedule.master.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name=TaskRelation.TABLE_NAME)
public class TaskRelation implements Serializable {

	private static final long serialVersionUID = -2645071585542405998L;
	public static final String TABLE_NAME ="t_task_relation";
	
	@EmbeddedId
	private PK pk;
	@Column(name="create_time",columnDefinition=("datetime default null comment '创建时间'"),updatable=false)
	private Date createTime;
	@Column(name="modify_time",columnDefinition=("datetime default null comment '修改时间'"))
	private Date modifyTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public PK getPk() {
		return pk;
	}
	public void setPk(PK pk) {
		this.pk = pk;
	}

	public static class PK implements Serializable{

		private static final long serialVersionUID = 4999862365783207762L;
		@Column(name="task_id",columnDefinition="int(11) not null comment '任务ID'")
		private Integer taskId;
		@Column(name="parent_task_id",columnDefinition="int(11) not null comment '父任务ID'")
		private Integer parentTaskId;
		
		public PK() {
		}
		public PK(Integer taskId, Integer parentTaskId) {
			super();
			this.taskId = taskId;
			this.parentTaskId = parentTaskId;
		}
		public Integer getTaskId() {
			return taskId;
		}
		public void setTaskId(Integer taskId) {
			this.taskId = taskId;
		}
		public Integer getParentTaskId() {
			return parentTaskId;
		}
		public void setParentTaskId(Integer parentTaskId) {
			this.parentTaskId = parentTaskId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((parentTaskId == null) ? 0 : parentTaskId.hashCode());
			result = prime * result
					+ ((taskId == null) ? 0 : taskId.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PK other = (PK) obj;
			if (parentTaskId == null) {
				if (other.parentTaskId != null)
					return false;
			} else if (!parentTaskId.equals(other.parentTaskId))
				return false;
			if (taskId == null) {
				if (other.taskId != null)
					return false;
			} else if (!taskId.equals(other.taskId))
				return false;
			return true;
		}
	}
}
