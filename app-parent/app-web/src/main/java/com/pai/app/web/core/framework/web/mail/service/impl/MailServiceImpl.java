package com.pai.app.web.core.framework.web.mail.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pai.app.web.core.framework.web.mail.MailHelper;
import com.pai.app.web.core.framework.web.mail.MailHelper.MailObj;
import com.pai.app.web.core.framework.web.mail.service.MailService;

import freemarker.template.TemplateException;

/**
 * 
 * <pre> 
 * 描述：邮件发送 Repository 接口实现
 * 构建组：biz-ec
 * 作者：马捷聪
 * 邮箱: majiecong@skg.com
 * 日期:May 20, 2015-2:03:14 PM
 * 版权：SKG 公司版权所有
 * </pre>
 */
@Service
public class MailServiceImpl implements MailService{
	
	private static Logger logger =  Logger.getLogger(MailServiceImpl.class);
	
	@Resource
	private MailHelper mapHelper;

	@Override
	public void sendRegActivity(String subject, Map<String, Object> data,List<MailObj> mailAccounts) {
		String ftlUrl = "template/mail/regActivity.ftl";
		try {
			mapHelper.sendMail(subject,ftlUrl,data,true,mailAccounts);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (TemplateException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	@Override
	public void sendRegActivity(String subject, Map<String, Object> data,String... mailAccounts) {
		String ftlUrl = "template/mail/regActivity.ftl";
		try {
			mapHelper.sendMail(subject,ftlUrl,data,true,mailAccounts);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (TemplateException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

}
