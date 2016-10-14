package com.pai.service.mq.activemq;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.usage.DefaultUsageCapacity;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.activemq.usage.UsageCapacity;

import com.pai.base.api.helper.IConfigHelper;
import com.pai.base.core.helper.SpringHelper;

public class ActiveMQHelper {
	private static Integer LOCK =1;
	private static boolean isStartUp =false;	
	public static void startupJms() {
		try {
			IConfigHelper configHelper = SpringHelper.getBean(IConfigHelper.class);
			synchronized (LOCK) {
				if(!isStartUp){
					isStartUp = true ;
					String jmsName = configHelper.getParamValue("jms.name");
					String jmsIp = configHelper.getParamValue("jms.ip");
					String jmsPort = configHelper.getParamValue("jms.port");
					String jmsDir = configHelper.getParamValue("jms.dir");
					
					BrokerService broker = new BrokerService();
					broker.setBrokerName(jmsName);
					broker.addConnector("tcp://" + jmsIp + ":" + jmsPort);		
					broker.setUseJmx(true);
					broker.setPersistent(false);
					broker.setDataDirectory(jmsDir);
	
					setUsages(broker);
					
					broker.start();					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void setUsages(BrokerService broker){
		MemoryUsage memoryUsage = new MemoryUsage();
		UsageCapacity  mUsageCapacity = new DefaultUsageCapacity();
		mUsageCapacity.setLimit(33554432L);
		memoryUsage.setLimiter(mUsageCapacity);
		broker.getSystemUsage().setMemoryUsage(memoryUsage);
		
		StoreUsage storeUsage = new StoreUsage();
		UsageCapacity sUsageCapacity = new DefaultUsageCapacity();
		sUsageCapacity.setLimit(268435456L);
		storeUsage.setLimiter(sUsageCapacity);
		broker.getSystemUsage().setStoreUsage(storeUsage);
		
		TempUsage tempUsage = new TempUsage();
		UsageCapacity tUsageCapacity = new DefaultUsageCapacity();
		tUsageCapacity.setLimit(268435456L);
		tempUsage.setLimiter(tUsageCapacity);
		broker.getSystemUsage().setTempUsage(tempUsage);
	}
}
