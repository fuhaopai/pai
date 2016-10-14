package com.pai.base.core.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * <pre> 
 * 描述：单元测试的基础类
 * 构建组：base-core
 * 作者：颜超敏
 * 邮箱: yanchaomin@pai.com
 * 日期:2013-12-20-下午8:31:42
 * 版权：pai.com版权所有
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests{
	protected Log logger=LogFactory.getLog(BaseTestCase.class);
	
    @Test
    public void test(){
    	
    }
    
}