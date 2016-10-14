/*package com.pai.app.web.core.framework.jms;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skg.app.core.framework.web.valid.SmsSendHelper;
import com.skg.app.core.framework.web.valid.entity.SmsValidCodeMsg;
import com.skg.base.api.service.IdGenerator;
import com.skg.biz.gl.domain.GlAudit;
import com.skg.biz.gl.persistence.entity.GlAuditPo;
import com.skg.biz.gl.repository.GlAuditRepository;
import com.skg.biz.ou.domain.Member;
import com.skg.biz.ou.persistence.entity.MemberPo;
import com.skg.biz.ou.persistence.entity.ReacDetailPo;
import com.skg.biz.ou.repository.MemberRepository;
import com.skg.biz.ou.repository.ReacDetailRepository;
import com.skg.service.mq.jms.JmsHandler;

@Service
public class SmsValidCodeHandler implements JmsHandler<SmsValidCodeMsg>{
	
	@Resource
	private IdGenerator idGenerator;
	
	@Resource
	private GlAuditRepository glAuditRepository;
	
	@Resource
	private ReacDetailRepository reacDetailRepository;
	
	@Resource 
	private MemberRepository memberRepository;
	
	private static Logger logger = LoggerFactory.getLogger(SmsValidCodeHandler.class);
	
	@Override
	public String getMsgType() {
		return SmsValidCodeMsg.MSG_TYPE;
	}

	@Override
	public void execute(SmsValidCodeMsg vo) {
		String result = "";
		GlAuditPo glAuditPo = new GlAuditPo();
		ReacDetailPo reacDetailPo = vo.getReacDetailPo();
		
		try {
			String[] mobile = vo.getMobile().split("\\|");
			String[] msg = vo.getSendMsg().split("\\|"); 
			String sendType = vo.getSendType();
			String fromType = vo.getFromType();
			if(sendType == null){
				sendType = "xuanwu";
			}
			if("xuanwu".equals(sendType)){
				result = SmsSendHelper.sendMsgByXWMultiple(mobile, msg, fromType);
			}else if("skg".equals(sendType)){
				result = SmsSendHelper.sendMsgByCatMultiple(mobile, msg);
			}else{
				result = SmsSendHelper.sendMsgByNut(vo.getMobile(), vo.getSendMsg());
			}
			
			String tempParams = "mobile="+vo.getMobile()+"&msg="+vo.getSendMsg();
			if (tempParams.length() > 4000) {
				tempParams = tempParams.substring(0, 4000);
			}
			glAuditPo.setReqParams(tempParams);
			glAuditPo.setType("info");
			glAuditPo.setDetail(result);
			
			//是否记录会员激活信息推送
			if(reacDetailPo != null){
				reacDetailPo.setPushStatus("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String tempParams = "mobile="+vo.getMobile();
			if (tempParams.length() > 4000) {
				tempParams = tempParams.substring(0, 4000);
			}
			glAuditPo.setReqParams(tempParams);
			glAuditPo.setType("error");
			glAuditPo.setDetail(e.toString());
			
			//是否记录会员激活信息推送
			if(reacDetailPo != null){
				reacDetailPo.setPushStatus("failure");
			}
		}
		
		//更新数据
		genGlAudit(vo.getBizCode(),glAuditPo);
		if(reacDetailPo != null){
			genReacDetailAndMember(reacDetailPo);
		}
	}
	
	//构造系统审计日志
	private void genGlAudit(String bizCode,GlAuditPo glAuditPo){
		glAuditPo.setId(idGenerator.genSid());
		glAuditPo.setExecTime(new Date());
		glAuditPo.setExecMethod("SmsValidCodeHandler.execute");
		glAuditPo.setName(bizCode);
		GlAudit glAudit = glAuditRepository.newInstance(glAuditPo);
		glAudit.create();
	}
	
	//构造记录会员激活信息,及修改user表的推送时间
	private void genReacDetailAndMember(ReacDetailPo reacDetailPo){
		//激活分析明细表
		reacDetailRepository.newInstance(reacDetailPo).create();
		
		//user表
		Member member = memberRepository.newInstance();
		MemberPo memberPo = memberRepository.findByPartId(reacDetailPo.getMeId());
		memberPo.setTermlySms(new Date());
		member.setData(memberPo);
		member.update();
	}
	
}
*/