<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<title>${appReqeustContext.appName}</title> 
</head>
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doLeave.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input type="hidden" name="bean.id" /> 
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.leaveDate">离开日期<span style="color:red">*</span></label></td>
				<td><input name="bean.leaveDate" id="bean.leaveDate" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentDate}"  /></td>
				<td  nowrap><label   for="bean.leaveTime">离开时间</label></td>
				<td><input name="bean.leaveTime" id="bean.leaveTime" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentTime}" onfocus="WdatePicker({dateFmt:'HH:MM:ss'})" /></td>
		   	</tr >
		   	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
	</div> 
	
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return truckScript.submitFormOnLeave(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript" src="<ts:base ref='path'/>/Truck.js"></script> 
<script type="text/javascript">
	var truckScript = new TruckScript();  
	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		truckScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
	} 
</script>      
</form> 
</body>
</html>