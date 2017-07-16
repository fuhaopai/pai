<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>名人堂，为匿名用户服务管理</title>
	<#include "/WEB-INF/view/common/jquery.ftl">
	<#include "/WEB-INF/view/common/ligerUI.ftl">		 
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/common.js" ></script>					    					    
    <script type="text/javascript">
    	var grid = null;
        var searchForm = null;
        $(function ()
        {
        	searchForm = $("#form1").ligerForm({
				inputWidth : 180, labelWidth : 90, space : 50, rightToken :'',
				fields : [
					{ display: '名字:', name: 'Q__S__EQ__name', newline : true, align: 'left', width: 140 },
					{ display: '帮派:', name: 'Q__S__EQ__organization', newline : false, align: 'left', width: 140 },
					{ display: '职业:', name: 'Q__S__EQ__vocation', newline : false, align: 'left', width: 140 },
					{ display: '故事来源:', name: 'Q__S__EQ__source', newline : false, align: 'left', width: 140 },
					{ display: '创建时间:', name: 'Q__S__EQ__create_time', newline : false, align: 'left', width: 140 },
					{ display: 'aliasSortName', name: 'aliasSortName',type:'hidden'},	
		          	{ display: "<input type='button' value='查询' class='l-button' onClick='javascript:fnListSearch();' style='width:50px;'>", name: "searchButton", newline: false, width:0.01}
				 ]
			 });        
        	
            grid = $("#maingrid").ligerGrid({
                height:'100%',
                
                onChangeSort: function (sortname, sortorder) {
	    			  $("input[name='aliasSortName']").val(sortname);
		              fnListSearch();
		              return;
	             } ,
	             
                columns: [
					{ display: '名字', name: 'name', align: 'left', width: 80, minWidth: 60 },
					{ display: '帮派', name: 'organization', align: 'left', width: 80, minWidth: 60 },
					{ display: '职业', name: 'vocation', align: 'left', width: 80, minWidth: 60 },
					{ display: '性别', name: 'gender', align: 'left', width: 80, minWidth: 60 },
					{ display: '传记', name: 'story', align: 'left', width: 80, minWidth: 60 },
					{ display: '故事来源', name: 'source', align: 'left', width: 80, minWidth: 60 },
					{ display: '主要成就，相当于xx县委书记', name: 'achievement', align: 'left', width: 80, minWidth: 60 },
					{ display: '学历，相当于xx大学毕业 ', name: 'education', align: 'left', width: 80, minWidth: 60 },
					{ display: '出身，官二代，贫农，富二代', name: 'background', align: 'left', width: 80, minWidth: 60 },
					{ display: '创建人', name: 'createBy', align: 'left', width: 80, minWidth: 60 },
					{ display: '创建时间', name: 'createTime', align: 'left', type:'date', options:{showTime: true,format:'yyyy-MM-dd hh:mm:ss'}, width: 140, minWidth: 60 },
					{ display: '修改人', name: 'updateBy', align: 'left', width: 80, minWidth: 60 },
					{ display: '修改时间', name: 'updateTime', align: 'left', type:'date', options:{showTime: true,format:'yyyy-MM-dd hh:mm:ss'}, width: 140, minWidth: 60 }
                ], 
                url:'${CtxPath}/admin/pai/member/memberFame/listData.do', 
                pageSize:30 ,
                rownumbers:true,
                pagesizeParmName:'pageSize',
                onReload:setDataToGrid,
                onDblClickRow : function (rowdata,index,value){
                     editDialog(rowdata.id);
                },
                toolbar: { items: [
	                { id:'add',text: '增加', click: add, icon: 'add' },
	                { line: true },
	                { id:'modify',text: '修改', click: edit, icon: 'modify' },
	                { line: true },
	                { id:'delete',text: '删除', click: deleteRow, img: '${CtxPath}/scripts/ligerUI/skins/icons/delete.gif' },
	                { line: true },
	                { id:'modify',text: '刷新', click: refresh, icon: 'refresh' }                
              	  ]
                }
            });             

            $("#pageloading").hide();
        });
        
        var __ctxPath = "${CtxPath}";
        function fnListSearch(){
			if(grid!=null){	            
				grid.setOptions({newPage:1});
				setDataToGrid();				
				grid.loadData();
			}
		}
		function setDataToGrid(){		
			var data = searchForm.getData();
			if(grid!=null){
				grid.set("parms",[]);
				for(var param in data){					
					$("input[name='"+param+"']").each(function() {
						var id = $(this).attr("id");						
						var paramValue = $("#" + id).val();					
                    	grid.get("parms").push({ name: param, value: paramValue });
     				});					
				}
			}			
		}
    </script>
    <script type="text/javascript" src="${CtxPath}/scripts/admin/pai/member/memberFame.js" ></script>
</head>
<body style="overflow-x:hidden; padding:2px;">
	<div class="l-loading" style="display:block" id="pageloading"></div>
 		<a class="l-button" style="width:120px;float:left; margin-left:10px; display:none;" onclick="deleteRow()">删除选择的行</a> 
 		<div class="l-clear"></div>
 		<form id="form1"></form>
    	<div id="maingrid"></div>
   		<div style="display:none;">
	</div> 
</body>
</html>