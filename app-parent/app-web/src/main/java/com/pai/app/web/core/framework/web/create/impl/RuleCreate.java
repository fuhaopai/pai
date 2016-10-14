package com.pai.app.web.core.framework.web.create.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;  
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.pai.app.web.core.framework.engine.FreemarkEngine;
import com.pai.app.web.core.framework.web.create.IRuleCreate;
import com.pai.base.core.helper.SpringHelper;

import freemarker.template.TemplateException;
@Service
@Scope("singleton")
public class RuleCreate implements IRuleCreate{
	@Resource
	private FreemarkEngine freemarkEngine;
	
	public void create(){
		/*ValidateRepository  validateRepository = SpringHelper.getBean(ValidateRepository.class);
		if(validateRepository!=null){
			List<ValidatePo>validatePos=validateRepository.findAll();		
			Map<String, Object>model=new HashMap<String, Object>();
			model.put("ruleList", validatePos);
			try {
			   String classPath = getClass().getClassLoader().getResource("").getPath();
		        int i = classPath.indexOf("WEB-INF");
		        classPath = classPath.substring(0, i);
		        classPath = classPath.replaceAll(" ", " ");
				String  rulejs=freemarkEngine.mergeTemplateIntoString("rulejs.ftl", model);
				File file=new File(classPath+"/scripts/skg/validate/rule.js");
				if (file.exists()) {
					file.createNewFile();
				}
				BufferedWriter output = new BufferedWriter(new FileWriter(file));  
		        output.write(rulejs);  
		        output.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		}*/	
	}

}
