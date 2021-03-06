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
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<div id="pagePanel" title="" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr>
				<td  nowrap><label   for="bean.name">名称<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.name" id="bean.name" class="easyui-textbox"   style="width:200px;height:30px"  /></td>
				<td  nowrap><label   for="bean.number">编号<span style="color:red">*</span></label></td>
				<td><input name="bean.number" id="bean.number" class="easyui-textbox"  style="width:200px;height:30px" /></td>
			</tr>
			<tr>
				<td  nowrap><label   for="bean.size">大小</label></td>
				<td><input name="bean.size" id="bean.size" class="easyui-textbox"   style="width:200px;height:30px" /></td>
				<td  nowrap><label   for="bean.capacity">可容纳人数</label></td>
				<td><input name="bean.capacity" id="bean.capacity" class="easyui-numberbox"  style="width:200px;height:30px" data-options="min:0" /></td>
			</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.address">地点</label></td>
			    <td><input class="easyui-textbox" name="bean.address" style="height:30px;width:200px"></td>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td><input class="easyui-textbox" name="bean.description" style="height:30px;width:200px"></td>
	      	</tr>  
		</table>
	</div>	
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return meetingRoomScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/MeetingRoom.js"></script> 
<script type="text/javascript">
    var meetingRoomScript = new MeetingRoomScript();  
	function modalDialogLoadEvent() {
		meetingRoomScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>