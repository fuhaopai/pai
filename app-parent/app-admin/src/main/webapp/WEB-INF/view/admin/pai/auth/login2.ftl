	<#include "/common/jquery.ftl">
	<#include "/common/ligerUI.ftl">	 					    
	<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/common.js" ></script>
		            	    	
    <script type="text/javascript">  
	if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
		top.location='${CtxPath}/adminLogin.do';
	}
	$(document).ready(function() {   
		bindFormValidation("formLogin","${CtxPath}/adminLogin.do",true);								
		$("#formLogin").ligerForm();           
	});   
    </script>
	<form id="formLogin" method="post">		
		<div class="top_login">
			<span class="htlogo_login l"></span>
			<span class="r">PAI平台管理后台&nbsp;&nbsp;&nbsp;</span>
		</div>
		<center>
			<div class="main_login">
				<div class="content_login">       				
					<div class="column">
						<span>用户名:</span><br />
						<input type="text" id="userName" name="userName" ltype="text" tabindex="1" value="" validate="{required:true}" /><br>
						<input type="hidden" id="isLogin" name="isLogin" value="Y"/>	
					</div>
					<div class="column">
						<span>密&nbsp;&nbsp;码:</span><br />
						<input type="password" name="password"  ltype="text" tabindex="2" value="" validate="{required:true}"/>
					</div>	
					<div class="vcode column">	
							<div>					
							<span>验证码:</span><br />						
							<input type="text" id="captchaCode" name="captchaCode"  tabindex="3" ltype="text" validate="{required:true}"/>
							<img id="imgCaptchaCode" name="imgCaptchaCode" src="${CtxPath}/generateImage.do" border="0" 
									onclick="refreshImage('imgCaptchaCode');" height="50" alt="换一张" title="换一张" style="margin-left:195px;margin-top:-30px;"/>&nbsp;														
							</div>                       					
					</div>				
					<div class="confirm">
						<input type="checkbox" name="_spring_security_remember_me" value="1"/><span>系统记住我</span>
						<input type="submit" value="登录" id="loginSubmit" class="l-button l-button-submit" /> 					
					</div>
					<#if loginInfo?exists>
						<#assign msgCode="${loginInfo.commonResult.msgCode}"/>
						<#if msgCode?exists && msgCode?length gt 0>
						<div class="confirm"><img src="${CtxPath}/styles/login/images/icon_warning.gif"/>&nbsp;&nbsp;<span style="color:#ff0000;">					
							<#if msgCode=="CAPTCHA">
								验证码有误
							<#elseif msgCode=="ACCOUNT_EMAIL_OR_PW_ERROR">
								账号、邮箱或密码有误
							</#if>							
						</span></div>
						</#if>
					</#if>
				</div>
			</div>
			<div class="copy">&copy;版权所有 π.com</div>
		</center>		
	</form>
	<script type="text/javascript">
		$(document).ready(function(){		
	  		$("#userName").focus();  		  	
		});
		
		function refreshImage(imageId){
			var image=document.getElementById(imageId);
			var url="${CtxPath}/generateImage.do";
			var xmlHttpReq=null;
			if(window.ActiveXObject){
				xmlHttpReq=new ActiveXObject("Microsoft.XMLHTTP");
			}else if(window.XMLHttpRequest){
				xmlHttpReq = new XMLHttpRequest();
			}
			xmlHttpReq.open("Post", url, true);
			xmlHttpReq.send(null); 
			
			image.src=url + "?rand="+parseInt(1000*Math.random());
			return false;
		}
		
		$("#captchaCode").keydown(function(e){  
	  		if(e.keyCode==13){  
	   			$("#formLogin").submit();//处理事件  
	  		}  
		});	
		
		
	</script>