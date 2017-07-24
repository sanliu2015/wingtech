<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/c" prefix="c" %>
<%@ taglib uri="/tags/fn" prefix="fn" %>
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<html lang="zh-cn">
<ts:base />
<head> 
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<title>系统登录</title>                     
<link rel="shortcut icon" href="${contextPath}/resource/image/logo/frontan.png">
  <link rel="bookmark" href="${contextPath}/resource/image/logo/frontan.png">
<link rel="stylesheet" href="${contextPath}/scripts/bootstrap/css/bootstrap.min.css"  type="text/css"> 
 <script src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>  
<script src="${contextPath}/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script> 
  <script src="Login.js?version=1.0" type="text/javascript"></script>  
</head>
<body style="padding-top: 10px;"> 
	<div class="container">
		<div  id="i202706117" style="text-align:center;margin-top:10px;margin-bottom:10px;height:100">
		   <div  id="i202706702">
               <br/> <br/> 
   		       <img src="${contextPath}/resource/image/logo/logo.gif" alt="wingtech.com"></img>
    	    </div>
        </div> 
		<div class="row" style="margin-top: 25px;">
			<div class="col-xs-6" style="margin-top: -15px;"> 
                <img src="${contextPath}/resource/image/logo/loginleft.jpg?v1" style="width:100%;height:60%"/>  
			</div> 
			<div class="col-xs-5"> 
				<div class="panel panel-success">
		              <div class="panel-heading">
		                <h3 class="panel-title">
		                	<span class="glyphicon glyphicon-user"></span>&nbsp;<ts:lang name='formTitle'/>
		                </h3>
		              </div>
		              <div class="panel-body">
	              		<form id="loginForm" name="loginForm" action="${contextPath}/index.do" method="post" class="form-horizontal"   >  
                         <input  name="loginstatus" type="hidden" value="pc"/>
                        <input name="appService"  id="appService" type="hidden" value="userService"/>
						  <div class="form-group">
                          <c:if test="${not empty errorMsg}">
                          <div class="alert alert-success" role="alert">${errorMsg}</div>
                          </c:if>
						    <label for="name" class="col-md-2 control-label"><ts:lang name='name'/></label>
						    <div class="col-md-6">
							    <input  name="name" type="text" class="form-control" id="name"  value="${userBean.name}" data-rule="账号:required;name;" placeholder="请输入账号" />
						    </div>
						  </div> 
						  <div class="form-group">
						    <label for="password" class="col-md-2 control-label"><ts:lang name='password'/></label>
						    <div class="col-md-6">
							    <input name="password" type="password" class="form-control" id="password" value="${userBean.password}" data-rule="密码:required;password;" placeholder="请输入密码" />
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="checkCode" class="col-md-2 control-label"><ts:lang name='checkCode'/></label>
						    <div class="col-md-3">
							    <input name="checkCode" type="text" class="form-control" id="checkCode" data-rule="验证码:required;checkCode;" placeholder="请输入验证码"   style="width:110px" />
                               </div>
                                 <div class="col-md-3">
                                <img src="${contextPath}/sys/imageCheckCode.do" id="createCheckCode" style="border:#999999 solid 1px; height:30px; width:80px">
						    </div>
						  </div> 
						  <div class="form-group">
						    <div class="col-md-offset-2 col-md-6">
						      <span style="margin:5px"><a id="submitButton" name="submitButton" class="btn btn-primary" style="font-size:20px"><ts:lang name='submitButton'/></a></span>
						      <a href="/user/forget.html">忘记密码？</a>
						    </div>
						  </div>
						</form> 

		              </div>
		               
	            </div>
			</div>
		</div>
        
       <div id="i202720092" style="border-top:1px solid RGB(164,164,164);padding-top:5px;margin-top:50px">
<div id="i202720677" style="float:left;color:RGB(120,120,120)">闻泰通讯 版本号V1.0 ©2016 <a href="http://www.wingtech.com/">www.wingtech.com</a></div>
<div id="i202721847" style="float:right;text-align:right;color:RGB(120,120,120)">
     
    <span style="margin-left:5px">
        <a href="http://www.wingtech.com" style="cursor:hand" onClick="this.style.behavior=&#39;url(#default#homepage)&#39;; 	this.setHomePage(&#39;http://www.wingtech.com/fap&#39;);"> 
    设为首页</a>
    </span> 
	<span style="margin-left:5px">|</span> 
    <span style="margin-left:5px">
    <a href="javascript:" onClick="jscript:window.external.AddFavorite(document.location.href,document.title);">收藏本页</a>
    </span> 
	<span style="margin-left:5px">|</span> 
    <span style="margin-left:5px">
    <a href="www.wingtech.com/contactus">联系我们</a>
    </span>
</div>
</div>  
<!-- 客户端二维码 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">客户端</h4>
      </div>
      <div class="modal-body" align="center">
        <img src="${contextPath}/pages/app/images/FAP-APK.png"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">  
	 loginScript.initPage({contextPath:"${contextPath}"});
	  
 </script>
</body>
</html>
