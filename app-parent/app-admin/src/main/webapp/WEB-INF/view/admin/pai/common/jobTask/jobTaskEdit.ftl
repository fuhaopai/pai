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
		bindFormValidation("jobTaskEditForm","${CtxPath}/admin/pai/common/jobTask/save.do");								
		$("#jobTaskEditForm").ligerForm();           
	});
	var __ctxPath = "${CtxPath}";	   
    </script>
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/common/jobTask.js" ></script>
</head>

<body style="padding:10px">	
    <form id="jobTaskEditForm" name="jobTaskEditForm" method="post">
    <#if jobTaskPo.id?exists>
    	<input type="hidden" name="pk" value="${jobTaskPo.id}" ltype="text"/>
    </#if>    	
    <#if jobTaskPo.createTime?exists>
    	<input type="hidden" name="createTime" value="${jobTaskPo.createTime?string('yyyy-MM-dd HH:mm:ss')}" ltype="text"/>
    </#if>
    <#if jobTaskPo.createBy?exists>
    	<input type="hidden" name="createBy" value="${jobTaskPo.createBy}" ltype="text"/>
    </#if>
    	<div>&nbsp;</div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit">
		        <tr>
		            <td align="right" class="l-table-edit-td">名称:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="name" type="text" id="name"  value="${jobTaskPo.name}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">描述:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="description" type="text" id="description"  value="${jobTaskPo.description}" ltype="text" />
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">类名:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="bean" type="text" id="bean"  value="${jobTaskPo.bean}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">所属组:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="groupName" type="text" id="groupName"  value="${jobTaskPo.groupName}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">类型（one_time=执行一次；expression=按表达式执行）:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="type" type="text" id="type"  value="${jobTaskPo.type}" ltype="text" validate='{required:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td">状态（1=运行中；2=停止中）:</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="status" type="text" id="status"  value="${jobTaskPo.status}" ltype="text" validate='{required:true,digits:true}'/>
		            </td>
		            <td align="left"></td>
		        </tr> 
        </table>
 		<br />
		<input type="submit" value="提交" id="Button1" class="l-button l-button-submit" /> 
    </form>   
</body>
</html>
