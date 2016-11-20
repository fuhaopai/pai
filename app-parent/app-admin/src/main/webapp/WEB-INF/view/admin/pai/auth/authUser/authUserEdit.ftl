<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title></title>
	<#include "/common/jquery.ftl">
	<#include "/common/ligerUI.ftl">		 			
	<link href="${CtxPath}/scripts/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${CtxPath}/styles/admin/pai/form.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/common.js" ></script>		    	            	    	
    <script type="text/javascript">  
	$(function(){		
		bindFormValidation("authUserEditForm","${CtxPath}/admin/pai/auth/authUser/save.do");								
		$("#authUserEditForm").ligerForm();           
	});
	var __ctxPath = "${CtxPath}";	   
    </script>
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/auth/authUser.js" ></script>
</head>

<body style="padding:10px">	
    <form id="authUserEditForm" name="authUserEditForm" method="post">
    <#if authUserPo.id?exists>
    	<input type="hidden" name="pk" value="${authUserPo.id}" ltype="text"/>
    </#if>    	
    <#if authUserPo.createTime?exists>
    	<input type="hidden" name="createTime" value="${authUserPo.createTime?string('yyyy-MM-dd HH:mm:ss')}" ltype="text"/>
    </#if>
    <#if authUserPo.createBy?exists>
    	<input type="hidden" name="createBy" value="${authUserPo.createBy}" ltype="text"/>
    </#if>
    	<div>&nbsp;</div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
		        <tr>
		            <td align="right" class="l-table-edit-td">用户名:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="name" type="text" id="name"  value="${authUserPo.name}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">手机号:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="phone" type="text" id="phone"  value="${authUserPo.phone}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">密码:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="password" type="text" id="password"  value="${authUserPo.password}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">邮箱:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="mail" type="text" id="mail"  value="${authUserPo.mail}" ltype="text" />
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">头像:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="profile" type="text" id="profile"  value="${authUserPo.profile}" ltype="text" />
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">专业:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="profession" type="text" id="profession"  value="${authUserPo.profession}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">属性（0=前端用户；1=后端用户；2：前后端用户）:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="type" type="text" id="type"  value="${authUserPo.type}" ltype="text" validate='{required:true,digits:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">状态（1=正常；2=冻结）:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="status" type="text" id="status"  value="${authUserPo.status}" ltype="text" validate='{required:true,digits:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">是否第三方登陆账号:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="isThird" type="text" id="isThird"  value="${authUserPo.isThird}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
        </table>
 		<br />
		<input type="submit" value="提交" id="Button1" class="l-button l-button-submit" /> 
    </form>   
</body>
</html>
