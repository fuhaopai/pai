<#assign class=model.variables.class>
<#assign classVar=model.variables.classVar>
<#assign module=model.variables.module>
<#assign sys=model.variables.sys>
<#assign baseClass=model.variables.baseClass>
package com.${sys}.biz.${module}.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.alibaba.fastjson.JSON;
import com.${sys}.base.api.response.BaseResponse;
import com.${sys}.base.api.response.ResPage;
import com.${sys}.base.api.service.IdGenerator;
import com.${sys}.base.core.test.BaseTestCase;
import com.${sys}.biz.${module}.api.model.${class}UserBean;
import com.${sys}.biz.${module}.api.service.${class}Service;
import com.${sys}.base.api.service.IdGenerator;
import com.${sys}.base.core.test.BaseTestCase;

/**
 * 测试类，。
 * @author ${vars.developer}
 *
 */
@ContextConfiguration({"classpath:conf/application.xml"})
public class ${baseClass}ServiceTest extends BaseTestCase{
	
	@Resource
    private IdGenerator idGenerator;
    
    @Resource
    private ${class}Service ${classVar}service;
    
    @Test
	public void list${class}ServiceTest(){
		Map<String, Object> map = new HashMap<String, Object>();
		BaseResponse<ResPage<${class}Bean>> baseResponse = ${classVar}Service.list${class}Service(map, 1, 10);
		System.out.println(JSON.toJSONString(baseResponse, true));
	}
}
