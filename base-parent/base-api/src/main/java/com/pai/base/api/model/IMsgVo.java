package com.pai.base.api.model;

import java.io.Serializable;

public interface IMsgVo extends Serializable{
	/**
	 * 获得消息类型。console=控制台；mail=邮件；inner=站内消息；sms=短信
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getMsgType();
}
