/*package com.pai.app.web.core.framework.web.controller;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.servlet.ModelAndView;

import com.pai.app.web.core.framework.util.HttpClientUtil;
import com.pai.base.core.encrypt.DESEncrypt;
import com.pai.base.core.helper.SpringHelper;
import com.pai.base.core.util.EncryptUtil;
import com.pai.base.core.util.JsonUtil;
import com.pai.base.core.util.ProxyUtil;
import com.pai.base.core.util.string.StringUtils;
import com.pai.base.core.util.ConfigHelper;

public abstract class FrontGenericController extends GenericController {

	private HttpClientUtil httpClientUtil = SpringHelper.getBean(HttpClientUtil.class);
	SaleEntityAPIHelper saleEntityAPIHelper = SpringHelper.getBean(SaleEntityAPIHelper.class);
	protected String REGISTER_AD = "register_ad";
	protected final static String CATE_PRODUCT = "AppliancesCate";
	protected final static String CALL_TYPE_WEB = "web";
	protected final static String CALL_TYPE_WAP = "wap";
	protected final static String PIC_TYPE_BIG = "big";
	protected final static String PIC_TYPE_MEDIUM = "medium";
	protected final static String PIC_TYPE_SMALL = "small";
	private final static String PUBLIC_ADB = "public_ad";
	private final static String SUBAPPLIANCESCATE = "SubAppliancesCate";
	AdEntityAPIHelper adEntityAPIHelper = SpringHelper.getBean(AdEntityAPIHelper.class);
	GlCateAPIHelper glCateAPIHelper = SpringHelper.getBean(GlCateAPIHelper.class);

	public List<NameValuePair> buildNameValuePairs(Map<String, Object> map) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			params.add(new BasicNameValuePair(key, map.get(key) == null ? "" : map.get(key).toString()));
		}
		return params;
	}

	public HttpClientUtil getHttpClientUtil() {
		if (httpClientUtil == null) {
			httpClientUtil = SpringHelper.getBean(HttpClientUtil.class);
		}
		return httpClientUtil;
	}

	public void saveFromTrack(String sys, String type, String content) {
		FromTrackPo fromTrackPo = OuOnlineHolder.getFromTrackPoSession();
		if (fromTrackPo != null) {
			fromTrackPo.setId(null);
			fromTrackPo.setSys(sys);
			fromTrackPo.setType(type);
			fromTrackPo.setContent(content);
			fromTrackPo.setCreateTime(new Date());
			FromTrackEvent fromTrackEvent = new FromTrackEvent(fromTrackPo);
			SpringHelper.publishEvent(fromTrackEvent);
		}
	}

	public ModelAndView setThridParams(ModelAndView modelAndView, String source) {
		// 组成微信登录请求地址
		ConfigHelper configHelper = ConfigHelper.getInstance();
		String appId = configHelper.getParamValue("WeiXin_PC_Login_AppID");
		String response_Type = configHelper.getParamValue("WeiXIn_PC_Login_Response_Type");
		String redirectUrl = configHelper.getParamValue("PC_Website") + "storefront/member/weixinLogin.htm";
		String scope = configHelper.getParamValue("WeiXIn_PC_Login_Scope");
		String state = source;
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("appid=" + appId);
		paramBuffer.append("&redirect_uri=" + URLEncoder.encode(redirectUrl));
		paramBuffer.append("&response_type=" + response_Type);
		paramBuffer.append("&scope=" + scope);
		if(StringUtils.isNotEmpty(state)){
			paramBuffer.append("&state=" + state);
		}
		String weixinPcLogin = Configure.Authorize_PC_API + "?" + paramBuffer.toString();
		logger.info("weixinLogin==" + weixinPcLogin);
		// 组成QQ登录请求地址
		String QQ_PC_Response_Type = configHelper.getParamValue("QQ_PC_Login_Response_Type");
		String QQ_PC_Client_Id = configHelper.getParamValue("QQ_PC_Login_Client_Id");
		String QQ_PC_Redirect_Uri = configHelper.getParamValue("PC_Website") + "storefront/member/qqLogin.htm";// 重定向地址，需要进行UrlEncode
		String QQ_PC_Scope = configHelper.getParamValue("QQ_PC_Login_Scope");
		StringBuffer paramBuffer2 = new StringBuffer();
		paramBuffer2.append("response_type=" + QQ_PC_Response_Type);
		paramBuffer2.append("&client_id=" + QQ_PC_Client_Id);
		paramBuffer2.append("&redirect_uri=" + URLEncoder.encode(QQ_PC_Redirect_Uri));
		paramBuffer2.append("&scope=" + QQ_PC_Scope);
		if(StringUtils.isNotEmpty(state)){
			paramBuffer2.append("&state=" + state);
		}
		
		String QqPcLogin = Configure.Authorize_PC_QQ_API + "?" + paramBuffer2.toString();
		modelAndView.addObject("QqPcLoginUrl", QqPcLogin);
		modelAndView.addObject("weixinPcLoginUrl", weixinPcLogin);
		modelAndView.addObject("website", configHelper.getParamValue("PC_Website"));
		modelAndView.addObject("weiboAppKey", configHelper.getParamValue("weibo.appKey"));
		AdEntityVo registerAdEntityVo = adEntityAPIHelper.getAdEntityVo(REGISTER_AD);
		modelAndView.addObject("registerAdEntityViewMap", registerAdEntityVo.getAdEntityViewMap());
		if (StringUtils.isNotEmpty(state)) {
			modelAndView.addObject("thridState", "&state=" + state);
		}
		return modelAndView;
	}

	public ModelAndView cookieLogin(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
		Cookie skgSessionIdCookie = new Cookie("skgSessionId", request.getSession().getId());
		skgSessionIdCookie.setSecure(false);
		skgSessionIdCookie.setPath("/");
		response.addCookie(skgSessionIdCookie);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("skgMember".equals(cookie.getName())) {
					try {
						String memberCooike = cookie.getValue();
						if (memberCooike.indexOf("\"") == 0
								&& memberCooike.lastIndexOf("\"") == memberCooike.length() - 1) {
							memberCooike = memberCooike.substring(1, memberCooike.length() - 1);
						}
						String[] member = EncryptUtil.aesDecrypt(memberCooike).split(":");
						if (member != null && member.length == 2) {
							String sid = member[0];
							String id = member[1];
							String result = ProxyUtil.httpRequest(ProxyUtil.getApiUrl()
									+ "/api/biz/ou/v1/members/action/checkLogin.htm?sid=" + sid, "GET", null);
							if (result != null) {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("memberView", MemberView.class);
								MemberAPIResult memberAPIResult = (MemberAPIResult) JsonUtil.getDTO(result,
										MemberAPIResult.class, map);
								if (memberAPIResult != null && memberAPIResult.getMemberView() != null
										&& memberAPIResult.getMemberView().getUserId().equals(id)
										&& "200".equals(memberAPIResult.getStatusCode())) {
									if (StringUtils.isNotEmpty(memberAPIResult.getMemberView().getCartId())) {
										CartEntityView cartEntityView = CartEntityHelper.getCartEntityView(
												memberAPIResult.getMemberView().getCartId(), null, CALL_TYPE_WEB,
												PIC_TYPE_SMALL);
										int qty = cartEntityView.getQty();
										memberAPIResult.setCartNumber(qty);
									} else {
										memberAPIResult.setCartNumber(0);
									}
									modelAndView.addObject("partyName", memberAPIResult.getMemberView().getPartyName());
									modelAndView.addObject("userId", memberAPIResult.getMemberView().getUserId());
									modelAndView.addObject("isNew", memberAPIResult.getMemberView().getIsNew());
									cookieLoginAfter(response, request.getSession(), memberAPIResult,
											CartEntityPo.Code.TYPE_COMMON);
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
		}
		return modelAndView;
	}

	private void cookieLoginAfter(HttpServletResponse response, HttpSession session, MemberAPIResult memberAPIResult,
			String type) {
		logger.info("memberAPIResult.getSid()=" + memberAPIResult.getSid());
		OuOnlineHolder.setMemberView(session, memberAPIResult.getSid(), memberAPIResult.getMemberView());
		OuOnlineHolder.setCartId(session, memberAPIResult.getCommonCartId(), CartEntityPo.Code.TYPE_COMMON);
		OuOnlineHolder.setCartId(session, memberAPIResult.getMemberView().getCartId(), type);
		OuOnlineHolder.setSid(memberAPIResult.getSid());
		String memberCooike = memberAPIResult.getSid() + ":" + memberAPIResult.getMemberView().getUserId();
		try {
			memberCooike = EncryptUtil.aesEncrypt(memberCooike);
			Cookie cookieSkgMember = new Cookie("skgMember", memberCooike);
			cookieSkgMember.setMaxAge(7 * 24 * 3600);
			cookieSkgMember.setSecure(false);
			cookieSkgMember.setDomain(".skg.com");
			cookieSkgMember.setPath("/");
			response.addCookie(cookieSkgMember);
			Cookie cookie = new Cookie("skgSessionId", memberAPIResult.getMemberView().getUserId());
			cookie.setSecure(false);
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ModelAndView getPublicAd(ModelAndView modelAndView, String isIndex) {
		// AdEntityVo adEntityVo = adEntityAPIHelper.getAdEntityVo(PUBLIC_ADB);
		// modelAndView.addObject("publicAdEntityViewMap", adEntityVo.getAdEntityViewMap());
		List<GlCateFrontView> glCateFrontViews = glCateAPIHelper.findGlCateFrontViews(CATE_PRODUCT, "", true);
		modelAndView.addObject("glCateFrontViews", glCateFrontViews);

		// 分类列表
		List<GlCateFrontView> navGlCateFrontViews = glCateAPIHelper.findGlCateFrontViews(SUBAPPLIANCESCATE, "", true);
		// 遍历分类，查询该分类下的热门商品
		Map<String, List<SaleEntityBriefView>> cateNewMap = new HashMap<String, List<SaleEntityBriefView>>();
		for (GlCateFrontView glCateFrontView : navGlCateFrontViews) {
			// 查询每个一级分类的热门商品
			List<SaleEntityBriefView> hotCateBriefViews = saleEntityAPIHelper.findSaleEntityBriefViews("", "",
					glCateFrontView.getKey(), "", "", "Y", SaleEntityAPIHelper.OD_SALEQTY, 1, 5, "cateExType", null,
					null);
			List<SaleEntityBriefView> hotViews = new ArrayList<SaleEntityBriefView>();
			for (SaleEntityBriefView saleEntityBriefView : hotCateBriefViews) {
				if (!StringUtils.isEmpty(saleEntityBriefView.getMainCloudPath())) {
					hotViews.add(saleEntityBriefView);
				}
			}
			cateNewMap.put(glCateFrontView.getKey(), hotViews);
		}
		modelAndView.addObject("navGlCateFrontViews", navGlCateFrontViews);
		modelAndView.addObject("cateNewMap", cateNewMap);
		if ("Y".equals(isIndex)) {
			modelAndView.addObject("isIndex", isIndex);
		}

		// 添加会员基本信息
		MemberView memberView = OuOnlineHolder.getMemberView();
		modelAndView.addObject("memberView", memberView);
		return modelAndView;
	}

	public ModelAndView getPublicAd(ModelAndView modelAndView) {
		return getPublicAd(modelAndView, null);
	}

	private static final String DEFAULT_DES_KEY = "skg=@#&%";

	public void printResultByDESEncrypt(HttpServletResponse response, Serializable entity, String key) {
		try {
			final String jsonString = JsonUtil.getJSONString(entity);
			final String encryptResultString = DESEncrypt.encrypt(jsonString, DEFAULT_DES_KEY);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(encryptResultString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void printResultByDESEncrypt(HttpServletResponse response, Serializable entity) {
		printResultByDESEncrypt(response, entity, DEFAULT_DES_KEY);
	}

}
*/