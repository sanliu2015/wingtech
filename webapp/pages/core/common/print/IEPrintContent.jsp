<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
 <%  
response.setHeader("Pragma","No-cache");//HTTP 1.1 
response.setHeader("Cache-Control","no-cache");//HTTP 1.0 
response.setHeader("Expires","0");//防止被proxy 
%>
<link rel="shortcut icon" href="${contextPath}/resource/image/logo/frontan.png">
  <link rel="bookmark" href="${contextPath}/resource/image/logo/frontan.png">
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
  <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js?version=1.0"></script>   
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script>
    
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>   
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>  
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/jquery.validate.js"></script>
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/additional-methods.js"></script>
   <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/messages_zh.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js?version=1.5"></script>
 <TITLE>打印文档</TITLE>   
 <style media=print>     
 
.noprint { display: none;color:green } 
 
.ieNoprint{display:none;} 
.PageNext{page-break-after: always;} 
.autoPageNext{page-break-after: auto;} 
  </style> 
 </head>  
	  
  <body  > 
<form id="form1" runat="server">	
   <center class="noprint" > 
<p> 
<OBJECT id="WebBrowser" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 width=0> 
</OBJECT>  
<input type=button value=直接打印 onClick="document.getElementById('WebBrowser').ExecWB(6,6); "  > 
<input type=button value=页面设置 onClick="document.getElementById('WebBrowser').ExecWB(8,1)"  > 
<input type=button value=打印预览  onClick="document.getElementById('WebBrowser').ExecWB(7,1);"     >
</p> 
<hr align="center" width="90%" size="1" noshade> 
</center>  
<div style="padding:10px;padding-left:10px; font-size:14px" id="printContentContainer">
${form.auditComments}
</div>
 
  </form>
</BODY>   
</html>