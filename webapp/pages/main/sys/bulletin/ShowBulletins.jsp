<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 
<title>事务提醒</title> 
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
<script type="text/javascript" src="hintjs/jquery.fullcalendar.js"></script> 
 
<script type="text/javascript" src="hintjs/TransactionHint.js?version=1.4"></script>
 
<style type="text/css">
<!--
 
.spanStyle { 
display:-moz-inline-box;
display:inline-block;
width:100px; 
} 
body {
	FONT-SIZE: 14px;
	COLOR:  #660000; 
	FONT-FAMILY:  "宋体"; 
} 
-->
</style>
</head>
<body   > 
<div id="cc" class="easyui-layout"  data-options="fit:true">
         <div region="center"  style="width:60%;padding1:0px;overflow:hidden;">
         <table width="100%" border="0"  align="left" cellpadding="0" cellspacing="0" class="linkContainer"> 
              <!-- 公司公告 -->
              <tr>
                <td> 
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="20" valign="top" background="${contextPath}/resource/image/icon/gonggao.jpg" style="background-repeat:no-repeat"> <span  class="spanStyle" > </span><span id="ltBulletinNoRecordCount" style="font-weight:bold; font-size:14px"> </span>
							<div align="right"><span style="border:1"><a href="javascript:void(0)" onClick="transactionHintUtil.openMoreBulletinTab( )"><img src="${contextPath}/resource/image/icon/more.gif" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
                            </td>
                            </tr>  
                          <tr>
                            <td width="100%">
							<table width="100%"  id="ltBulletinTable"   style="font-size:14px"  > 
                            </table></td>
                          </tr>  
                        </table>   
                 </td>
              </tr>		 
              <tr>
                <td width="100%"> 
                        <table width="100%" cellpadding="0" cellspacing="0" >
                          <tr>
                            <td height="20" valign="top" background="${contextPath}/resource/image/icon/tongzhi.jpg" style="background-repeat:no-repeat">
							<span  class="spanStyle">&nbsp;</span><span id="ltNotifyNoRecordCount" style="font-weight:bold"> </span>
							<div align="right"><span style="border:1"><a href="javascript:void(0)" onClick="transactionHintUtil.openMoreAuditReportTab()"><img src="${contextPath}/resource/image/icon/more.gif" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div></td>
                          </tr>
						<!--显示待办事件--> 
                          <tr>
                            <td width="100%"><table width="100%"  id="ltNotifyTable" style="font-size:14px"> 
                            </table></td>
                          </tr> 
                        </table>  
                   </td>
              </tr>
               
            </table> 
          </div>
          <div region="east" title="&nbsp;" split="true"   style="overflow:hidden;width:40%">
             <table width="100%" border="0" cellspacing="1" cellpadding="1">
              <tr>
                  <td width="100%" align="center"  > 
				  		   <div  style="width:100%;height:450px;" id='customCalendar' fit="true">
							<div class="easyui-fullCalendar" id="fullCalendarDiv" name="fullCalendarDiv" fit="true"></div>
                            <a href="javascript:void(0)"   id="fullCalendarButton">日程管理</a>
						</div>
						
				  </td>
              </tr> 
            </table> 
        </div> 
     </div>

<script language="javascript">
var  transactionHintUtil=null;
var emergentLevelNews=new Array();
	  
$(function(){ 
     var defaults={ userId:'${appReqeustContext.user.id}',empId:'${appReqeustContext.user.employeeId}' };
	 transactionHintUtil=new TransactionHintUtil(defaults);   
	 //scriptInstance.initCalendarEvent();
	 transactionHintUtil.initPage(); 
     //getDateCell();
});
</script>
</body>
</html>
