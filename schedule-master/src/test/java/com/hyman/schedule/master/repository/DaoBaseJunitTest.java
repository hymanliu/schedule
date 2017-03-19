package com.hyman.schedule.master.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("classpath*:applicationContext.xml")
@TransactionConfiguration(transactionManager="transactionManager" , defaultRollback=true)
public class DaoBaseJunitTest  extends AbstractJUnit4SpringContextTests  {

    @Autowired
    protected SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        Session session = getSession(this.sessionFactory);
        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(session));
        logger.debug("Hibernate session is bound");
    }

    @After
    public void tearDown() throws Exception {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        closeSession(sessionHolder.getSession(), sessionFactory);
        logger.debug("Hibernate session is closed");
    }

    protected void closeSession(Session session, SessionFactory sessionFactory) {
        SessionFactoryUtils.closeSession(session);
    }

    protected Session getSession(SessionFactory sessionFactory){
    	Session session = sessionFactory.openSession();
        return session;
    }
}