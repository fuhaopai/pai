package com.pai.app.admin.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pai.app.web.core.constants.UrlConstants;
import com.pai.app.web.core.constants.WebConstants;
import com.pai.app.web.core.framework.web.context.OuOnlineHolder;
import com.pai.base.api.constants.Constants;
import com.pai.base.core.util.string.StringUtils;
import com.pai.biz.auth.persistence.entity.AuthResourcesPo;
import com.pai.biz.auth.persistence.entity.AuthUserPo;

public class SecurityInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {	
		//单元测试环境
		if(request instanceof MockHttpServletRequest){
			return true;
		}
		
		OuOnlineHolder.setSession(request.getSession());				
		
		//登录过滤
		AuthUserPo authUserPo = OuOnlineHolder.getUserPo();
		if(authUserPo == null){
			redirectToLogin(request,response);
			return true;
		}
		
		//请求权限过滤
		String uri = request.getRequestURI();
		boolean pass = false;
		List<AuthResourcesPo> authResourcesPos = authUserPo.getAuthResourcesPos();
		if(!resourceFilter(uri, pass, authResourcesPos))
			throw new RuntimeException("无此功能权限！");
		
		return true;
	}

	private boolean resourceFilter(String uri, boolean pass,
			List<AuthResourcesPo> authResourcesPos) {
		for(AuthResourcesPo authResourcesPo : authResourcesPos){
			if(StringUtils.isNotEmpty(authResourcesPo.getUrl()) && uri.indexOf(authResourcesPo.getUrl()) != -1){
				pass = true;
				break;
			}
			if(authResourcesPo.getSubs().size() > 0){
				resourceFilter(uri, pass, authResourcesPo.getSubs());
			}
		}
		return pass;
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
