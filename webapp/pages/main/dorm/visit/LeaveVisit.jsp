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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doLeave.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input type="hidden" name="bean.id" /> 
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
        		<td  nowrap><label   for="bean.doorOut">离开厂门<span style="color:red">*</span></label></td>
        		<td>
        			<select name="bean.doorOut"  id="bean.doorOut" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='doorsList' insertEmpty='1' value="" />
					</select>
        		</td>
          		<td  nowrap><label   for="bean.leaveDate">离开日期<span style="color:red">*</span></label></td>
				<td><input name="bean.leaveDate" id="bean.leaveDate" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentDate}"  /></td>
				<td  nowrap><label   for="bean.leaveTime">离开时间</label></td>
				<td><input name="bean.leaveTime" id="bean.leaveTime" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentTime}" onfocus="WdatePicker({dateFmt:'HH:MM:ss'})" /></td>
		   	</tr >
		   	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
	</div>
	<div>
		<div style="width:45%;float:left">
			<table id="dtlListGrid" class="easyui-datagrid" data-options='rownumbers:true,title:"带入物品",data:${dtlList}'>
			<thead>
				<tr>
	                <th data-options="field:'name',width:150">名称</th>
					<th data-options="field:'quantity',width:50">数量</th>                    
					<th data-options="field:'description',width:200">备注</th>    
				</tr>
			</thead>
			</table> 
		</div>
		<div style="width:50%;float:left">
			<table id="leaveListGrid" class="easyui-datagrid" data-options="rownumbers:true,title:'带出物品',toolbar:'#leaveListGridToolbar'">
			<thead>
				<tr>
					<th data-options="field:'operateField',width:40,formatter:visitScript.removeRowFormatLeave">操作</th>
	                <th data-options="field:'name',width:150,formatter:visitScript.textInputFormatLeave">名称</th>
					<th data-options="field:'quantity',width:50,formatter:visitScript.textInputFormatLeave">数量</th>                    
					<th data-options="field:'description',width:200, formatter:visitScript.textInputFormatLeave">备注</th>    
					<th data-options="field:'recordOperateStatus',width:10, formatter:visitScript.hiddenColumnFormatLeave,hidden:true"></th>  
					<th data-options="field:'id',width:10, formatter:visitScript.hiddenColumnFormatLeave,hidden:true"></th> 
				</tr>
			</thead>
			</table> 
		</div>
	</div>
	
	 
	<div id="leaveListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="visitScript.appendRowLeave(this);">添加物品</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return visitScript.submitFormOnLeave(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript" src="<ts:base ref='path'/>/Visit.js"></script> 
<script type="text/javascript">
    var visitScript = new VisitScript();  
    var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
    $(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		visitScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			var tmp = {"leaveList" : dtlList[i]}; 
			visitScript.insertDatagridRowLeave(tmp);   
		} 
	} 
</script>      
</form> 
</body>
</html>