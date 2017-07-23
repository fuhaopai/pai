package com.pai.service.mq.jms;

import com.pai.base.api.model.IMsgVo;
import com.pai.base.core.helper.SpringHelper;
import com.pai.service.mq.activemq.event.JmsConsumerEvent;

public class JmsConsumer{
	public void execute(IMsgVo model) {
		if(model instanceof IMsgVo){
			IMsgVo vo=(IMsgVo)model;
			//这里限定死只有一个监听者，如果有handle方法则被当做队列模式
			JmsHandler<IMsgVo> jmsHandler = (JmsHandler<IMsgVo>)SpringHelper.getBean(vo.getMsgType() + "Handler");
			if(jmsHandler!=null){
				jmsHandler.execute(vo);
			}else { 
				//通过spring事件监听间接实现TOPC模式,不过，写死了destination只能用一次
				JmsConsumerEvent event = new JmsConsumerEvent(model);
				SpringHelper.publishEvent(event);
			}
		}		
	}	
}
