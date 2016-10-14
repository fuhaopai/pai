package com.pai.app.web.core.framework.web.mail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pai.app.web.core.framework.engine.FreemarkEngine;
import com.pai.base.api.helper.IConfigHelper;
import com.pai.base.core.helper.SpringHelper;
import com.pai.service.image.jms.JmsService;
import com.pai.service.mail.entity.MailVo;

import freemarker.template.TemplateException;

/**
 * 
 * <pre> 
 * 描述：邮件发送工具类
 * 构建组：app-web
 * 作者：马捷聪
 * 邮箱: majiecong@skg.com
 * 日期:May 20, 2015-1:16:43 PM
 * 版权：SKG 公司版权所有
 * </pre>
 */
@Service
public class MailHelper {
	
	private static Logger logger = Logger.getLogger(MailHelper.class);
	
	private IConfigHelper configHelper = SpringHelper.getBean(IConfigHelper.class);
	
	@Resource
	private FreemarkEngine freemarkEngine;
	
	public class MailObj{
		
		private String account;
		
		private String name;

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 
	 * 邮件发送基础方法
	 * @param subject
	 * @param content
	 * @param isHtml
	 * @param mailAccounts 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	private  void sendMail(String subject,String content,Boolean isHtml,String ...mailAccounts){
		MailVo mailVo = new MailVo(configHelper);
		try {
			mailVo.setContent(subject, content,true);
			for(String mailAccount : mailAccounts){
				mailVo.addTo(mailAccount);
			}
		} catch (EmailException e) {
			logger.error("邮件发送失败："+e);
			e.printStackTrace();
		}				
		JmsService jmsService = SpringHelper.getBean(JmsService.class);
		jmsService.startService();
		jmsService.getSendRunnable().send(mailVo);		
	}
	
	/**
	 * 
	 * 邮件发送基础方法
	 * @param subject
	 * @param content
	 * @param isHtml
	 * @param mailObjs 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	private  void sendMail(String subject,String content,Boolean isHtml,List<MailObj> mailObjs){
		MailVo mailVo = new MailVo(configHelper);
		try {
			mailVo.setContent(subject, content,true);
			for(MailObj mailObj : mailObjs){
				mailVo.addTo(mailObj.getAccount(),mailObj.getName());
			}
		} catch (EmailException e) {
			logger.error("邮件发送失败："+e);
			e.printStackTrace();
		}				
		JmsService jmsService = SpringHelper.getBean(JmsService.class);
		jmsService.startService();
		jmsService.getSendRunnable().send(mailVo);		
	}
	
	public  void sendMail(String subject,String tempUrl,Map<String, Object>  data,Boolean isHtml,String ...mailAccounts) throws IOException, TemplateException{
		String content = freemarkEngine.mergeTemplateIntoString(tempUrl, data);
		sendMail(subject, content, isHtml, mailAccounts);
	}
	
	public  void sendMail(String subject,String tempUrl,Map<String, Object>  data,Boolean isHtml,List<MailObj> mailObjs) throws IOException, TemplateException{
		String content = freemarkEngine.mergeTemplateIntoString(tempUrl, data);
		sendMail(subject, content, isHtml, mailObjs);
	}
	
	public  void sendTextMail(String subject,String content,List<MailObj> mailObjs) {
		sendMail(subject,content,false,mailObjs);
	}
	
	public  void sendTextMail(String subject,String content,String ...mailAccounts) {
		sendMail(subject,content,false,mailAccounts);
	}
	
	public  void sendHtmlMail(String subject,String content,String ...mailAccounts) {
		sendMail(subject,content,true,mailAccounts);
	}
	
	public  void sendHtmlMail(String subject,String content,List<MailObj> mailObjs) {
		sendMail(subject,content,true,mailObjs);
	}
}
