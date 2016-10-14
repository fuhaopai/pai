package com.pai.app.web.core.framework.web.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * <pre> 
 * 描述：TODO 百度推送事件
 * 构建组：app-web
 * 作者：钟金佑
 * 邮箱: zhongjinyou@skg.com
 * 日期:Sep 23, 2015-4:26:02 PM
 * 版权：SKG 公司版权所有
 * </pre>
 */
public class OuPushEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public OuPushEvent(Object source) {
		super(source);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getParams (){
		if(source instanceof Map)
			return (Map<String, String>)source;
		return null;
	}
}
