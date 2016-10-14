package com.pai.app.web.core.framework.web.listener;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.pai.app.web.core.framework.web.event.SessionDestroyedEvent;
import com.pai.base.core.helper.SpringHelper;

public class PAISessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ServletContext application = session.getServletContext();
		HashSet sessions = (HashSet)application.getAttribute("sessions");
		if(sessions==null){
			sessions = new HashSet();
			application.setAttribute("sessions", sessions);
		}
		sessions.add(session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		SessionDestroyedEvent sessionDestroyedEvent = new SessionDestroyedEvent(se,session); 
		SpringHelper.publishEvent(sessionDestroyedEvent);

		ServletContext  application = session.getServletContext();
		HashSet sessionsHashSet = (HashSet) application.getAttribute("sessions");
		if(sessionsHashSet!=null){
			sessionsHashSet.remove(session);	
		}
		
	}
	
}
