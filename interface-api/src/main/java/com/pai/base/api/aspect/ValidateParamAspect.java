package com.pai.base.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pai.base.api.constants.ResponseCode;
import com.pai.base.api.response.BaseResponse;
import com.pai.base.api.validate.AnnotationValidator;
import com.pai.base.api.validate.parser.ValidateResult;

/**
 * 提供接口响应aop拦截
 *
 * @date 2017-06-09
 */
@Aspect
public class ValidateParamAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateParamAspect.class);

    @Around("within(com.pai.biz.*.api.impl.*)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	Object result;
    	Object[] objects = pjp.getArgs();
    	LOGGER.info("参数拦截开始====="+JSON.toJSONString(objects));
    	for(Object o : objects) {
    		ValidateResult validateResult = AnnotationValidator.validate(o);
    		if(!validateResult.isValid()) {
    			LOGGER.error("参数拦截信息"+JSON.toJSONString(validateResult.getMessage()));
    			return new BaseResponse<>(ResponseCode.BUSINESS_PARAMETER_EXCEPTION.getCode(), validateResult.getMessage());
    		}
    	}
    	LOGGER.info("======参数拦截结束=====");
    	result = pjp.proceed();
        return result;
    }

}
