package com.pai.base.api.template;

import com.pai.base.api.template.model.TemplateVarsContainer;
import com.pai.base.api.template.model.TemplateVo;

public interface TemplateService {

	public TemplateVo getTemplate(String templateKey);
	
	public String parseSubject(TemplateVo templateVo,TemplateVarsContainer varContainer);
	
	public String parsePlainContent(TemplateVo templateVo,TemplateVarsContainer varContainer);
	
	public String parseHtmlContent(TemplateVo templateVo,TemplateVarsContainer varContainer);
}
