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
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="plan.id" id="plan.id" type="hidden"  />  <input name="plan.status" type="hidden"    value="1"/>    
    
   <input name="plan.employee.id" type="hidden"   value="${form.plan.employee.id}"/> 
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number">时间</label></td>
				<td ><input name="plan.name"  id="plan.name"  class="easyui-textbox" readonly   style="width:180px;height:30px" value="${form.plan.name}"></input></td> 
			</tr>  
            <tr  >
				<td nowrap><label  for="plan.planDate">日期</label></td>
				<td  ><input name="plan.planDate"  id="plan.planDate"  class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;height:30px"   value="${form.plan.planDate}"></input></td> 
				 <input type="radio" name="plan.dayType"    value="am" ${form.plan.dayType=='am'?'"checked"':""}/>上午
                <input type="radio" name="plan.dayType"  value="pm" ${form.plan.dayType=='pm'?'"checked"':""} />下午</td> 
			</tr>  
           <td  nowrap><label   for="plan.eventExplain"  >计划事项*</label></td>
				<td  > <select name="plan.eventExplain"  id="plan.eventExplain"   class="easyui-combobox"   style="width:180px;height:30px"  >   
                                <ts:forEach name='eventExplainList'    insertEmpty='0'/>
							</select></td>                 
            </tr> 
            <tr  >
                <td  nowrap><label   for="plan.custName">拜访客户 </label></td>
				<td><input name="plan.custName" id="bean.custName" class="easyui-textbox"     style="width:180px;height:30px"  ></input></td>
           </tr>    
			<tr  >
				<td nowrap><label   for="plan.description">详情：</label></td>
				<td  ><input class="easyui-textbox" name="plan.description"   data-options="multiline:true" style="height:70px;width:300px"></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return myCalendarUtil.submitPlanForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>        
</form> 
 
</body>
</html>