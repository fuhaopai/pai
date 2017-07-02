package com.pai.app.admin.member.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pai.base.api.model.Page;
import com.pai.biz.frame.repository.IRepository;
import com.pai.base.core.constants.ActionMsgCode;
import com.pai.base.core.entity.CommonResult;
import com.pai.app.web.core.framework.util.PageUtil;
import com.pai.app.web.core.framework.web.controller.AdminController;
import com.pai.app.web.core.framework.web.entity.QueryBuilder;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.image.utils.RequestUtil;
import com.pai.base.db.mybatis.impl.domain.PageList;
import com.pai.biz.member.domain.MemberUser;
import com.pai.biz.member.repository.MemberUserRepository;
import com.pai.biz.member.persistence.entity.MemberUserPo;

/**
 * 对象功能:会员表 控制类
 * 开发公司:π
 * 开发人员:FU_HAO
 * 创建时间:2017-07-02 18:04:24
 */
@Controller
@RequestMapping("/admin/pai/member/memberUser")
public class MemberUserController extends AdminController<String, MemberUserPo, MemberUser>{
	 
	@Resource
	private MemberUserRepository memberUserRepository;
	
	@Override
	protected IRepository<String, MemberUserPo, MemberUser> getRepository() {
		return memberUserRepository;
	}

	@Override
	protected String getPoEntityComment() {		
		return "会员表";
	}

	/**
	 * 查询【会员表】列表
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
		//查询会员表列表
		PageList<MemberUserPo> memberUserPoList = (PageList<MemberUserPo>) getRepository().findPaged(queryBuilder.buildMap(),page);
		//构造返回数据
		String listData = buildListData(memberUserPoList,memberUserPoList.getPageResult().getTotalCount());
		
		return listData;
	}
	
	/**
	 * 进入【会员表】编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response, @RequestParam String id) throws Exception{
		//装载领域对象
		//是否新增
		boolean isNew =StringUtils.isEmpty(id)?true:false; 
		MemberUser memberUser = null;
		if(isNew){
			memberUser = memberUserRepository.newInstance();
		}else{
			memberUser = memberUserRepository.load(id);			
		}		
		
		//根据新增或更新，进行若干业务处理
		if(isNew){
			//TODO
		}		
		
		//构造返回对象和视图
		ModelAndView modelAndView = buildAutoView(request);
		modelAndView.addObject("isNew",isNew);
		modelAndView.addObject(poEntityName, memberUser.getData());
		
		//返回
		return modelAndView;		
	}	
	
	/**
	 * 保存【会员表】
	 * @param request
	 * @param response
	 * @param memberUserPo
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */	
	@RequestMapping("save")
	@ResponseBody
	public CommonResult save(HttpServletRequest request,HttpServletResponse response,MemberUserPo memberUserPo) throws Exception{
		//是否新增
		boolean isNew = StringUtils.isEmpty(memberUserPo.getId())?true:false;
		
		//构造领域对象和保存数据
		MemberUser memberUser = memberUserRepository.newInstance();
		memberUser.setData(memberUserPo);
		memberUser.save();
		
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
	 * 删除【会员表】
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
		MemberUser memberUser = memberUserRepository.newInstance();				
		memberUser.destroy(id);
		
		//构造返回数据
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		result.setMsgCode(ActionMsgCode.DELETE.name());		
		
		//返回
		return result;
	}

}
