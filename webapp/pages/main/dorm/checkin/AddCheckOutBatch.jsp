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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doOutBatch.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input name="ids" id="ids" type="hidden" />
	<div id="pagePanel" title="" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr>
          		<td  nowrap><label   for="bean.outDate">退宿日期<span style="color:red">*</span></label></td>
				<td><input name="bean.outDate" id="bean.outDate" class="easyui-my97" style="width:126px;height:28px"  /></td>
				<td  nowrap><label   for="bean.outTime">退宿时间</label></td>
				<td><input name="bean.outTime" id="bean.outTime" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"/></td>
		   		<td nowrap><label   for="bean.outReason">退宿原因</label></td>
			    <td><input class="easyui-textbox" name="bean.outReason" id="bean.outReason" style="height:30px;width:130px"></td>
		   	</tr >
		</table>
	</div> 
	<table id="dtlListGrid" title="损坏遗失费用登记"  class="easyui-datagrid" style="width:100%" data-options="rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'roomName',width:90">房间</th>
                <th data-options="field:'empNumber',width:100">工号</th>
				<th data-options="field:'empName',width:90">姓名</th>
				<th data-options="field:'amount',width:70, formatter:checkInScript.numberboxInputFormat">扣款金额</th>
				<th data-options="field:'reason',width:200, formatter:checkInScript.comboboxMultipleFormat">扣款原因</th>
				<th data-options="field:'description',width:200, formatter:checkInScript.textInputFormat">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:checkInScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'employeeId',width:10, formatter:checkInScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
	</table> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return checkInScript.submitFormOnCheckOutBatch(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js"></script> 
<script type="text/javascript">
    var checkInScript = new CheckInScript();
    checkInScript.reasonList = '<ts:forEach name="damageReasonList" toJson="1"/>';
    var dtlList = ${dtlList};
    $(function() {    
    	checkInScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
    	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
		var formJson = jQuery.parseJSON(jsonContentObj.text()); 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	});
    function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			dtlList[i].recordOperateStatus="update"; 
			var tmp = {"dtlList" : dtlList[i]}; 
			checkInScript.insertDatagridRow(tmp);   
		} 
	} 
</script>      
</form> 
</body>
</html>