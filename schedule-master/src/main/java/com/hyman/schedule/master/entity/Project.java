package com.hyman.schedule.master.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name=Project.TABLE_NAME)
public class Project implements Serializable {

	private static final long serialVersionUID = -3479439467003051023L;
	protected final static String TABLE_NAME="bdp_poject";

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="name",columnDefinition=("varchar(50) default null comment '项目名称'"),updatable=false)
	private String name;
	@Column(name="remarks",columnDefinition=("varchar(255) default null comment '描述'"))
	private String remarks;
	@Column(name="email",columnDefinition=("varchar(255) default null comment '项目负责人邮箱'"))
	private String email;
	@Column(name="create_time",columnDefinition=("datetime default null comment '创建时间'"),updatable=false)
	private Date createTime;
	@Column(name="modify_time",columnDefinition=("datetime default null comment '修改时间'"))
	private Date modifyTime;
	
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
}
