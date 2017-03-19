package com.hyman.schedule.master.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name=JobRelation.TABLE_NAME)
public class JobRelation implements Serializable {

	private static final long serialVersionUID = -2645071585542405998L;
	public static final String TABLE_NAME ="t_job_relation";
	
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
		@Column(name="job_id",columnDefinition="varchar(18) not null comment '实例ID'")
		private String jobId;
		@Column(name="parent_job_id",columnDefinition="varchar(18) not null comment '父实例ID'")
		private String parentJobId;
		public PK() {
		}
		public String getJobId() {
			return jobId;
		}
		public void setJobId(String jobId) {
			this.jobId = jobId;
		}
		public String getParentJobId() {
			return parentJobId;
		}
		public void setParentJobId(String parentJobId) {
			this.parentJobId = parentJobId;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
			result = prime * result
					+ ((parentJobId == null) ? 0 : parentJobId.hashCode());
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
			if (jobId == null) {
				if (other.jobId != null)
					return false;
			} else if (!jobId.equals(other.jobId))
				return false;
			if (parentJobId == null) {
				if (other.parentJobId != null)
					return false;
			} else if (!parentJobId.equals(other.parentJobId))
				return false;
			return true;
		}
	}
}
