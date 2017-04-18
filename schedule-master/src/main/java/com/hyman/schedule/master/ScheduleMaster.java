package com.hyman.schedule.master;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.hyman.schedule.common.bean.ServerInfo;
import com.hyman.schedule.common.config.ConfigurationContext;
import com.hyman.schedule.common.util.SysUtil;
import com.hyman.schedule.master.core.JobCreateTracker;
import com.hyman.schedule.master.core.MasterContext;
import com.hyman.schedule.master.core.MasterContextTracker;
import com.hyman.schedule.master.core.StateChangeTracker;

public class ScheduleMaster {
	
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleMaster.class);
	
	public static final int ZK_SESSION_TIMEOUT=5000;
	public static final int ZK_CONNECTION_TIMEOUT=5000;
	public static final String ROOT_PATH ="/schedule";
	public static final String MASTER_PATH = ROOT_PATH+"/master";
    public static final String SERVERS_PATH=ROOT_PATH+"/servers";
    public static final String SLAVES_PATH=ROOT_PATH+"/slaves";
    
    private MasterContext masterContext = MasterContext.getInstance();
    
	private ZkClient zkclient; 
	private ServerInfo serverInfo;
	private IZkDataListener dataListener;
	private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
	private ApplicationContext context;
	
	public ScheduleMaster(){
		String host = SysUtil.getHostName();
		int port = Integer.parseInt(ConfigurationContext.getValue("master.port","5566"));
		this.context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.serverInfo = new ServerInfo(host,port);
		String zkServers = ConfigurationContext.getValue("zookeeper.address");
		zkclient = new ZkClient(zkServers, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT, new BytesPushThroughSerializer());
		dataListener = new IZkDataListener(){
			@Override
			public void handleDataChange(String dataPath, Object data)
					throws Exception {
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				ServerInfo masterInfo = masterContext.getActiveMaster();
				if (masterInfo!=null && masterInfo.equals(serverInfo)){
					takeMaster();
				}else{
					delayExector.schedule(new Runnable(){
						public void run(){
							takeMaster();
						}
					}, 5, TimeUnit.SECONDS);
					
				}
			}
		};
	}
	
	public void start(){
		try {
			registServer();
			zkclient.subscribeDataChanges(MASTER_PATH, dataListener);
			takeMaster();
			startTracker();
		} catch (Exception e) {
			LOG.error("start the schedule master error",e);
			System.exit(1);
		}
	}
	
	private void startTracker() {
		JobCreateTracker jobCreateTracker = context.getBean(JobCreateTracker.class);
		Thread jobCreateTrackerThread = new Thread(jobCreateTracker);
		jobCreateTrackerThread.start();
		StateChangeTracker stateChangeTracker = context.getBean(StateChangeTracker.class);
		Thread stateChangeTrackerThread = new Thread(stateChangeTracker);
		stateChangeTrackerThread.start();
		MasterContextTracker masterContextTracker = context.getBean(MasterContextTracker.class);
		Thread masterContextTrackerThread = new Thread(masterContextTracker);
		masterContextTrackerThread.start();
	}
	
	public void registServer(){
		String mePath = SERVERS_PATH.concat("/")
				.concat(serverInfo.getHost())
				.concat(":"+serverInfo.getPort());
		try {
			zkclient.createEphemeral(mePath, JSON.toJSONString(serverInfo).getBytes());
		} catch (ZkNoNodeException e) {
			zkclient.createPersistent(SERVERS_PATH, true);
			registServer();
		}
	}
	
	private void takeMaster() {
		try {
			zkclient.create(MASTER_PATH, JSON.toJSONString(serverInfo).getBytes(), CreateMode.EPHEMERAL);
			masterContext.setActiveMaster(serverInfo);
			System.out.println(serverInfo.getHost()+":"+serverInfo.getPort()+" is master");
			
			delayExector.schedule(new Runnable() {			
				public void run() {
					if (checkMaster()){
						releaseMaster();
					}
				}
			}, 5, TimeUnit.SECONDS);
			
		} catch (ZkNodeExistsException e) {
			ServerInfo runningInfo = zkclient.readData(MASTER_PATH, true);
			if (runningInfo == null) {
				takeMaster();
			} else {
				masterContext.setActiveMaster(serverInfo);
			}
		} catch (Exception e) {
			// ignore;
		}

	}
	
	private void releaseMaster() {
		if (checkMaster()) {
			zkclient.delete(MASTER_PATH);
		}
	}

	private boolean checkMaster() {
		try {
			ServerInfo eventData = zkclient.readData(MASTER_PATH);
			masterContext.setActiveMaster(eventData);
			if (masterContext.getActiveMaster().equals(serverInfo)) {
				return true;
			}
			return false;
		} catch (ZkNoNodeException e) {
			return false;
		} catch (ZkInterruptedException e) {
			return checkMaster();
		} catch (ZkException e) {
			return false;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		new ScheduleMaster().start();
		
	}
}