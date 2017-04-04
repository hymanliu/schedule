package com.hyman.schedule.common.jdbc;

import java.io.Serializable;

public class Column implements Serializable{
	
	private static final long serialVersionUID = 2747697034001845952L;
	private String name;
	private String type;
	private String comment;
	public Column(){}
	
	public Column(String name, String type, String comment) {
		super();
		this.name = name;
		this.type = type;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}