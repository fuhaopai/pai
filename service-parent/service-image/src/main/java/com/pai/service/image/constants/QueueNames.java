package com.pai.service.image.constants;

/**
 *     MQ队列名配置
 *     @author Suoron 
 */

public class QueueNames {
	
	public final static String DEFAULT_QUEUE = "mq.queue";                  //默认队列
	public final static String STATIC_QUEUE_SEND = "mq.to_static";       //静态化发送队列
	public final static String STATIC_QUEUE_RECV = "mq.from_static";    //静态化发送接收
	
}
