/*package com.pai.app.web.core.framework.jms;

import org.springframework.stereotype.Service;

import com.skg.app.core.framework.web.create.IRuleCreate;
import com.skg.app.core.framework.web.create.entity.RuleCreateMsg;
import com.skg.base.core.helper.SpringHelper;
import com.skg.service.mq.jms.JmsHandler;

@Service
public class RuleCreateHandler implements JmsHandler<RuleCreateMsg> {

	@Override
	public String getMsgType() {
		return RuleCreateMsg.MSG_TYPE;
	}

	@Override
	public void execute(RuleCreateMsg vo) {
		// 调用验证规则生成方法
		IRuleCreate iRuleCreate = SpringHelper.getBean(IRuleCreate.class);
		if (iRuleCreate != null) {
			iRuleCreate.create();
		}
	}

}
*/