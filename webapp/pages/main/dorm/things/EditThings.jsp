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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<input name="bean.id" id="bean.id" type="hidden" />
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
				<td  nowrap><label   for="bean.name">名称<span style="color:red">*</span></label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox"  style="width:150px;height:30px" /></td>
				<td  nowrap><label   for="bean.number">编号</label></td>
				<td><input name="bean.number" id="bean.number" class="easyui-textbox"  style="width:150px;height:30px" /></td>
		   	</tr>
	      	<tr>
	      		<td  nowrap><label   for="bean.unit">单位</label></td>
				<td>
					<select name="bean.unit"  id="bean.unit" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='unitList' insertEmpty='0'/>
					</select>
				</td>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return thingsScript.submitFormOnEdit(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Things.js"></script> 
<script type="text/javascript">
    var thingsScript = new ThingsScript();  
    $(function() {    
    	thingsScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
    	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
		var formJson = jQuery.parseJSON(jsonContentObj.text()); 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	});
	function modalDialogLoadEvent() {
	} 
</script>      
</form> 
</body>
</html>