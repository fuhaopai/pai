package com.pai.inf.validate.parser;

import java.lang.reflect.Field;

/**
 * 校验框架注解解析器接口
 * 实现该接口构建解析器，理论上每一个注解都会对应一个解析器， 解析器需要在校验器中注册使用
 * @ClassName: IAnnotationParser 
 * @Description: TODO
 * @author: fuhao
 * @date: 2017年6月20日 下午5:42:21
 */
public interface IAnnotationParser {
	
	/**
	 * 校验一个字段f的值是否符合基于value的某一个规则，并且把校验结果保存在ValidateResult中返回
	 * @param f
	 * @param value
	 * @return
	 */
	public ValidateResult validate(Field f, Object value);
}
