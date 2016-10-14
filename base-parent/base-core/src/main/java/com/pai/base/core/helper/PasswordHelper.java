package com.pai.base.core.helper;

import com.pai.base.api.constants.Constants;
import com.pai.base.api.helper.IConfigHelper;
import com.pai.base.core.util.EncryptUtil;
import com.pai.base.core.util.string.StringUtils;

public class PasswordHelper {
	public static String getEncryptPassword(String password) {		
		if(getEncryptType().equals(Constants.EncryptType.SHA)){
			return EncryptUtil.sha(password);
		}
		return EncryptUtil.md5(password);
	}
	
	private static String getEncryptType(){
		String encryptType = null;
		IConfigHelper configHelper = SpringHelper.getBean(IConfigHelper.class);
		if(configHelper!=null){
			encryptType = configHelper.getParamValue("encryt.type");
		}
		if(StringUtils.isEmpty(encryptType)){
			encryptType = Constants.EncryptType.MD5;	
		}
		return encryptType;
	}
}
