package com.pai.app.web.core.framework.web.listener;

import org.springframework.context.ApplicationListener;

import com.pai.app.web.core.framework.web.event.FileDeleteEvent;
import com.pai.base.core.util.FileUtils;

public class FileDeleteListener implements ApplicationListener<FileDeleteEvent>{

	public void onApplicationEvent(FileDeleteEvent event) {
		try {
			FileUtils.deleteFile(event.getDeleteFilePath());	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
