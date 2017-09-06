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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doOut.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input name="bean.id" id="bean.id" type="hidden" />
	<input name="bean.employeeId" id="bean.employeeId" type="hidden" />
	<div id="pagePanel" title="" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr>
          		<td  nowrap><label   for="bean.outDate">退宿日期<span style="color:red">*</span></label></td>
				<td><input name="bean.outDate" id="bean.outDate" class="easyui-my97" style="width:126px;height:28px"  /></td>
				<td  nowrap><label   for="bean.empNumber">工号</label></td>
				<td><input name="bean.empNumber" id="bean.empNumber" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.empName">姓名<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox"   style="width:150px;height:30px" readonly />
				</td>
		   	</tr >
		   	<tr>
		   		<td  nowrap><label   for="bean.outTime">退宿时间</label></td>
				<td><input name="bean.outTime" id="bean.outTime" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})"/></td>
	      		<td  nowrap><label   for="bean.keyStatus">进门户钥匙</label></td>
	      		<td><input name="bean.keyStatus" id="bean.keyStatus" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.empName">空调遥控器</label></td>
				<td>
					<input name="remoterKeep" id="remoterKeep" class="easyui-textbox" style="width:150px;height:30px" readonly />
				</td>
	      	</tr>
			<tr>
	      		<td nowrap><label   for="bean.outReason">退宿原因</label></td>
			    <td colspan="5" ><input class="easyui-textbox" name="bean.outReason" id="bean.outReason" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" title="损坏遗失费用登记" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr>
			   	<td nowrap><label   for="damage.amount">扣款金额</label></td>
			   	<td  ><input class="easyui-numberbox" name="damage.amount" id="damage.amount" data-options="min:0,precision:0" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="damage.reason">扣款原因</label></td>
			   	<td  colspan="3">
			   		<input name="damage.reason" id="damage.reason" style="width:250px;height:30px" />
			   	</td>
	      	</tr>
			<tr>
	      		<td nowrap><label   for="damage.description">备注</label></td>
			    <td colspan="5" ><input class="easyui-textbox" name="damage.description" style="height:30px;width:400px"></td>
	      	</tr> 
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return checkInScript.submitFormOnCheckOut(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js"></script> 
<script type="text/javascript">
    var checkInScript = new CheckInScript();
    function modalDialogLoadEvent() {   
		$("#damage\\.reason").combobox({
			multiple:true,
			valueField:"name",    
	        textField:"name",
			data:<ts:forEach name="damageReasonList" toJson="1"/>,
		});
	}
    $(function() {    
    	checkInScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
    	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
		var formJson = jQuery.parseJSON(jsonContentObj.text()); 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	});
</script>      
</form> 
</body>
</html>