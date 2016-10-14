package com.pai.service.mq.activemq.listener;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pai.base.core.util.ExceptionUtil;

/**
 * JMS发送错误时监控。
 * <pre>
 *  配置在app-jms.xml中。
 *  &lt;bean id="jmsExceptionListener" class="com.hotent.core.jms.JmsExceptionListener">&lt;/bean>
 *  </pre>
 * @author ray
 *
 */
public class JmsExceptionListener implements ExceptionListener {
	protected Logger logger = LoggerFactory.getLogger(JmsExceptionListener.class);

	public void onException(JMSException ex) {
		ex.printStackTrace();
		String message= ExceptionUtil.getExceptionMessage(ex);
		logger.error(message);
	}

}
