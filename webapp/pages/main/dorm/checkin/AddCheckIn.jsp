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
	<input name="bean.roomId" id="bean.roomId" type="hidden" />
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr>
				<td  nowrap><label   for="bean.roomName">房间<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.roomName" id="bean.roomName" class="easyui-textbox"   style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return checkInScript.chooseRoom(this);"  plain='true'></a>
				</td>
				<td  nowrap><label   for="bean.buildingName">楼栋</label></td>
				<td><input name="bean.buildingName" id="bean.buildingName" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
			</tr>
			<tr>
				<td  nowrap><label   for="bean.empName">姓名<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox"   style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return checkInScript.chooseEmployee(this);"  plain='true'></a>
				</td>
				<td  nowrap><label   for="bean.empNumber">工号</label></td>
				<td><input name="bean.empNumber" id="bean.empNumber" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
			</tr>
			<tr>
				<td  nowrap><label   for="bean.inDate">入住日期<span style="color:red">*</span></label></td>
				<td><input name="bean.inDate" id="bean.inDate" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
				<td  nowrap><label   for="bean.inTime">入住时间</label></td>
				<td><input name="bean.inTime" id="bean.inTime" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"/></td>
			</tr>
			<tr>
	      		<td nowrap><label   for="bean.keyStatus">进门户钥匙</label></td>
			    <td ><input class="easyui-textbox" name="bean.keyStatus" style="height:30px;width:150px"></td>
			    <td nowrap><label   for="bean.remoterKeep">空调遥控器保管者</label></td>
			    <td ><input class="easyui-textbox" name="bean.remoterKeep" style="height:30px;width:150px"></td>
	      	</tr>  
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:300px"></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return checkInScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js"></script> 
<script type="text/javascript">
    var checkInScript = new CheckInScript();  
	function modalDialogLoadEvent() {
		checkInScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>