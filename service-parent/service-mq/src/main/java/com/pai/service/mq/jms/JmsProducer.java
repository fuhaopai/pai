package com.pai.service.mq.jms;

public interface JmsProducer {
	/**
	 * 发送消息到队列中
	 * @param jmsVo 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void sendToQueue(Object object);
}
