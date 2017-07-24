<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 
<title>日历管理</title> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css">  
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css"> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js?version=1.0"></script> 
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script> 
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script> 
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>   
<script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script> 
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script> 
<script type="text/javascript" src="<ts:base ref='path'/>/MyCalendar.js?version=1.2"></script> 
 
</head>
<body id="${appReqeustContext.appKey}Body" > 
<style type="text/css">
	.fc-day-number {
	float: right;
	width:15; 
	font-weight:bold;
	padding: 0px;
	}
</style>
  <form action="${contextPath}/core/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}CalendarForm" name="${appReqeustContext.appKey}CalendarForm" method="post">  
  <input name="myCalendarEmployeeId" id="myCalendarEmployeeId"  type="hidden"  value="${appReqeustContext.employeeId}" />
  <div style="text-align:left; width:99%; height:20px" id="workPlanContainer"> 
     <table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%"  >
     <tr  ><td  width="15%" align="left"   >  
     <div class="easyui-panel" style="padding:0px;width:120px"> 
        <a href="javascript:void(0)" id="weekPlanButtonGroup" dateRangeType="week" class="easyui-linkbutton" data-options="toggle:true,group:'g1',selected:true">周计划</a>
        <a href="javascript:void(0)" id="monthPlanButtonGroup"  dateRangeType="month"  class="easyui-linkbutton" data-options="toggle:true,group:'g1'">月总结</a> 
    </div>
    </td>
     <td width="25%"  ><a href="javascript:void(0)" class="easyui-linkbutton" id="prevWeekBtn"     onClick=" return myCalendarUtil.getMyCalendarDatas(this,'prev');">上一周</a><a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn"    onClick=" return myCalendarUtil.getMyCalendarDatas(this,'current');">今&nbsp;&nbsp;天</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" id="nextWeekBtn"   onClick="return myCalendarUtil.getMyCalendarDatas(this,'next');">下一周</a></td> 
      <td width="25%"  ><label   >日期 ：</label><input name="calendarDate"  id="calendarDate"  class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:80px"    onChange="return myCalendarUtil.getMyCalendarDatas(this,'')"  value="${appReqeustContext.currentDate}"></input></td>	
      <td  width="35%"  ><label   for="name">员工:</label> 
				 <input name="name" id="name" class="easyui-textbox"  readonly style="width:150px;"   value="${appReqeustContext.user.employeeName}"></input><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return myCalendarUtil.choseEmployee(this);">选择</a> <input name="employeeId" id="employeeId" type="hidden"  value="${appReqeustContext.employeeId}" /></td></tr>          
     </table>    
  </div>  
  <div id="myCalendarPlanPanel" class="easyui-panel"   title="&nbsp;"  data-options="collapsible:true" width="100%"> 
  <table width="100%" border="1" id="myCalendarPlanTable" align="left" cellpadding="0" cellspacing="0"  style="border-collapse:collapse;font-size:14px">  
 </table> 
 </div>
  <div id="testOrderFileListPanel" class="easyui-panel"   title="本周完成情况"  data-options="collapsible:true" width="100%">  
  <table width="100%" border="1" id="myCalendarSummaryTable" align="left" cellpadding="0" cellspacing="0"  style="border-collapse:collapse;font-size:14px"> 
      
 </table>
  </div>
  </form>
</body>
<script language="javascript">
var  myCalendarUtil=null; 
$(function(){ 
     var defaults={ userId:'${appReqeustContext.user.id}',empId:'${appReqeustContext.user.employeeId}' };
	 myCalendarUtil=new MyCalendarUtil(defaults);  
	 myCalendarUtil.initPage();   
	 $.ts.EasyUI.titleAppendToolbar("workPlanContainer","myCalendarPlanPanel","center",true); 
	  
});
</script>
</html>
