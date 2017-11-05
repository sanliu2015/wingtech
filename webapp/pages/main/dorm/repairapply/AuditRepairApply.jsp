<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>修改${appReqeustContext.appName}</title> 

</head>
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doAudit.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  	<input type="hidden" name="bean.id" id="bean.id"/> 
  	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
				<td nowrap><label   for="bean.repairDate">报修日期<span style="color:red">*</span></label></td>
			   	<td  ><input name="bean.repairDate" id="bean.repairDate" class="easyui-my97" style="width:126px;height:28px"></td>
				<td  nowrap><label   for="bean.roomId">报修单位<span style="color:red">*</span></label></td>
				<td>
					<input type="hidden" name="bean.roomId" id="bean.roomId" />
					<input type="hidden" name="bean.buildingName" id="bean.buildingName" />
					<input name="bean.roomNumber"  id="bean.roomNumber" class="easyui-textbox" readonly style="width:150px;height:30px" />   
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return repairApplyScript.chooseRoom(this);"  plain='true'></a>
                		
				</td>
				<td  nowrap><label   for="bean.repairerId">维修人员<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.repairerName" id="bean.repairerName" class="easyui-textbox" readonly style="width:150px;height:30px" />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return repairApplyScript.chooseRepairer(this);"  plain='true'></a>
					<input name="bean.repairerId" id="bean.repairerId" type="hidden"  />
				</td>
		   	</tr >
		    <tr>
		    	<td  nowrap><label   for="bean.applyer">报修人员<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.applyer" id="bean.applyer" class="easyui-textbox" style="width:150px;height:30px" />
				</td>
				<td  nowrap><label   for="bean.contactPhone">联系电话<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.contactPhone" id="bean.contactPhone" class="easyui-textbox" style="width:150px;height:30px" />
				</td>
				<td  nowrap><label   for="bean.repairType">报修类别<span style="color:red">*</span></label></td>
				<td>
					<select name="bean.repairType"  id="bean.repairType" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='repairTypeList' insertEmpty='0' />
					</select>
				</td>
	      		
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.repairContent">报修内容<span style="color:red">*</span></label></td>
			   	<td colspan="5" ><input class="easyui-textbox" name="bean.repairContent" id="bean.repairContent" style="width:500px;height:30px;"></td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5"><input class="easyui-textbox" name="bean.description" style="width:500px;height:30px;"></td>
	      	</tr>
		</table>
	</div> 
    
    <table id="dtlListGrid" class="easyui-datagrid" style="width:100%" data-options="rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:repairApplyScript.removeRowFormat">操作</th>
                <th data-options="field:'name',width:200, formatter:repairApplyScript.textInputFormat">名称</th>
				<th data-options="field:'description',width:300, formatter:repairApplyScript.textInputFormat">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:repairApplyScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:repairApplyScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
	</table> 
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="repairApplyScript.appendRow(this);">添加报修设备</a>
	</div> 
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return repairApplyScript.submitForm(this);">审核通过</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>
</form>
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript" src="<ts:base ref='path'/>/RepairApply.js"></script> 
<script type="text/javascript">
    var repairApplyScript=new RepairApplyScript();   
    var dtlList = ${dtlList};
    var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		repairApplyScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			var tmp = {"dtlList" : dtlList[i]}; 
			repairApplyScript.insertDatagridRow(tmp);   
		}
		
		$('input,select,textarea',$('form[name="${appReqeustContext.appKey}Form"]')).prop('readonly',true);
	} 
</script>
</body>
</html>