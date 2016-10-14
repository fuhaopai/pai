package com.pai.app.web.core.framework.web.event;

import java.io.File;

import org.springframework.context.ApplicationEvent;

public class FileDeleteEvent extends ApplicationEvent{
	public FileDeleteEvent(Object source){
		super(source);
	}
	public String getDeleteFilePath(){
		return (String)source;
	}
	
}
