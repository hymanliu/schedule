package com.hyman.schedule.master.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthInterceptor extends AbstractPhaseInterceptor<Message> {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    public AuthInterceptor() {
        //定义拦截器阶段
        super(Phase.RECEIVE);
    }
	@Override
	public void handleMessage(Message msg) throws Fault {
		HttpServletRequest req = (HttpServletRequest)msg.get(AbstractHTTPDestination.HTTP_REQUEST);
		String host = req.getRemoteHost();
		System.out.println(host);
		logger.info("filter the message");
	}

}
