package com.pai.app.admin.auth.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONStringer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pai.base.api.model.Page;
import com.pai.biz.frame.repository.IRepository;
import com.pai.base.core.constants.ActionMsgCode;
import com.pai.base.core.entity.CommonResult;
import com.pai.app.web.core.framework.util.PageUtil;
import com.pai.app.web.core.framework.web.context.OuOnlineHolder;
import com.pai.app.web.core.framework.web.controller.AdminController;
import com.pai.app.web.core.framework.web.entity.QueryBuilder;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.image.utils.RequestUtil;
import com.pai.biz.auth.domain.AuthResources;
import com.pai.biz.auth.repository.AuthResourcesRepository;
import com.pai.biz.auth.persistence.entity.AuthResourcesPo;
import com.pai.biz.auth.persistence.entity.AuthUserPo;

/**
 * 对象功能:资源 控制类
 * 开发公司:PAI.COM
 * 开发人员:FUHAO
 * 创建时间:2016-09-16 18:25:09
 */
@Controller
@RequestMapping("/admin/pai/auth/authResources")
public class AuthResourcesController extends AdminController<String, AuthResourcesPo, AuthResources>{
	 
	@Resource
	private AuthResourcesRepository authResourcesRepository;
	
	@Override
	protected IRepository<String, AuthResourcesPo, AuthResources> getRepository() {
		return authResourcesRepository;
	}

	@Override
	protected String getPoEntityComment() {		
		return "资源";
	}

	/**
	 * 查询【资源】列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("listData")	
	@ResponseBody
	public String listData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//构造分页对象
		QueryBuilder queryBuilder = new QueryBuilder(request);
		Page page = PageUtil.buildPage(request);
		//查询资源列表
		List<AuthResourcesPo> authResourcesPoList = getRepository().findPaged(queryBuilder.buildMap(),page);
		//查询总数
		Integer totalRecords = getRepository().count(queryBuilder.buildWhereSqlMap());
		//构造返回数据
		String listData = buildListData(authResourcesPoList,totalRecords);
		
		return listData;
	}
	
	/**
	 * 封装菜单json资源
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listResources")	
	@ResponseBody
	public String listResources(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AuthUserPo authUserPo=OuOnlineHolder.getUserPo();
		if(authUserPo==null){
			return "";
		}
		//查询资源列表
		List<AuthResourcesPo> authResourcesPoList = authResourcesRepository.listResourcesByUserId(authUserPo.getId());
		//拼装ligerUI返回数据
		JSONStringer stringer = new JSONStringer();
		stringer.array();
		for(AuthResourcesPo authResourcesPo:authResourcesPoList){
			append(request, stringer, authResourcesPo);
		}
		stringer.endArray();				
		return stringer.toString();
	}
	
	private void append(HttpServletRequest request, JSONStringer stringer, AuthResourcesPo authResourcesPo) {
		if(StringUtils.isEmpty(authResourcesPo.getUrl()))
			appendMenu(request, stringer, authResourcesPo);
		else 
			appendItem(request, stringer, authResourcesPo);
	}

	private void appendMenu(HttpServletRequest request, JSONStringer stringer, AuthResourcesPo authResourcesPo) {
		stringer.object();
		stringer.key("text");
		stringer.value(authResourcesPo.getName());
		stringer.key("isexpand");
		stringer.value("false");
		stringer.key("children");
		stringer.array();
		for(AuthResourcesPo sub:authResourcesPo.getSubs()){			
			append(request, stringer, sub);
		}
		stringer.endArray();
		stringer.endObject();
	}

	private void appendItem(HttpServletRequest request, JSONStringer stringer, AuthResourcesPo authResourcesPo) {
		stringer.object();
		stringer.key("url");
		stringer.value(request.getContextPath() + authResourcesPo.getUrl());
		stringer.key("text");
		stringer.value(authResourcesPo.getName());
		stringer.key("tabid");
		stringer.value(authResourcesPo.getId()+"TabId");
		stringer.endObject();
	}
	
	/**
	 * 进入【资源】编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取主键
		String id = RequestUtil.getParameterNullSafe(request, "id");
		
		//装载领域对象
		//是否新增
		boolean isNew =StringUtils.isEmpty(id)?true:false; 
		AuthResources authResources = null;
		if(isNew){
			authResources = authResourcesRepository.newInstance();
		}else{
			authResources = authResourcesRepository.load(id);			
		}		
		
		//根据新增或更新，进行若干业务处理
		if(isNew){
			//TODO
		}		
		
		//构造返回对象和视图
		ModelAndView modelAndView = buildAutoView(request);
		modelAndView.addObject("isNew",isNew);
		modelAndView.addObject(poEntityName, authResources.getData());
		
		//返回
		return modelAndView;		
	}	
	
	/**
	 * 保存【资源】
	 * @param request
	 * @param response
	 * @param authResourcesPo
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */	
	@RequestMapping("save")
	@ResponseBody
	public CommonResult save(HttpServletRequest request,HttpServletResponse response,AuthResourcesPo authResourcesPo) throws Exception{
		//是否新增
		boolean isNew = StringUtils.isEmpty(authResourcesPo.getId())?true:false;
		
		//构造领域对象和保存数据
		AuthResources authResources = authResourcesRepository.newInstance();
		authResources.setData(authResourcesPo);
		authResources.save();
		
		//构造返回数据
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		if(isNew){
			result.setMsgCode(ActionMsgCode.CREATE.name());	
		}else {
			result.setMsgCode(ActionMsgCode.UPDATE.name());
		}			
		
		//返回
		return result;
	}		
	
	/**
	 * 删除【资源】
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception 
	 * CommonResult
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("delete")
	@ResponseBody
	public CommonResult delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获得待删除的id
		String id = RequestUtil.getParameterNullSafe(request, "id");
		
		//构造领域对象和进行删除操作
		AuthResources authResources = authResourcesRepository.newInstance();				
		authResources.destroy(id);
		
		//构造返回数据
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		result.setMsgCode(ActionMsgCode.DELETE.name());		
		
		//返回
		return result;
	}

}
