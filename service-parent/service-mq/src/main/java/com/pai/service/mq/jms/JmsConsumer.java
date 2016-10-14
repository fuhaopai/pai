package com.pai.service.mq.jms;

import com.pai.base.api.model.IMsgVo;
import com.pai.base.core.helper.SpringHelper;
import com.pai.service.mq.activemq.event.JmsConsumerEvent;

public class JmsConsumer{
	public void execute(IMsgVo model) {
		if(model instanceof IMsgVo){
			IMsgVo vo=(IMsgVo)model;
			JmsHandler<IMsgVo> jmsHandler = (JmsHandler<IMsgVo>)SpringHelper.getBean(vo.getMsgType() + "Handler");
			if(jmsHandler!=null){
				jmsHandler.execute(vo);
			}else {
				JmsConsumerEvent event = new JmsConsumerEvent(model);
				SpringHelper.publishEvent(event);
			}
		}		
	}	
}
