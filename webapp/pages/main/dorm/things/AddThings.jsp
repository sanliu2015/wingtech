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
	<div id="pagePanel" title="物品信息" class="easyui-panel" width="100%"  style="padding:10px">
	<table id="dtlListGrid" class="easyui-datagrid" data-options="height:300,rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:thingsScript.removeRowFormat">操作</th>
                <th data-options="field:'name',width:150, formatter:thingsScript.textInputFormat">名称<span style="color:red">*</span></th>
				<th data-options="field:'number',width:150,formatter:thingsScript.textInputFormat">编号</th>
				<th data-options="field:'unit',width:90,formatter:thingsScript.comboboxFormat">单位</th>
				<th data-options="field:'description',width:250, formatter:thingsScript.textInputFormat">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:thingsScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:thingsScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
	</table> 
	</div> 
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="thingsScript.appendRow(this);">增加</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return thingsScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Things.js"></script> 
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript">
    var thingsScript = new ThingsScript();  
    thingsScript.unitList = '<ts:forEach name="unitList" insertEmpty="0" toJson="1"/>'
	function modalDialogLoadEvent() {
		thingsScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>