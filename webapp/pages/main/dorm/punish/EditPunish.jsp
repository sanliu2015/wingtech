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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input name="bean.id" id="bean.id" type="hidden" />
	<input name="bean.employeeId" id="bean.employeeId" type="hidden" />
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.occurDate">日期<span style="color:red">*</span></label></td>
				<td><input name="bean.occurDate" id="bean.occurDate" class="easyui-my97" style="width:126px;height:28px"  /></td>
				<td  nowrap><label   for="bean.empNumber">工号</label></td>
				<td><input name="bean.empNumber" id="bean.empNumber" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.empName">姓名<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox" style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return punishScript.chooseEmployee(this);"  plain='true'></a>
				</td>
		   	</tr >
          	<tr>
			   	<td nowrap><label   for="bean.amount">经济处罚</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.amount" id="bean.amount" data-options="min:0,precision:0" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.executivePunish">行政处罚</label></td>
			   	<td  >
			   		<select name="bean.executivePunish" id="bean.executivePunish" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='executivePunishList' insertEmpty='1'/>
					</select>
			   	</td>
			   	<td nowrap><label   for="bean.reason">惩罚原因<span style="color:red">*</span></label></td>
			   	<td  ><input name="bean.reason" id="bean.reason" style="width:250px;height:30px" /></td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return punishScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Punish.js"></script> 
<script type="text/javascript">
    var punishScript = new PunishScript();  
    $(function() {    
    	punishScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
    	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
		var formJson = jQuery.parseJSON(jsonContentObj.text()); 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	});
	function modalDialogLoadEvent() {
		$("#bean\\.reason").combobox({
			valueField:'id',    
	        textFiled:'text',
			data:${reasonList}
		});
	} 
</script>      
</form> 
</body>
</html>