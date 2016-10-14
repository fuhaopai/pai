package com.pai.service.mq.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DefaultJmsProducer implements JmsProducer {
    private static final Log logger=LogFactory.getLog(DefaultJmsProducer.class);	
	
	private Destination destination;
	private JmsTemplate jmsTemplate;

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public void sendToQueue(Object object) {
		logger.debug("send the message to queue.");
					
		jmsTemplate.send(destination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createTextMessage("Hello ActiveMQ Text Message");  
            }  
        });  
	}
}
