<link href="${CtxPath}/styles/admin/pai/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CtxPath}/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	var __ctxPath = "${CtxPath}";
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
	if(top!=this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
		top.location='${CtxPath}/adminLogin.do';
	}
	$(document).ready(function() {   
		var params=$("#formLogin").serialize();
		$.post("${CtxPath}/adminLogin.do",params,showResponse);
	});
	
	function showResponse(responseText) {
	}
</script>
<body>
<div class="wrap">
	<div id="m" class="left_div">    
		<p id="lg">
			<img src="${CtxPath}/images/dragon/before.gif" style="width:100%;" usemap="#mp">
			<map name="mp">
				<area shape="rect" coords="22,17,252,110" href=""
				target="_blank" onmousedown="return ns_c({'fm':'behs','tab':'bdlogo'})">
			</map>
		</p>
	</div>
	<div class="verticalLine"></div>
	<div class="right_div">
		<div class="loginForm_wap">
			<div class="top_title">π后台</div>
			<form id="formLogin" method="post">
					用户名：<input type="text" class="input_txt" id="userName" name="userName" /> <br /> 
					<input type="hidden" id="isLogin" name="isLogin" value="Y"/>
					<div class="input_space"></div>
					<font style="margin-left:16px;">密码：</font><input type="password" class="input_txt" id="password" name="password" /> <br />  
					<div class="input_space"></div>
					验证码：<input type="text" id="captchaCode" name="captchaCode"  tabindex="3" class="input_code"/><span><img id="imgCaptchaCode" name="imgCaptchaCode" class="imgCaptchaCode" src="${CtxPath}/generateImage.do" 
									onclick="refreshImage('imgCaptchaCode')" alt="换一张" title="换一张" /></span>
					<br /> <input type="submit" class="sub" value="登 录" />
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${CtxPath}/scripts/admin/pai/dragon.js"></script>
</body>
