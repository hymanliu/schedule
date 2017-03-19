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

import com.hyman.schedule.common.enums.Cycle;

@Entity
@Table(name=Task.TABLE_NAME)
public class Task implements Serializable {

	private static final long serialVersionUID = -3479439467003051023L;
	protected final static String TABLE_NAME="t_task";

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="name",columnDefinition=("varchar(50) default null comment '任务名称'"))
	private String name;
	@Column(name="crontab",columnDefinition=("varchar(50) default null comment '任务crontab'"))
	private String crontab;
	@Column(name="remarks",columnDefinition=("varchar(255) default null comment '任务描述'"))
	private String remarks;
	@Column(name="email",columnDefinition=("varchar(255) default null comment '项目负责人邮箱'"))
	private String email;
	@Column(name="create_time",columnDefinition=("datetime default null comment '创建时间'"),updatable=false)
	private Date createTime;
	@Column(name="modify_time",columnDefinition=("datetime default null comment '修改时间'"))
	private Date modifyTime;
	@Column(name="is_use",columnDefinition="int(4) default 1 comment '是否禁用'")
	private Boolean isUse;
	@Column(name="cycle",columnDefinition="varchar(20) not null comment '调度周期'")
	@Enumerated(EnumType.STRING)
	private Cycle cycle;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCrontab() {
		return crontab;
	}
	public void setCrontab(String crontab) {
		this.crontab = crontab;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getIsUse() {
		return isUse;
	}
	public void setIsUse(Boolean isUse) {
		this.isUse = isUse;
	}
	public Cycle getCycle() {
		return cycle;
	}
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
}
