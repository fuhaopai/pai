package com.pai.app.admin.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pai.app.web.core.constants.UrlConstants;
import com.pai.app.web.core.constants.WebConstants;
import com.pai.base.api.constants.Constants;

public class SecurityInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {	
		//单元测试环境
		if(request instanceof MockHttpServletRequest){
			return true;
		}
		
		/*OuOnlineHolder.setSession(request.getSession());				
		
		if(OuOnlineHolder.getUserId()==null){	//未登录
			redirectToLogin(request,response);
		}
		//权限过滤
		UserPo userPo = OuOnlineHolder.getUserPo();
		String uri = request.getRequestURI();
		boolean pass = false;
		uri = uri.substring(0, uri.lastIndexOf("/"));
		for(ResPo resPo : userPo.getResPos()){
			if(!"menu".equals(resPo.getType()) && StringUtils.isNotBlank(resPo.getContent())){
				String resContent = resPo.getContent().substring(0, resPo.getContent().lastIndexOf("/"));
				if(uri.indexOf(resContent) != -1){
					pass = true;
					return true;
				}
			}
		}
		if(!pass)
			throw new RuntimeException("无此功能权限！");*/
		
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void redirectToLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String url = request.getAttribute(WebConstants.CONTEXT_PATH) + UrlConstants.LOGIN_URL;
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.sendRedirect(url);		
	}
}
