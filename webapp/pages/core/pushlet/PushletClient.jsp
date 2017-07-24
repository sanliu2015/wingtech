<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/c" prefix="c" %>
<%@ taglib uri="/tags/fn" prefix="fn" %>
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes" />  
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<title>FAP智能应用平台</title>  
 
  <link rel="shortcut icon" href="${contextPath}/resource/image/logo/frontan.png">
<link rel="bookmark" href="${contextPath}/resource/image/logo/frontan.png">       
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">    
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/jquery/plugs/ztree/zTreeStyle/zTreeStyle.css" > 
  <link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
   <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="${contextPath}/scripts/jquery/plugs/ztree/jquery.ztree.core-3.5.js?version=1.0"></script>
<script language="javascript" src='${contextPath}/scripts/jquery/jquery.tabs.extend.js'></script>
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>  
<script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script>
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/jquery.validate.js"></script>
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/additional-methods.js"></script>
<script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/messages_zh.js"></script>
<script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>

 
 <script src="${contextPath}/scripts/ts/ajax-pushlet-client.js"></script>
 
 
</head>
<body>
 
 <form action="<c:url value='/app/core/test/testOrder/execute.htm?appMethod=submit'/>" method="post">
    
	<table width="100%" id="userListTable">
		<tr>
			<td>选择订阅主题</td>
			<td><select name="selectField" onChange="subscribeUnsubscribe(this.value)">  
           <option selected value="" >选择主题</option>  
           <option value="/system">系统信息</option>  
           <option value="/temperature">天气情况</option>  
           <option value="/stocks">网络连接</option>  
           <option value="/stocks,/temperature">多主题订阅</option>  
           <option value="/">所有消息</option>  
          <option value="UNSUBSCRIBE">卸载主题</option>  
          </select>  </td>
		</tr> 
        <tr>
			<td>发送方式</td>
			<td><select name="sendModel"  id="sendModel">   
           <option value="multicast">主题发布</option>  
           <option value="unicast">向用户ID发布</option>  
           <option value="broadcast">所有用户发布</option>   
          </select>  </td>
		</tr> 
         <tr>
			<td>发送的人员ID</td>
			<td>  <input type="text" name="assionId"  id="assionId"  />  </td>
		</tr> 
        <tr>
			<td>接收到的消息</td>
			<td>   <input type="text" name="receiverMsg"  id="receiverMsg"  size="50" />  </td>
		</tr> 
	</table>
    输入主题：<input type="text" name="dataEvent"  id="dataEvent"  value="/user" /> 
    输入发送内容：<input type="text" name="dateRangeName"  id="dateRangeName"  /> 
    <input type="button" value="经过后台推送消息"  onClick="sendMsg()"/>
     <input type="button" value="经过前台推送消息"  onClick="sendJsMsg()"/>
</form>
<script type="text/javascript"> 
    function receiveMsg(){
		PL.userId='${LOGIN_USER.employeeId}'; //放在PL._init();之前  
	    PL._init();  
	    //PL.joinListen('/ts,/user,/system,/stocks,/temperature');
		p_join_listen('/ts,/user,/system,/stocks,/temperature');
	}
	receiveMsg();
	function subscribeUnsubscribe(value){ 
	      p_unsubscribe();
		  p_subscribe(value, 'mylabel');  
		  p_leave();   
		  p_unsubscribe();   
	     PL._init();  
	     PL.joinListen(value);
	}
function onData(event){ 
   alert(event.get("number"));
   var msg=event.get("msg");
   if(msg!=null){  
	$("#receiverMsg").val(msg+event.getEvent());
   }
	//p_leave();
  /*   p_publish('1234', 'action', 'exit', 'nick', nick);
    p_leave(); */
} 
function sendMsg(){
	$.post("${contextPath}/core/testMsgPushletService/json/pulltocust.do", {name:$("#dateRangeName").val(),
	number:$("#dataEvent").val(),createdBy:$("#sendModel").val(),createDate:$("#assionId").val()});
	//p_publish('1','msg',encodeURI($("#msg").val()));
}
function sendJsMsg(){
	var json={name:$("#dateRangeName").val(),
	number:$("#dataEvent").val(),createdBy:$("#sendModel").val(),createDate:$("#assionId").val()};
	var str=$.ts.Utils.toUrlParam(json);
	PL.publish($("#dataEvent").val(),str);    
}	
	 
</script>
</body>
</html>