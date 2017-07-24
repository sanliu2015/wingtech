<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>添加${appReqeustContext.appName}</title> 
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/saveWeekPlan.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="summary.id" type="hidden"  value="${form.summary.id}" /> 
    <input name="summary.status" type="hidden"    value="1"/>    
    <input name="summary.eventType" type="hidden"    value="plan"/>  
    <input name="summary.dateRangeType" type="hidden"    value="week"/>   
   <input name="summary.startDate" type="hidden"   value="${form.summary.startDate}"/> 
   <input name="summary.endDate" type="hidden"   value="${form.summary.endDate}"/> 
   <input name="summary.employee.id" type="hidden"   value="${form.summary.employee.id}"/> 
<div style="padding:5px;padding-left:1%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="summary.name">时间</label> </td>
				<td nowrap> <input name="summary.name"  id="summary.name"  class="easyui-textbox" readonly   style="width:180px;height:30px" value="${form.summary.name}"></input> 
                 </td>  
			</tr> 
            <tr  > 
           <td  nowrap  ><label   for="summary.eventExplain">标题*</label> </td>
				<td nowrap> <input name="summary.eventExplain"  id="summary.eventExplain"  class="easyui-textbox"     style="width:280px;height:30px" value="${form.summary.eventExplain}"></input></td>                 
            </tr> 
            <tr  > 
              <td  nowrap  ><label   for="summary.visitCustNames">重点拜访客户</label> </td>
				<td nowrap> <input name="summary.visitCustNames"  id="summary.visitCustNames"  class="easyui-textbox"     style="width:280px;height:30px" value="${form.summary.visitCustNames}"></input></td>                 
            </tr>
            <tr  > 
              <td  nowrap  ><label   for="summary.developCustNames">新开发客户</label></td> 
				<td nowrap> <input name="summary.developCustNames"  id="summary.developCustNames"  class="easyui-textbox"     style="width:280px;height:30px" value="${form.summary.developCustNames}"></input></td>                 
            </tr>
              <tr  >
				<td nowrap    ><label  for="summary.description" >重点行动目标</label></td>  
				<td    ><input class="easyui-textbox"   name="summary.description" id="summary.description" data-options="multiline:true"  style="height:200px;width:500px"  value="${form.summary.description}"></input> </td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return myCalendarUtil.submitWeekPlanForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>        
 
  <c:if test="${form.actionType=='1'}">
	<script type="text/javascript" src="<ts:base ref='path'/>/MyCalendar.js?version=1.1"></script> 
    <script language="javascript">
    var  myCalendarUtil=null; 
	
    $(function(){ 
         var defaults={ userId:'${appReqeustContext.user.id}',empId:'${appReqeustContext.user.employeeId}' };
         myCalendarUtil=new MyCalendarUtil(defaults);  
         myCalendarUtil.initPage();    
          
    });
    </script>
</c:if>
 
</form>   
</body>
</html>