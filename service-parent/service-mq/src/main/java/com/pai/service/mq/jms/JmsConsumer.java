package com.pai.service.mq.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pai.base.api.model.IMsgVo;
import com.pai.base.core.helper.SpringHelper;
import com.pai.service.mq.activemq.event.JmsConsumerEvent;
/**
 * 队列异步不会丢消息，主题会丢消息，即通过管控台发送异步消息，服务器启动后队列能收到，主题不能收到
 * 队列发送1,2,3,4时，如果监听器配置了A/B/C，3个监听类，则先后顺序为A收到1，B收到2，C收到3，A收到4
 * 主题模式发送1,2,3,4时，如果监听器配置了A/B/C，3个监听类，则A，B，C同时收到1,2,3,4
 * 如果要实现topic模式又不丢失消息，则使用spring事件模拟，但这只能监听一个destination,还是要配置多个destination实现
 */
public class JmsConsumer implements MessageListener{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessage) {
				IMsgVo msgVo = (IMsgVo) ((ObjectMessage) message).getObject();
				JmsHandler<IMsgVo> jmsHandler = (JmsHandler<IMsgVo>)SpringHelper.getBean(msgVo.getMsgType() + "Handler");
				if(jmsHandler!=null){
					jmsHandler.execute(msgVo);
				}else {
					JmsConsumerEvent event = new JmsConsumerEvent(msgVo);
					SpringHelper.publishEvent(event);
				}
			}
		} catch (JMSException e) {
			logger.error(e.getMessage());
		}
		
	}	
}
