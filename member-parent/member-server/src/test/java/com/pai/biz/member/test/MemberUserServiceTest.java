package com.pai.biz.member.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.alibaba.fastjson.JSON;
import com.pai.base.api.response.BaseResponse;
import com.pai.base.api.response.ResPage;
import com.pai.base.api.service.IdGenerator;
import com.pai.base.core.test.BaseTestCase;
import com.pai.biz.member.api.model.MemberUserBean;
import com.pai.biz.member.api.service.MemberUserService;

/**
 * 测试类，。
 * @author FU_HAO
 *
 */
@ContextConfiguration({"classpath:conf/application.xml"})
public class MemberUserServiceTest extends BaseTestCase{
	
	@Resource
    private IdGenerator idGenerator;
    
    @Resource
    private MemberUserService memberUserService;
    
    @Test
	public void listMemberUserServiceTest(){
		Map<String, Object> map = new HashMap<String, Object>();
		BaseResponse<ResPage<MemberUserBean>> baseResponse = memberUserService.listMemberUserService(map, 1, 10);
		System.out.println(JSON.toJSONString(baseResponse, true));
	}
}
