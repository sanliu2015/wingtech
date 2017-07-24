<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最近一次日志查看</title> 
</head>
<body id="${appReqeustContext.appKey}Body"   >  
	<form action="${contextPath}/core/${appReqeustContext.appService}/json/save.ts" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
		<div style="padding:10px;"> 
			<table cellpadding="0" cellspacing="0" class="baseForm-table">
				<tr  >
					<td nowrap><label id="number-label" for="occurDate">执行日期</label></td>
					<td ><input name="occurDate"  id="occurDate" readonly  class="easyui-textbox"  style="width:300px;height:30px" value="${logBean.occurDate}"></input></td> 
				</tr>  
            	<tr  >
                	<td nowrap><label id="number-label" for="infoFlag">执行结果</label></td>
					<td ><input name="infoFlag"  id="infoFlag"  readonly class="easyui-textbox"  style="width:300px;height:30px" value="${logBean.infoFlag}"></input></td> 
           		</tr> 
           		<tr  >
                	<td nowrap><label id="number-label" for="description">提示信息</label></td>
					<td ><input class="easyui-textbox" readonly name="description" data-options="multiline:true" style="width:600px;height:400px" value="${logBean.description}"></input></td>
           		</tr>   
			</table>
		</div> 
 		<div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     	</div> 
	<script type="text/javascript" src="<ts:base ref='path'/>/ScheduleJob.js"></script> 
	<script type="text/javascript">
    var scriptInstance=new ScriptUtil();  
	$(function() { 
		scriptInstance.initPage({ appKey:"${appReqeustContext.appKey}"});    
	   
	}); 
	</script>      
	</form> 
</body>
</html>