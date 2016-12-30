<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户管理</title>
	<#include "/WEB-INF/view/common/jquery.ftl">
	<#include "/WEB-INF/view/common/ligerUI.ftl">		 
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/common.js" ></script>					    					    
    <script type="text/javascript">
    	var grid = null;
        var searchForm = null;
        $(function ()
        {
        	searchForm = $("#form1").ligerForm({
				inputWidth : 180,labelWidth : 90,space : 50,rightToken :'',
				fields : [
					{ display: '&nbsp;昵称', name: 'Q__S__EQ__name', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;手机号', name: 'Q__S__EQ__phone', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;密码', name: 'Q__S__EQ__password', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;邮箱', name: 'Q__S__EQ__mail', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;头像', name: 'Q__S__EQ__profile', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;专业', name: 'Q__S__EQ__profession', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;属性', name: 'Q__S__EQ__type', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;状态', name: 'Q__S__EQ__status', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: '&nbsp;是否第三方登陆账号', name: 'Q__S__EQ__isThird', newline : false, align: 'left', width: 140, minWidth: 60 },
					{ display: 'aliasSortName', name: 'aliasSortName',type:'hidden'},
					{display: "<input type='button' value='查询' class='l-button' onClick='javascript:fnListSearch();' style='width:50px;'>",name: "searchButton", newline: false, width:0.01}
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
					{ display: '昵称', name: 'name', align: 'left', width: 120, minWidth: 60 },
					{ display: '手机号', name: 'phone', align: 'left', width: 150, minWidth: 60 },
					{ display: '邮箱', name: 'mail', align: 'left', width: 150, minWidth: 60 },
					{ display: '头像', name: 'profile', align: 'left', width: 80, minWidth: 60, render:function(rowdata,index,value){
					        if(value != null && value != ""){
					        	return '<img src="'+value+'@!80x80">';
					        }else{
					       		return '<font color="#00c">默认图</font>';
					        }
					    }
				  	},
					{ display: '专业', name: 'profession', align: 'left', width: 100, minWidth: 60 },
					{ display: '属性', name: 'type', align: 'left', width: 80, minWidth: 60, render:function(rowdata,index,value){
							if(value == 0)
								return "前端用户";
							else if(value == 1)
								return "后端用户";
							else 
								return "前后端用户";
						} 
					},
					{ display: '状态', name: 'status', align: 'left', width: 80, minWidth: 60, render:function(rowdata,index,value){
							if(value == 1)
								return "正常";
							else if(value == 2)
								return "冻结";
						} 
					},
					{ display: '是否第三方登陆账号', name: 'isThird', align: 'left', width: 120, minWidth: 60, render:function(rowdata,index,value){
							if(value == 'Y')
								return "是";
							else if(value == 'N')
								return "否";
						} 
					}
              	  ], 
              	url:'${CtxPath}/admin/pai/auth/authUser/listData.do', 
              	pageSize:30 ,
              	rownumbers:true,
              	pagesizeParmName:'pageSize',
              	rowHeight:80,
                onReload:setDataToGrid,
                /*onDblClickRow : function (rowdata,index,value){
                     editDialog(rowdata.id);
                },*/
                toolbar: { items: [
	                { id:'add',text: '增加', click: add, icon: 'add' },
	                { line: true },
	                /*{ id:'modify',text: '修改', click: edit, icon: 'modify' },
	                { line: true },*/
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
    <script type="text/javascript" src="${CtxPath}/scripts/admin/pai/auth/authUser.js" ></script>
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