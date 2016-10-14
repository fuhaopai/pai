package com.pai.service.image.jms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.pai.base.api.helper.IConfigHelper;
import com.pai.base.core.helper.SpringHelper;
import com.pai.service.image.constants.QueueNames;
import com.pai.service.mq.activemq.ReceiveRunnable;
import com.pai.service.mq.activemq.SendRunnable;
import com.pai.service.mq.jms.JmsConsumer;

/**
 *     JMS消息服务启动线程
 *     @author Suoron
 */

@Service
public class JmsService {
	
	private Map<String,ReceiveRunnable> receiveRunnables = null;
	private Map<String,SendRunnable> sendRunnables = null;
	
	IConfigHelper configHelper =SpringHelper.getBean(IConfigHelper.class);
		
	private void putRecvList(ReceiveRunnable receiveRunnable){
		 if(receiveRunnables == null){
			 receiveRunnables = new HashMap<String, ReceiveRunnable>();
		 }
		 receiveRunnables.put(receiveRunnable.getQUEUE(), receiveRunnable);		 
	}	
	private void putSendList(SendRunnable sendRunnable){
		 if(sendRunnables == null){
			 sendRunnables = new HashMap<String, SendRunnable>();
		 }
		 sendRunnables.put(sendRunnable.getQUEUE(), sendRunnable);		 
	}
	/**
	 *    启动所有生成者队列 
	 */
    private void startAllProductors(List<String> toQueues,ExecutorService executorService){
    	JmsTemplate producerTemplate = (JmsTemplate)SpringHelper.getBean("producerTemplate");
    	
    	for(int i=0;i<toQueues.size();i++){
    		if(toQueues.get(i) == null || toQueues.get(i).trim().length() == 0)
    			 continue;
    		SendRunnable sendRunnable = new SendRunnable(producerTemplate,toQueues.get(i));
    		this.putSendList(sendRunnable);
    		executorService.execute(sendRunnable);
    	}  	
    }
	/**
	 *    启动所有消费者队列 
	 */
    private void startAllConsumers(List<String> fromQueues,ExecutorService executorService){
    	JmsTemplate consumerTemplate = (JmsTemplate)SpringHelper.getBean("consumerTemplate");
    	JmsConsumer jmsConsumer = (JmsConsumer)SpringHelper.getBean("jmsConsumer");
    	
    	for(int i=0;i<fromQueues.size();i++){
    		if(fromQueues.get(i) == null || fromQueues.get(i).trim().length() == 0)
    			 continue;
    		
    		ReceiveRunnable receiveRunnable = new ReceiveRunnable(consumerTemplate,jmsConsumer,fromQueues.get(i));
    		this.putRecvList(receiveRunnable);
    		executorService.execute(receiveRunnable);
    	}  	
    }    
	public void startService(){
		int pool_size = 2;
    	List<String> toQueues = configHelper.getLikeToList("mq.to_");
    	List<String> fromQueues = configHelper.getLikeToList("mq.from_");
    	
    	//添加默认队列
    	toQueues.add(configHelper.getParamValue(QueueNames.DEFAULT_QUEUE));
    	fromQueues.add(configHelper.getParamValue(QueueNames.DEFAULT_QUEUE));
    	
    	pool_size += toQueues.size();
    	pool_size += fromQueues.size();
    	ExecutorService executorService = Executors.newFixedThreadPool(pool_size);
    	
    	startAllProductors(toQueues,executorService);       //启动所有生成者队列
    	startAllConsumers(fromQueues,executorService);  //启动所有消费者队列
	}
	
	public ReceiveRunnable getReceiveRunnableByQueue(String queue){ 	    
		return receiveRunnables ==null?null:receiveRunnables.get(queue);
	}	
	public SendRunnable getSendRunnableByQueue(String queue){
		return sendRunnables ==null?null:sendRunnables.get(queue);
	}	
	public ReceiveRunnable getReceiveRunnable(){
		return getReceiveRunnableByQueue(configHelper.getParamValue(QueueNames.DEFAULT_QUEUE));		
	}
	public SendRunnable getSendRunnable(){
		return getSendRunnableByQueue(configHelper.getParamValue(QueueNames.DEFAULT_QUEUE));
	}
	
}
