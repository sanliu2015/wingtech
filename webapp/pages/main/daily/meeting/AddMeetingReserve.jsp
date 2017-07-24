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
				<td  nowrap><label   for="bean.theme">会议主题<span style="color:red">*</span></label></td>
				<td colspan="3"><input name="bean.theme" id="bean.theme" class="easyui-textbox"  style="width:400px;height:30px" /></td>
				<td  nowrap><label   for="bean.meetingRoomId">会议室<span style="color:red">*</span></label></td>
				<td>
					<select name="bean.meetingRoomId"  id="bean.meetingRoomId" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='meetingRoomList' insertEmpty='1' value="" />
					</select>
				</td>
			</tr>
			<tr>
				<td  nowrap><label   for="bean.conveneDate">会议日期<span style="color:red">*</span></label></td>
				<td colspan="5"><input name="bean.conveneDate" id="bean.conveneDate" class="textbox"   style="width:800px;height:30px" /></td>
				<!-- <td><input name="bean.conveneDate" id="bean.conveneDate" class="textbox"   style="width:150px;height:30px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" /></td> -->
			</tr>
			<tr>
				<td  nowrap><label   for="bean.beginTime">开始时间<span style="color:red">*</span></label></td>
				<td><input name="bean.beginTime" id="bean.beginTime" class="textbox"   style="width:150px;height:30px" onclick="WdatePicker({dateFmt:'HH:mm'})"  /></td>
				<td  nowrap><label   for="bean.endedTime">结束时间<span style="color:red">*</span></label></td>
				<td><input name="bean.endedTime" id="bean.endedTime" class="textbox"   style="width:150px;height:30px" onclick="WdatePicker({dateFmt:'HH:mm'})" /></td>
			</tr>
			<tr>	
				<td  nowrap><label   for="bean.joinSum">与会人数</label></td>
				<td><input name="bean.joinSum" id="bean.joinSum" class="easyui-numberbox"   style="width:150px;height:30px" /></td>
				<td  nowrap><label   for="bean.needProjector">是否要投影仪</label></td>
				<td>
					<select name="bean.needProjector"  id="bean.needProjector" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='whetherList' insertEmpty='0' />
					</select>
				</td>
				<td nowrap><label for="bean.reserveEmpName">预定人员</label></td>
				<td><input name="bean.reserveEmpName" id="bean.reserveEmpName" class="easyui-textbox" style="width:150px;height:30px" readonly/></input>
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return meetingReserveScript.chooseEmployee(this);"  plain='true'></a>
					<input name="bean.reserveEmpId" id="bean.reserveEmpId" type="hidden"  />
				</td> 		
			</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5"><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
		<table id="dtlListGrid" class="easyui-datagrid" data-options="height:300,rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:meetingReserveScript.removeRowFormat">操作</th>
                <th data-options="field:'name',width:150, formatter:meetingReserveScript.textInputFormat">姓名</th>
				<th data-options="field:'phone',width:150,formatter:meetingReserveScript.textInputFormat">手机</th>
				<th data-options="field:'isLeader',width:90,formatter:meetingReserveScript.comboboxFormat">是否领导</th>
				<th data-options="field:'description',width:250, formatter:meetingReserveScript.textInputFormat">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:meetingReserveScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:meetingReserveScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'hdrId',width:10, formatter:meetingReserveScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
		</table> 
	</div>	
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="meetingReserveScript.appendRow(this);">增加</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return meetingReserveScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div>
<style>
.datagrid-row {
        height: 27px;
    }
</style>      
<script type="text/javascript" src="<ts:base ref='path'/>/MeetingReserve.js"></script> 
<script type="text/javascript">
    var meetingReserveScript = new MeetingReserveScript();  
    meetingReserveScript.isLeaderList = '<ts:forEach name="isLeaderList" insertEmpty="0" toJson="1"/>'
	function modalDialogLoadEvent() {
    	meetingReserveScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>