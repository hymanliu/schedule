package com.hyman.schedule.slave;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.hyman.schedule.common.bean.NodeInfo;
import com.hyman.schedule.common.bean.ServerInfo;
import com.hyman.schedule.common.config.ConfigurationContext;
import com.hyman.schedule.common.util.SysUtil;
import com.hyman.schedule.slave.core.HeartBeatTracker;
import com.hyman.schedule.slave.core.SlaveContext;



public class ScheduleSlave {
	
	static final Logger LOG = LoggerFactory.getLogger(ScheduleSlave.class);
	
	public static final int ZK_SESSION_TIMEOUT=5000;
	public static final int ZK_CONNECTION_TIMEOUT=5000;
    public static final String ROOT_PATH ="/schedule";
    public static final String MASTER_PATH = ROOT_PATH+"/master";
    public static final String SLAVES_PATH=ROOT_PATH+"/slaves";
    
	private ZkClient zkclient; 
	private IZkDataListener dataListener;
	private SlaveContext slaveContext = SlaveContext.getInstance();
	private ApplicationContext context;
	
	
	public ScheduleSlave(){
		String host = SysUtil.getHostName();
		int port = Integer.parseInt(ConfigurationContext.getValue("slave.port","5577"));
		this.context = new ClassPathXmlApplicationContext("applicationContext.xml");
		slaveContext.setNodeInfo(new NodeInfo(host,port));
		String zkServers = ConfigurationContext.getValue("zookeeper.address","localhost:2181");
		zkclient = new ZkClient(zkServers, ZK_SESSION_TIMEOUT, ZK_CONNECTION_TIMEOUT, new BytesPushThroughSerializer());
		
		dataListener = new IZkDataListener(){
			@Override
			public void handleDataChange(String dataPath, Object data)
					throws Exception {
				System.out.println(new String((byte[])data));
				ServerInfo serverInfo = data==null?null:JSON.parseObject(new String((byte[])data), ServerInfo.class);
				slaveContext.setActiveMaster(serverInfo);
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
			}
		};
		zkclient.subscribeDataChanges(MASTER_PATH, dataListener);
		
		byte[] data = zkclient.readData(MASTER_PATH, true);
		ServerInfo serverInfo = data==null?null:JSON.parseObject(new String(data), ServerInfo.class);
		slaveContext.setActiveMaster(serverInfo);
	}
	
	
	public void start() throws Exception {
		HeartBeatTracker heartBeatTracker = context.getBean(HeartBeatTracker.class);
		Thread heartBeatTrackerThread = new Thread(heartBeatTracker);
		heartBeatTrackerThread.start();
	}
	

	public static void main(String[] args) {
		
		ScheduleSlave slave = new ScheduleSlave();
		try{
			slave.start();
		}
		catch(Exception e){
			LOG.error("start slave failed",e);
			System.exit(1);
		}
	}
}
