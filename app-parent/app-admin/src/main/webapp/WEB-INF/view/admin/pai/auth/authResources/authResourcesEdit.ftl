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
		bindFormValidation("authResourcesEditForm","${CtxPath}/admin/pai/auth/authResources/save.do");								
		$("#authResourcesEditForm").ligerForm();           
	});
	var __ctxPath = "${CtxPath}";	   
    </script>
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/auth/authResources.js" ></script>
</head>

<body style="padding:10px">	
    <form id="authResourcesEditForm" name="authResourcesEditForm" method="post">
    <#if authResourcesPo.id?exists>
    	<input type="hidden" name="pk" value="${authResourcesPo.id}" ltype="text"/>
    </#if>    	
    <#if authResourcesPo.createTime?exists>
    	<input type="hidden" name="createTime" value="${authResourcesPo.createTime?string('yyyy-MM-dd HH:mm:ss')}" ltype="text"/>
    </#if>
    <#if authResourcesPo.createBy?exists>
    	<input type="hidden" name="createBy" value="${authResourcesPo.createBy}" ltype="text"/>
    </#if>
    	<div>&nbsp;</div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
            <tr>
                <td align="right" class="l-table-edit-td">名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="name" type="text" id="name"  value="${authResourcesPo.name}" ltype="text" validate='{required:true}'/>
                </td>
                <td align="left"></td>
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td">资源类型（1=菜单；2=功能按钮）:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="type" type="text" id="type"  value="${authResourcesPo.type}" ltype="text" validate='{required:true,digits:true}'/>
                </td>
                <td align="left"></td>
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td">资源链接:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="url" type="text" id="url"  value="${authResourcesPo.url}" ltype="text" validate='{required:true}'/>
                </td>
                <td align="left"></td>
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td">父资源Id:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="parentId" type="text" id="parentId"  value="${authResourcesPo.parentId}" ltype="text" />
                </td>
                <td align="left"></td>
            </tr>    
            <!-- <tr>
                <td align="right" class="l-table-edit-td">树路劲:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="path" type="text" id="path"  value="${authResourcesPo.path}" ltype="text" />
                </td>
                <td align="left"></td>
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td">层次:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="depath" type="text" id="depath"  value="${authResourcesPo.depath}" ltype="text" validate='{digits:true}'/>
                </td>
                <td align="left"></td>
            </tr> -->   
            <tr>
                <td align="right" class="l-table-edit-td">图标:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="icon" type="text" id="icon"  value="${authResourcesPo.icon}" ltype="text" />
                </td>
                <td align="left"></td>
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td">排序:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="sort" type="text" id="sort"  value="${authResourcesPo.sort}" ltype="text" validate='{required:true,digits:true}'/>
                </td>
                <td align="left"></td>
            </tr>    
            <tr>
                <td align="right" class="l-table-edit-td">状态:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="status" type="text" id="status"  value="${authResourcesPo.status}" ltype="text" validate='{required:true,digits:true}'/>
                </td>
                <td align="left"></td>
            </tr>    
        </table>
 		<br />
		<input type="submit" value="提交" id="Button1" class="l-button l-button-submit" /> 
    </form>   
</body>
</html>
