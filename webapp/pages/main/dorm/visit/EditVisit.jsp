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
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<input type="hidden" name="bean.id" /> 
	<input name="bean.employeeId" id="bean.employeeId" type="hidden" />
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
        		<td  nowrap><label   for="bean.doorIn">进入厂门<span style="color:red">*</span></label></td>
        		<td>
        			<select name="bean.doorIn"  id="bean.doorIn" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='doorsList' insertEmpty='1' value="" />
					</select>
        		</td>
          		<td  nowrap><label   for="bean.visitDate">进入日期<span style="color:red">*</span></label></td>
				<td><input name="bean.visitDate" id="bean.visitDate" class="easyui-my97" style="width:126px;height:28px"   /></td>
				<td  nowrap><label   for="bean.visitTime">进入时间</label></td>
				<td><input name="bean.visitTime" id="bean.visitTime" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'HH:MM:ss'})" /></td>
		   	</tr >
		   	<tr>
		   		<td  nowrap><label   for="bean.visitor">姓名<span style="color:red">*</span></label></td>
				<td>
					<input class="easyui-textbox" name="bean.visitor" id="bean.visitor" style="width:150px;height:30px" />
				</td>
		   		<td nowrap><label   for="bean.visitCompany">所属单位</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitCompany" id="bean.visitCompany" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.visitorPhone">联系电话</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorPhone" id="bean.visitorPhone" style="width:150px;height:30px" /></td>
			   	<!-- <td nowrap><label   for="bean.visitorNum">来访人数</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorNum" id="bean.visitorNum" style="width:150px;height:30px" /></td> -->
		   	</tr>
		   	<tr>
		   		<td nowrap><label   for="bean.visitorIdCard">身份证</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorIdCard" id="bean.visitorIdCard" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.visitorCarNum">车牌号</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitorCarNum" id="bean.visitorCarNum" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.visitReason">来访事由</label></td>
			   	<td ><input class="easyui-textbox" name="bean.visitReason" id="bean.visitReason" style="width:150px;height:30px" /></td>
		   	</tr>
		   	<tr>
				<td  nowrap><label   for="bean.empName">被访人姓名</label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox"   style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return visitScript.chooseEmployee(this);"  plain='true'></a>
				</td>
				<td  nowrap><label   for="bean.depName">部门/科室</label></td>
				<td><input name="bean.depName" id="bean.depName" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
		   		<td  nowrap><label   for="bean.tempCard">临时厂牌编号</label></td>
				<td><input name="bean.tempCard" id="bean.tempCard" class="easyui-textbox"  style="width:150px;height:30px" /></td>
		   	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:400px"></td>
	      	</tr>  
		</table>
	</div> 
	<table id="dtlListGrid" class="easyui-datagrid" style="width:100%" data-options="rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:visitScript.removeRowFormat">操作</th>
                <th data-options="field:'name',width:150,formatter:visitScript.textInputFormat">名称</th>
				<th data-options="field:'quantity',width:150,formatter:visitScript.textInputFormat">数量</th>                    
				<th data-options="field:'description',width:200, formatter:visitScript.textInputFormat">备注</th>    
				<th data-options="field:'id',width:10, formatter:visitScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'recordOperateStatus',width:10, formatter:visitScript.hiddenColumnFormat,hidden:true"></th>  
			</tr>
		</thead>
	</table> 
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="visitScript.appendRow(this);">添加物品</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return visitScript.submitForm(this);">确定</a>  
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
    var dtlList= ${dtlList};
    $(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		visitScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			var tmp = {"dtlList" : dtlList[i]}; 
			visitScript.insertDatagridRow(tmp);   
		} 
	} 
</script>      
</form> 
</body>
</html>