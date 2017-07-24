<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改${appReqeustContext.appName}</title> 
</head>
<body id="${appReqeustContext.appKey}Body"   >  
	<form action="${contextPath}/core/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
		<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
		<input type="hidden" name="bean.id" id="bean.id">
		<div style="padding:10px;padding-left:15%">
			<table cellpadding="0" cellspacing="0" class="baseForm-table">
				<tr  >
					<td nowrap><label id="number-label" for="bean.jobName">任务名称<span style="color:red">*</span></label></td>
					<td style="width:260px"><input name="bean.jobName"  id="bean.jobName"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
				</tr>  
            	<tr  >
                	<td nowrap><label id="number-label" for="bean.jobGroup">任务小组</label></td>
					<td style="width:260px"><input name="bean.jobGroup"  id="bean.jobGroup"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
           		</tr> 
           		<tr  >
                	<td nowrap><label id="number-label" for="bean.cronExpression">cron表达式<span style="color:red">*</span></label></td>
					<td style="width:260px"><input name="bean.cronExpression"  id="bean.cronExpression"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
           		</tr>  
           		<tr  >
                	<td nowrap><label id="number-label" for="bean.beanClass">java类名<span style="color:red">*</span></label></td>
					<td style="width:260px"><input name="bean.beanClass"  id="bean.beanClass"  class="easyui-textbox"  style="width:300px;height:30px" value="com.ts.main.sys.schedulejob.ExecuteTask"></input></td> 
           		</tr>  
           		<tr  >
                	<td nowrap><label id="number-label" for="bean.methodName">java方法名<span style="color:red">*</span></label></td>
					<td style="width:260px"><input name="bean.methodName"  id="bean.methodName"  class="easyui-textbox"  style="width:200px;height:30px" value="executeProc"></input></td> 
           		</tr>   
			</table>
		</div> 
 		<div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return scriptInstance.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     	</div> 
	<script type="text/javascript" src="<ts:base ref='path'/>/ScheduleJob.js"></script> 
	<script type="text/javascript">
    var scriptInstance=new ScriptUtil();  
	$(function() { 
		var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
		var formJson=jQuery.parseJSON(jsonContentObj.text()); 
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);  
		jsonContentObj.remove(); 
		scriptInstance.initPage({ appKey:"${appReqeustContext.appKey}"});    
	   
	}); 
	</script>      
	</form> 
</body>
</html>