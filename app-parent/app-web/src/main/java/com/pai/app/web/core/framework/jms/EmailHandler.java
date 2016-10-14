/*package com.pai.app.web.core.framework.jms;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skg.base.api.constants.Constants;
import com.skg.base.api.constants.MsgType;
import com.skg.base.api.helper.IConfigHelper;
import com.skg.base.api.model.IMsgVo;
import com.skg.base.core.helper.SpringHelper;
import com.skg.biz.ou.domain.Member;
import com.skg.biz.ou.persistence.entity.MemberPo;
import com.skg.biz.ou.persistence.entity.ReacDetailPo;
import com.skg.biz.ou.repository.MemberRepository;
import com.skg.biz.ou.repository.ReacDetailRepository;
import com.skg.service.mail.MailUtil;
import com.skg.service.mail.entity.MailVo;
import com.skg.service.mq.jms.JmsHandler;


@Service
public class EmailHandler implements JmsHandler<IMsgVo>{
	
	@Resource
	private ReacDetailRepository reacDetailRepository;
	
	@Resource 
	private MemberRepository memberRepository;
	
	private Logger logger = LoggerFactory.getLogger(EmailHandler.class);
	
	public String getMsgType() {
		return MsgType.EMAIL.key();
	}

	public void execute(IMsgVo vo) {
		if(vo instanceof MailVo){
			MailVo mailVo = (MailVo)vo;
			
			//如果有参数才会更新会员激活明细表(现在只用在会员优惠券激活提醒)
			Boolean isReac = false;
			Map<String, Object> parmasMap = mailVo.getParamsMap();
			ReacDetailPo reacDetailPo = null;
			if(null != parmasMap &&  parmasMap.size() > 0 && parmasMap.containsKey("reacDetailPo")){
				reacDetailPo = (ReacDetailPo) parmasMap.get("reacDetailPo");
				isReac = true;
			}
			
			try {
				if(isReac && parmasMap.containsKey("isDebug")){
					MailUtil.setDebugMode("Y".equals(parmasMap.get("isDebug"))?true:false);
				}else {
					IConfigHelper configHelper = SpringHelper.getBean(IConfigHelper.class);
					boolean isDebug = configHelper.getBool(Constants.MAIL.IS_DEBUG);
					MailUtil.setDebugMode(isDebug);
				}
				
				if(mailVo.getPreDelayMS()>0L){
					Thread.sleep(mailVo.getPreDelayMS());
				}
				MailUtil.send(mailVo);
				if(mailVo.getPostDelayMS()>0L){
					Thread.sleep(mailVo.getPostDelayMS());
				}
				if(isReac){
					reacDetailPo.setPushStatus("success");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				if(isReac){
					reacDetailPo.setPushStatus("failure");
				}
			}
			
			if(isReac){
				reacDetailRepository.newInstance(reacDetailPo).create();
				
				Member member = memberRepository.newInstance();
				MemberPo memberPo = memberRepository.findByPartId(reacDetailPo.getMeId());
				memberPo.setTermlyEmail(new Date());
				member.setData(memberPo);
				member.update();
			}
		}
	}

}
*/