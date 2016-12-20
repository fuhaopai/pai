package com.pai.app.admin.auth.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.pai.app.web.core.constants.MsgCode;
import com.pai.app.web.core.constants.UrlConstants;
import com.pai.app.web.core.constants.WebConstants;
import com.pai.app.web.core.framework.web.context.OuOnlineHolder;
import com.pai.app.web.core.framework.web.controller.LigerUIController;
import com.pai.base.core.entity.CommonResult;
import com.pai.base.core.helper.SpringHelper;
import com.pai.base.core.util.string.StringUtils;
import com.pai.biz.auth.event.LoginEvent;
import com.pai.biz.auth.persistence.entity.AuthResourcesPo;
import com.pai.biz.auth.persistence.entity.AuthUserPo;
import com.pai.biz.auth.persistence.entity.LoginInfo;
import com.pai.biz.auth.repository.AuthResourcesRepository;
import com.pai.biz.auth.repository.AuthUserRepository;

/**
 * 后台用户登录，及登录后的操作
 * <pre> 
 * 构建组：app-admin
 * 作者：fuhao
 * 日期：2016年11月4日-上午9:58:58
 * </pre>
 */
@Controller
@RequestMapping("/")
public class LoginController extends LigerUIController{

	private final static String LOGIN_INFO = "loginInfo";
	
	@Resource
	private ImageCaptchaService imageCaptchaService = null;
	
	@Resource
	AuthUserRepository authUserRepository = null;	
	@Resource
	AuthResourcesRepository authResourcesRepository = null;
	@Override
	protected void initController() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 登录查找权限
	 * @param request
	 * @param response
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping("adminLogin")
	public ModelAndView login(final HttpServletRequest request,HttpServletResponse response,LoginInfo loginInfo){
		ModelAndView modelAndView = new ModelAndView(UrlConstants.LOGIN_FTL);
		 if(loginInfo.isLogin()){	//是提交登录	
			CommonResult commonResult = null;
		 	//匹配验证码
			if(isCaptchaCorrect(request.getSession().getId(),loginInfo.getCaptchaCode())){
				//查询用户
				final AuthUserPo authUserPo = authUserRepository.getAccount(loginInfo.getUserName(), loginInfo.getEncryptPassword());
				if(authUserPo != null){
					//登录后发布一个事件，记录登录日志等操作，mq topic
					ExecutorService executorService = Executors.newCachedThreadPool();
					try {
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								SpringHelper.publishEvent(new LoginEvent(authUserPo, getIpAddr(request)));
							}
						});
					} finally {
						executorService.shutdown();
					}
					//查询资源列表,用于拦截器匹配请求权限
					List<AuthResourcesPo> authResourcesPoList = authResourcesRepository.listResourcesByUserId(authUserPo.getId());
					authUserPo.setAuthResourcesPos(authResourcesPoList);
					//放置session
					OuOnlineHolder.setUserPo(request.getSession(), authUserPo);
					//重定向到后台主页
					redirectUrl(response, request.getAttribute(WebConstants.CONTEXT_PATH)+UrlConstants.MAIN_URL); 
				}else{
					commonResult = new CommonResult();
					commonResult.setSuccess(false);
					commonResult.setMsgCode(MsgCode.LOGINERROR.getCode());
					commonResult.setMsg(MsgCode.LOGINERROR.getMsg());
				}
			}else{
				commonResult = new CommonResult();
				commonResult.setSuccess(false);
				commonResult.setMsgCode(MsgCode.CAPTCHA.getCode());
				commonResult.setMsg(MsgCode.CAPTCHA.getMsg());
			}				
			loginInfo.setCommonResult(commonResult);		
		 }	
		 
		 modelAndView.addObject(LOGIN_INFO, loginInfo);
		 return	modelAndView;
	}
	
	@RequestMapping("logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
//		OuOnlineHolder.logout();
		request.getSession().invalidate();
//		redirectUrl(response, request.getAttribute(WebConstants.CONTEXT_PATH) + UrlConstants.LOGIN_URL);
	}
	
	private boolean isCaptchaCorrect(String sessionId,String postCaptchaCode){
		boolean isCorrect = true;
		String captchaId = sessionId;		
		if (StringUtils.isNotEmpty(postCaptchaCode)) {
			try{
				isCorrect = imageCaptchaService.validateResponseForID(captchaId, postCaptchaCode.toLowerCase()).booleanValue();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			isCorrect = false;		
		}
		return isCorrect;
	}	
	
	public String getIpAddr(HttpServletRequest request) {  
		   String fromSource = "X-Real-IP";  
		    String ip = request.getHeader("X-Real-IP");  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("X-Forwarded-For");  
		        fromSource = "X-Forwarded-For";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		        fromSource = "Proxy-Client-IP";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		        fromSource = "WL-Proxy-Client-IP";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		        fromSource = "request.getRemoteAddr";  
		    }  
		    logger.info("App Client IP: "+ip+", fromSource: "+fromSource);  
		    return ip;  
	}  
}
