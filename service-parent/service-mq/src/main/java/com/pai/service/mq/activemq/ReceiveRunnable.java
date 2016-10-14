package com.pai.service.mq.activemq;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.core.JmsTemplate;

import com.pai.base.api.model.IMsgVo;
import com.pai.service.mq.jms.JmsConsumer;

public class ReceiveRunnable  implements Runnable{
	private AtomicBoolean stopReceiving = new AtomicBoolean();
	private JmsTemplate consumerTemplate;
	private JmsConsumer jmsConsumer;
	private String QUEUE;
	
	
	public String getQUEUE() {
		return QUEUE;
	}

	public void setQUEUE(String qUEUE) {
		QUEUE = qUEUE;
	}

	public ReceiveRunnable(JmsTemplate consumerTemplate,JmsConsumer jmsConsumer,String queue) {
		super();
		this.consumerTemplate = consumerTemplate;
		this.jmsConsumer = jmsConsumer;
		//IConfigHelper configHelper =SpringHelper.getBean(IConfigHelper.class);
		//QUEUE = configHelper.getParamValue("mq.queue");				
		QUEUE = queue;
	}

	public void run() {
		while (!stopReceiving.get()) {
			try {
				Message message = consumerTemplate.receive(QUEUE);
				System.out.println(Thread.currentThread() + " 收到消息：" + message);
				
				if(message instanceof ObjectMessage){
					ObjectMessage objectMessage = (ObjectMessage)message;
					jmsConsumer.execute((IMsgVo)objectMessage.getObject());
				}else{
					//System.out.println(Thread.currentThread() + " 收到消息：" + message);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		stopReceiving.set(true);
	}
}
