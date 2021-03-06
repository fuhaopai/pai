package com.pai.base.core.validate.rule;

import com.pai.base.core.validate.AnnotationValidator;
import com.pai.base.core.validate.parser.ValidateResult;

/**
 * 使用AnnotationValidator的校验规则
 * 
 * @see AnnotationValidator
 * @author fuhao
 *
 */
public class AnnotationRule implements Rule{
	private String message;
	private Object o;
	
	public AnnotationRule(Object o) {
		this.o = o;
	}
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isValid() {
		ValidateResult result = AnnotationValidator.validate(this.o);
		this.message = result.getMessage();
		return result.isValid();
	}
	
}
