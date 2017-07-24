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
  	<input type="hidden" name="bean.id" /> 
 	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<div style="padding:10px;padding-left:5px">
	<table cellpadding="0" cellspacing="0" class="baseForm-table">
        <tr>
			<td  nowrap><label   for="bean.name">方案名称</label></td>
			<td><input name="bean.name" id="bean.name" class="easyui-textbox"  style="width:150px;height:30px" /></td>
			<td  nowrap><label   for="bean.number">唯一编码<span style="color:red">*</span></label></td>
			<td><input name="bean.number" id="bean.number" class="easyui-textbox"  style="width:150px;height:30px" /></td>
			<td  nowrap><label   for="bean.templateName">模板文件名称</label></td>
			<td><input name="bean.templateName" id="bean.templateName" class="easyui-textbox"  style="width:150px;height:30px" /></td>
	    </tr>
	    <tr>
			<td  nowrap><label   for="bean.beforeSp">导入前执行</label></td>
			<td colspan="5"><input name="bean.beforeSp" id="bean.beforeSp" class="easyui-textbox"  data-options="multiline:true" style="width:950px;height:30px" /></td>
	    </tr>
	    <tr>
			<td  nowrap><label   for="bean.middleSp">导入时执行</label></td>
			<td colspan="5"><input name="bean.middleSp" id="bean.middleSp" class="easyui-textbox"  data-options="multiline:true" style="width:950px;height:30px" /></td>
	    </tr>
	    <tr>
			<td  nowrap><label   for="bean.afterSp">导入后执行</label></td>
			<td colspan="5"><input name="bean.afterSp" id="bean.afterSp" class="easyui-textbox" data-options="multiline:true" style="width:950px;height:30px" /></td>
	    </tr>
	    <tr>
			<td  nowrap><label   for="bean.description">备注</label></td>
			<td colspan="5"><input name="bean.description" id="bean.description" class="easyui-textbox" data-options="multiline:true" style="width:950px;height:30px" /></td>
	    </tr>
	</table> 
    <table id="dtlListGrid" class="easyui-datagrid" data-options="height:300,rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:importPlanScript.removeRowFormat">操作</th>
                <th data-options="field:'title',width:150, formatter:importPlanScript.textInputFormat">标题名称<span style="color:red">*</span></th>
				<th data-options="field:'colName',width:150,formatter:importPlanScript.textInputFormat">字段名<span style="color:red">*</span></th>
				<th data-options="field:'colType',width:90,formatter:importPlanScript.comboboxFormat">字段类型<span style="color:red">*</span></th>
				<th data-options="field:'description',width:250, formatter:importPlanScript.textInputFormat">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:importPlanScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:importPlanScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'hdrId',width:10, formatter:importPlanScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
	</table> 
	</div>
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="importPlanScript.appendRow(this);">增加</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return importPlanScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div>    
</form>
<script type="text/javascript" src="<ts:base ref='path'/>/ImportPlan.js"></script> 
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript">
    var importPlanScript = new ImportPlanScript();  
    importPlanScript.colTypeList = '<ts:forEach name="colTypeList" insertEmpty="0" toJson="1"/>'
	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	var dtlList = formJson.dtlList;
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		importPlanScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			dtlList[i].recordOperateStatus="update"; 
			var tmp = {"dtlList" : dtlList[i]}; 
			importPlanScript.insertDatagridRow(tmp);   
		} 
	} 
	
</script>
</body>
</html>