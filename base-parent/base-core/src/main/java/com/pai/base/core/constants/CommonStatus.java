package com.pai.base.core.constants;

import com.pai.base.core.util.string.StringUtils;

/**
 * 
 * <pre> 
 * 描述：通用状态
 * 构建组：pai-core
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2016-09-29 18:00:11
 * </pre>
 */
public enum CommonStatus {
	INACTIVE,		//未激活。一般作为初始状态，可以认为是草稿。
	ACTIVED,		//激活。也可以认为是发布、可用状态。
	LOCKED,			//锁定。针对激活状态，临时让其不可用，则置为锁定（不适合使用“未激活”，因为意义不同）
	validated,//验证
	unvalidate;//未验证
	
	public String key(){
		return name().toLowerCase();
	}
	public static CommonStatus fromKey(String key){
		if(StringUtils.isEmpty(key)){
			return null;
		}
		for(CommonStatus status:values()){
			if(key.toLowerCase().equals(status.key())){
				return status;
			}
		}
		return null;
	}
}
