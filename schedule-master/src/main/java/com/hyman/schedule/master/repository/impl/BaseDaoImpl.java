package com.hyman.schedule.master.repository.impl;

import java.io.Serializable;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

public class BaseDaoImpl<T, ID extends Serializable> extends GenericDAOImpl<T, ID>{
	
	@Resource(name="sessionFactory")
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
