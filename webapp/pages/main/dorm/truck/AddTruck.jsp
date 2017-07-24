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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input name="bean.employeeId" id="bean.employeeId" type="hidden" />
	<input name="bean.status" id="bean.status" type="hidden" value="0" />
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.visitDate">进入日期<span style="color:red">*</span></label></td>
				<td><input name="bean.visitDate" id="bean.visitDate" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentDate}"  /></td>
				<td  nowrap><label   for="bean.visitTime">进入时间</label></td>
				<td><input name="bean.visitTime" id="bean.visitTime" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentTime}" onfocus="WdatePicker({dateFmt:'HH:MM:ss'})" /></td>
		   		<td  nowrap><label   for="bean.sendNo">货运单号</label></td>
				<td>
					<input class="easyui-textbox" name="bean.sendNo" id="bean.sendNo" style="width:150px;height:30px" />
				</td>
		   	</tr >
		   	<tr>
		   		<td  nowrap><label   for="bean.visitor">姓名<span style="color:red">*</span></label></td>
				<td>
					<input class="easyui-textbox" name="bean.visitor" id="bean.visitor" style="width:150px;height:30px" />
				</td>
		   		<td nowrap><label   for="bean.visitCompany">所属单位</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitCompany" id="bean.visitCompany" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.visitorPhone">联系电话</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorPhone" id="bean.visitorPhone" style="width:150px;height:30px" /></td>
		   	</tr>
		   	<tr>
		   		<td nowrap><label   for="bean.visitorIdCard">身份证</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorIdCard" id="bean.visitorIdCard" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.visitorCarNum">车牌号</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorCarNum" id="bean.visitorCarNum" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.visitReason">类型</label></td>
			   	<td >
			   		<input type="radio" name="bean.visitReason" value="送货" />送货
			   		<input type="radio" name="bean.visitReason" value="提货" />提货
			   	</td>	
		   	</tr>
		   	<tr>
				<td  nowrap><label   for="bean.empName">相关人姓名</label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox"   style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return truckScript.chooseEmployee(this);"  plain='true'></a>
				</td>
				<td  nowrap><label   for="bean.depName">部门/科室</label></td>
				<td><input name="bean.depName" id="bean.depName" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
		   		<td  nowrap><label   for="bean.tempCard">临时厂牌编号</label></td>
				<td><input name="bean.tempCard" id="bean.tempCard" class="easyui-textbox"  style="width:150px;height:30px" /></td>
		   	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
			    <td nowrap><label   for="bean.hasGood">有无自带货</label></td>
			    <td >
			   		<input type="radio" name="bean.hasGood" value="0" />无
			   		<input type="radio" name="bean.hasGood" value="1" />有
			   	</td>	
	      	</tr>  
		</table>
	</div> 
	
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return truckScript.submitForm(this);">确定</a>  
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
	function modalDialogLoadEvent() {
		truckScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>