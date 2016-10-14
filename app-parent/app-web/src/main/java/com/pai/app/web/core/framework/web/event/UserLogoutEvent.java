/*package com.pai.app.web.core.framework.web.event;

import org.springframework.context.ApplicationEvent;

import com.pai.biz.frame.ou.persistence.entity.MemberPo;
import com.pai.biz.frame.ou.persistence.entity.UserPo;

@SuppressWarnings("serial")
public class UserLogoutEvent extends ApplicationEvent{
	public UserLogoutEvent(Object source){
		super(source);
	}
	public UserPo getUserPo() {
		if(source instanceof UserPo)
			return (UserPo)source;
		return null;
	}
	public MemberPo getMemberPo() {
		if(source instanceof MemberPo)
			return (MemberPo)source;
		return null;
	}
}
*/