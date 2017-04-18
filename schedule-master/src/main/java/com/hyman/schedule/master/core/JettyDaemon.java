package com.hyman.schedule.master.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.hyman.schedule.common.util.SysUtil;


public class JettyDaemon implements ApplicationContextAware {
	
	private static final Logger LOG = LoggerFactory.getLogger(JettyDaemon.class);
	private static final String LOG_PATH = "./logs/front/yyyy_mm_dd.log";
	private static final String WEB_XML = "META-INF/WEB-INF/web.xml";
    private static final String WEB_WAR_PATH = "src/main/webapp";
    
    @Value("#{configProperties['master.port']}")
	private Integer port=5566;
	private Server server;
	private ApplicationContext applicationContext;
	
	@Override  
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
		this.applicationContext = applicationContext;  
	}
	
	public void start() throws Exception
	{
		server = new Server();
        server.setThreadPool(createThreadPool());
        server.addConnector(createConnector());
        server.setHandler(createHandlers());        
        server.setStopAtShutdown(true);
        server.start();
	}
	
	public void stop() throws Exception
	{
		server.stop();
	}
	
	
	public void join() throws Exception
	{
		server.join();
		LOG.info("please visit web address: http://:"+SysUtil.getHostName()+":"+this.port+"/index");
	}
	
	private HandlerCollection createHandlers()
    {                
        WebAppContext webAppContext = new WebAppContext();
        webAppContext .setContextPath("/");
        if(isRunningInShadedJar()){
            webAppContext.setWar(getShadedWarUrl());
        }
        else{            
            webAppContext.setWar(WEB_WAR_PATH);
        }
        
        XmlWebApplicationContext xmlWebAppContext = new XmlWebApplicationContext();  
        xmlWebAppContext.setParent(applicationContext);  
        xmlWebAppContext.setConfigLocation("");  
        xmlWebAppContext.setServletContext(webAppContext.getServletContext());  
        xmlWebAppContext.refresh();  
        webAppContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,xmlWebAppContext);
        
        List<Handler> handlers = new ArrayList<Handler>();        
        handlers.add(webAppContext);
        
        RequestLogHandler logHandler = new RequestLogHandler();
        logHandler.setRequestLog(createRequestLog());
        
        handlers.add(logHandler);
        
        HandlerCollection ret = new HandlerCollection();
        ret.setHandlers(handlers.toArray(new Handler[0]));
        return ret;
    }

	private SelectChannelConnector createConnector()
    {
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        return connector;
    }
	
	//TODO 改造为使用spring ioc的方式
	private ThreadPool createThreadPool() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        return threadPool;
	}

	private RequestLog createRequestLog()
    {
        NCSARequestLog reqLog = new NCSARequestLog();        
    	File logPath = new File(LOG_PATH);
        logPath.getParentFile().mkdirs();     
        reqLog.setFilename(logPath.getPath());
        reqLog.setRetainDays(90);
        reqLog.setExtended(false);
        reqLog.setAppend(true);
        reqLog.setLogTimeZone("GMT");
        reqLog.setLogLatency(true);
        return reqLog;
    }
		
		
	private boolean isRunningInShadedJar()
    {
    	String className = this.getClass().getName().replace('.', '/');
    	String classJar =  this.getClass().getResource("/" + className + ".class").toString();
    	System.out.println(classJar);
    	if (classJar.startsWith("jar:")) {
    		return true;
    	}
        return false;
    }
    
    private URL getResource(String aResource)
    {
        return Thread.currentThread().getContextClassLoader().getResource(aResource); 
    }
    
    private String getShadedWarUrl()
    {
    	LOG.info("web_xml:"+WEB_XML);
        String urlStr = getResource(WEB_XML).toString();
        return urlStr.substring(0, urlStr.length() - 15);
    }	
    
    public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
