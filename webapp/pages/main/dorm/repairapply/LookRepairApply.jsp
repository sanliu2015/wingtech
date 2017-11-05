<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>查看${appReqeustContext.appName}</title> 

</head>
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doEndWork.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  	<input type="hidden" name="bean.id" /> 
  	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
        		<td nowrap><label   for="bean.repairDate">报修日期<span style="color:red">*</span></label></td>
			   	<td  ><input name="bean.repairDate" id="bean.repairDate" class="easyui-textbox" style="width:126px;height:28px" readonly></td>
				<td  nowrap><label   for="bean.roomId">报修单位<span style="color:red">*</span></label></td>
				<td>
					<input type="hidden" name="bean.roomId" id="bean.roomId" />
					<input type="hidden" name="bean.buildingName" id="bean.buildingName" />
					<input name="bean.roomNumber"  id="bean.roomNumber" class="easyui-textbox" readonly style="width:150px;height:30px" />   
				</td>
				<td  nowrap><label   for="bean.repairerId">维修人员<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.repairerName" id="bean.repairerName" class="easyui-textbox" readonly style="width:150px;height:30px" />
				</td>
		   	</tr >
		    <tr>
		    	<td  nowrap><label   for="bean.applyer">报修人员<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.applyer" id="bean.applyer" class="easyui-textbox" readonly style="width:150px;height:30px" />
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
			   	<td colspan="3" ><input class="easyui-textbox" readonly name="bean.repairContent" id="bean.repairContent" style="width:500px;height:30px;"></td>
	      		<td  nowrap><label   for="bean.number">报修单号<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.number" id="bean.number" class="easyui-textbox" readonly style="width:150px;height:30px" />
				</td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5"><input class="easyui-textbox" readonly name="bean.description" style="width:500px;height:30px;"></td>
	      	</tr>
	      	<tr>
        		<td nowrap><label   for="bean.endWorkDate">完工日期</label></td>
			   	<td  ><input name="bean.endWorkDate" id="bean.endWorkDate" readonly class="easyui-textbox" style="width:150px;height:30px"></td>
				<td  nowrap><label   for="bean.repairFee">维修费用</label></td>
				<td>
					<input name="bean.repairFee" id="bean.repairFee" class="easyui-textbox" readonly style="width:150px;height:30px" />
				</td>
				<td  nowrap><label   for="bean.scoreResult">满意度评分</label></td>
				<td>
					<span id="scoreResult"></span>
					<input name="bean.scoreResult" id="bean.scoreResult" type="hidden"  />
				</td>
	      	</tr>
		</table>
	</div> 
<div id="tt" class="easyui-tabs">
 	<div title="分摊费用">	
	<table id="damageListGrid" class="easyui-datagrid" style="width:100%" data-options="rownumbers:true,toolbar:'#damageListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'empNumber',width:150">工号</th>
                <th data-options="field:'empName',width:150">姓名</th>
                <th data-options="field:'amount',width:100">金额</th>
                <th data-options="field:'reason',width:200">原因</th>
				<th data-options="field:'description',width:300">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:repairApplyScript.hiddenColumnFormatDamage,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:repairApplyScript.hiddenColumnFormatDamage,hidden:true"></th> 
				<th data-options="field:'employeeId',width:10, formatter:repairApplyScript.hiddenColumnFormatDamage,hidden:true"></th>
			</tr>
		</thead>
	</table>
	</div> 
 	<div title="设备信息">
    <table id="dtlListGrid" class="easyui-datagrid" style="width:100%" data-options="rownumbers:true">
		<thead>
			<tr>
                <th data-options="field:'name',width:200">名称</th>
				<th data-options="field:'description',width:300">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:repairApplyScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:repairApplyScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
	</table>
	</div>
</div>	
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>
</form>
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript" src="<ts:base ref='path'/>/RepairApply.js"></script> 
<script type="text/javascript" src="${contextPath}/scripts/jquery/raty/jquery.raty.js"></script> 
<script type="text/javascript">
    var repairApplyScript=new RepairApplyScript();   
    var dtlList = ${dtlList};
    var damageList = ${damageList};
    var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		repairApplyScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
		$('#scoreResult').raty({
			hints: ['极不满意','不满意','一般', '满意', '非常满意'],
			path: "${contextPath}/scripts/jquery/raty/images",
			readOnly: true,
			score:'${form.bean.scoreResult}'
		});
	});
	function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			var tmp = {"dtlList" : dtlList[i]}; 
			repairApplyScript.insertDatagridRow(tmp);   
		} 
		for(var i=0;i<damageList.length;i++){ 
			var tmp = {"damageList" : damageList[i]}; 
			repairApplyScript.insertDamageListRow(tmp);   
		} 
	} 
</script>
</body>
</html>