package com.pai.base.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pai.base.api.constants.ResponseCode;
import com.pai.base.api.exception.ManagerException;
import com.pai.base.api.response.BaseResponse;

/**
 * 提供接口响应aop拦截
 *
 * @date 2017-06-09
 */
@Aspect
public class ExceptionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

    @Around("within(com.pai.biz.*.api.impl.*)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        String singnatureStr = pjp.getSignature().toString();

        LOGGER.info("********** {} begin **********", singnatureStr);

        try {
            result = pjp.proceed();
        } catch (ManagerException e) {
            LOGGER.error("{}, 请求参数：{}", singnatureStr, JSON.toJSONString(pjp.getArgs()));
            LOGGER.error("{}, 异常信息：{}", singnatureStr, e.getMessage());
            result = new BaseResponse<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = new BaseResponse<>(ResponseCode.SYSTEM_EXCEPTION.getCode(), ResponseCode.SYSTEM_EXCEPTION.getDesc());
        }
        LOGGER.info("********** {} end **********", singnatureStr);
        return result;
    }

}
