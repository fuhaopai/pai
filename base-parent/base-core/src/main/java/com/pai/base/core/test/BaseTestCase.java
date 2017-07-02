package com.pai.base.core.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(JUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests{
	protected Log logger=LogFactory.getLog(BaseTestCase.class);
	
	@Autowired
    private ApplicationContext applicationContext;


    /**
     * 得到spring上下文对象
     *
     * @return
     */
    public final ApplicationContext getApplicationContext() {

        return applicationContext;
    }
    
}