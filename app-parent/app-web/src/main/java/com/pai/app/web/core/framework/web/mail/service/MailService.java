package com.pai.app.web.core.framework.web.mail.service;

import java.util.List;
import java.util.Map;

import com.pai.app.web.core.framework.web.mail.MailHelper.MailObj;

/**
 * 
 * <pre> 
 * 描述：邮件发送 Repository接口
 * 构建组：biz-ec
 * 作者：马捷聪
 * 邮箱: majiecong@skg.com
 * 日期:May 20, 2015-2:01:50 PM
 * 版权：SKG 公司版权所有
 * </pre>
 */
public interface MailService {

	public void sendRegActivity(String subject,Map<String,Object> data,List<MailObj> mailAccounts);
	
	public void sendRegActivity(String subject,Map<String,Object> data,String ...mailAccounts);
}
