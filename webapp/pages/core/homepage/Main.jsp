<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/c" prefix="c" %>
<%@ taglib uri="/tags/fn" prefix="fn" %>
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<meta charset="utf-8">
<title>物业管理系统</title>        
<link rel="shortcut icon" href="${contextPath}/resource/image/logo/frontan.png">
<link rel="bookmark" href="${contextPath}/resource/image/logo/frontan.png">       
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">    
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/jquery/plugs/ztree/zTreeStyle/zTreeStyle.css" > 
  <link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
   <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 

 
</head>

<body class="easyui-layout"     >
 <div  data-options="region:'north',split:true,border:false,collapsible:false"    style="height:60px;padding:-2px; background:transparent"> 
  
     <div  style="width:600px;height:20px;float:left;padding-left:10px; font-size:14px" id="myInfoLinkDiv">
     <div style="float:left;"><br/><img src="${contextPath}/resource/image/logo/logo.gif"      alt="物业管理系统" > </img></div> 
      <div  style="float:left; padding:10px;font-size:14px"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-man'">${LOGIN_USER.employeeName}(${LOGIN_USER.positionName})</a></div>  
      <!-- <div  style="float:right; padding:10px;font-size:14px"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"><span class="badge"  id="main_taskRecordsContainer">消息(<span id="main_taskRecords">0</span>)</span></a></div>   -->
     </div> 
    <div  style="width:200px;height:20px;float:right;padding:10px; font-size:14px" id="mainLinkDiv">
    <a href="javascript:void(0)" id="main_homepageBtn" plain=true        style="text-decoration:underline">首页</a> 
     <a href="javascript:void(0)" id="main_modifyPasswordBtn" plain=true  iconCls="icon-lock"      style="text-decoration:underline">修改密码</a>
     <a href="javascript:void(0)" id="main_returnSysBtn" plain=true  style="padding-left:10px;text-decoration:underline"  >退出</a> 
    </div>
 </div>  
 <div region="west" split="true" title="导航面板" style="width:220px;padding1:0px;overflow:hidden;">
       <div id="navigationAccordion" class="easyui-accordion" fit="true" border="false">
				<div title="功能菜单" selected="true"   style="overflow:auto;">
					 <ul id="menuTree" class="ztree" style="width:95%;height:98%;overflow:auto; font-size:14px"></ul>
				</div>
				<div title="收藏夹" style="padding:10px;" id="bookMarkDiv">
					  <table width="100%" border="0"  style="padding:2px">
						<tr>
						  <td align="left" bgcolor="#00FFFF" style=" padding-left:5px"  > 
							<img src="${contextPath}/resource/image/home/menuleft.gif"><a href="/managerBookMark.do?next=edit">管理收藏夹</a> </td>
						</tr>
						 
					  </table>
				</div> 
			</div>
 </div>
		<div region="center" title="" style="overflow:hidden;">
			<div id="tabs" class="easyui-tabs" fit="true" border="false"> 
			</div>
		</div>
  <div id="mm" class="easyui-menu" style="width:150px; display:none">
			<div id="mm-tabclose">关闭</div>
			<div id="mm-tabcloseall">全部关闭</div>
			<div id="mm-tabcloseother">除此之外全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">当前页右侧全部关闭</div>
			<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-tabrefresh">刷新</div>
			<div id="mm-exit">退出</div>
		</div>
		<div id="treeRightMenu" class="easyui-menu" style="width:150px; display:none">
		     <div id="rm-openTab">打开</div> 
			<div id="rm-openNewTab">在新标签页打开</div> 
			<div id="rm-insertBookMark">添加到收藏夹</div> 
			<div id="rm-refreshMenuTree">刷新菜单</div> 
		</div> 
  <bgsound id="msgHintSound"/>

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

<script src="Main.js?version=2.2" type="text/javascript"></script>  
<script src="MsgPushletService.js?version=2.5" type="text/javascript"></script>  
 <script src="${contextPath}/scripts/ts/ajax-pushlet-client.js"></script> 
<script type="text/javascript">
 var mainScript=new MainScript();
 var msgPushletScript=new MsgPushletScript();
 function initMsgPushletService(){
		PL.userId='${LOGIN_USER.id}'; //放在PL._init();之前  
		var parameters=new Array();
		parameters[0]={name:"loginstatus",value:"pc"};
		PL.parameters=parameters;
	   // PL._init();//加了该行在ifrmae框架中且为chrome下接收不到推送的消息  
	    //PL.joinListen('bulletin'); 
		//p_join_listen('/ts/login,/ts/bulletin,/ts/task');
		 p_join_listen('/ts');  
	}
 initMsgPushletService();
$(document).ready(function() {  
	mainScript.initPage({contextPath:"${contextPath}",userId:"${LOGIN_USER.id}",employeeId:"${LOGIN_USER.employeeId}"}); 
 	 
	
}); 

</script>

</body>
</html>
