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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doSubmit.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post" enctype="multipart/form-data">  
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
	<input name="planId" id="planId" value="${planId}" type="hidden">
	<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        <tr>
           	<td nowrap><label  for="sheetNo">sheet编号</label><span style="color:red">*</span></td>
			<td ><input name="sheetNo" id="sheetNo" class="easyui-numberspinner" style="height:30px;width:150px" value="1" data-options="min:1,precision:0" /></td> 
			<td nowrap><label  for="headNo">标题行号</label><span style="color:red">*</span></td>
			<td ><input name="headNo" id="headNo" class="easyui-numberspinner" style="height:30px;width:150px" value="${headNo}" data-options="min:1,precision:0" /></td> 
        </tr>
        <tr>
           	<td nowrap><label  for="excelFile">excel文件</label><span style="color:red">*</span></td>
           	<td colspan="3">
           		<input id="excelFile" name="excelFile" type="text" style="width:300px;height:30px">
           		<a href="${contextPath}/core/downloadFileService/stream/downloadFile.do?number=${templateName}&name=/resource/template/importexcel/${templateName}" >模板下载</a>
           	</td>
        </tr> 
	</table> 
	</div> 
	<table id="errorListGrid" class="easyui-datagrid" style="width:100%;height:300px" title="验证出错信息"  
 		data-options="rownumbers:true,striped:true,singleSelect:false,idField:'id',collapsible:true,toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'error',width:100">出错提示</th>
					<th data-options="field:'unit',width:100">入住单位</th>
                    <th data-options="field:'empName',width:70">姓名 </th>
                    <th data-options="field:'empNumber',width:90">工号</th>
                    <th data-options="field:'idNo',width:150">身份证</th>
                    <th data-options="field:'sex',width:40">性别</th>
                    <th data-options="field:'parentDepName',width:150">所在部门</th>
                    <th data-options="field:'depName',width:150">科室</th>
                    <th data-options="field:'posName',width:100">职位</th>
                    <th data-options="field:'roomNumber',width:70">房间编码</th>
				</tr>
			</thead>
	</table>
	<div id="tb">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel',plain:true" onclick="exportExcel('入住出错信息', $('#errorListGrid'));">导出</a>
	</div>
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return checkInScript.submitFormOnImport(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js"></script> 
<script type="text/javascript">
    var checkInScript = new CheckInScript(); 
    $(function() {
		$('#excelFile').filebox({    
		    buttonText: '选择文件', 
		    buttonAlign: 'right' 
		});
	}); 
	function modalDialogLoadEvent() {
		checkInScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>