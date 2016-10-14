package com.pai.app.web.core.framework.web.event;

import org.springframework.context.ApplicationEvent;

import com.pai.service.image.CloudUploadResult;
import com.pai.service.image.entity.UploadPath;

@SuppressWarnings("serial")
public class UploadCompleteEvent extends ApplicationEvent{
	
	private CloudUploadResult cloudUploadResult;
	
	public UploadCompleteEvent(Object uploadPath,CloudUploadResult cloudUploadResult){
		super(uploadPath);
	}
	public UploadPath getUploadPath(){
		return (UploadPath)source;
	}
	public CloudUploadResult getCloudUploadResult(){
		return cloudUploadResult;
	}
}
