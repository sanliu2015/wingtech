<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>完工${appReqeustContext.appName}</title> 

</head>
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doEndWork.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  	<input type="hidden" name="bean.id" /> 
  	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
        		<td nowrap><label   for="bean.endWorkDate">完工日期<span style="color:red">*</span></label></td>
			   	<td  ><input name="bean.endWorkDate" id="bean.endWorkDate" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentDate}"></td>
				<td  nowrap><label   for="bean.repairFee">维修费用</label></td>
				<td>
					<input name="bean.repairFee" id="bean.repairFee" class="easyui-numberbox" data-options="min:0,precision:2" style="width:150px;height:30px" />
					<a href="javascript:void(0)" onClick="calFee();">均摊</a>
				</td>
				<td nowrap><label   for="bean.autoScoreDate">评分截止日期<span style="color:red">*</span></label></td>
			   	<td  ><input name="bean.autoScoreDate" id="bean.autoScoreDate" class="easyui-my97" style="width:126px;height:28px" ></td>
	      	</tr>
	      	<tr>
        		<td nowrap><label   for="bean.notifyFlag">完工短信通知<span style="color:red">*</span></label></td>
			   	<td  ><input type="radio" name="bean.notifyFlag" value="1" checked="true" />是<input type="radio" name="bean.notifyFlag" value="0" />否</td>
			   	<td  nowrap class="dis"><label   for="bean.empPhone">申请人手机号<span style="color:red">*</span></label></td>
				<td class="dis">
					<input name="bean.empPhone" id="bean.empPhone" class="easyui-textbox" style="width:150px;height:30px" />
				</td>
	      	</tr>
        	<tr>
        		<td  nowrap><label   for="bean.employeeId">报修人员<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.employeeName" id="bean.employeeName" class="easyui-textbox" readonly style="width:150px;height:30px" />
					<input name="bean.employeeId" id="bean.employeeId" type="hidden"  />
				</td>
          		<td  nowrap><label   for="bean.buildingId">楼栋位置<span style="color:red">*</span></label></td>
				<td>
					<select name="bean.buildingId"  id="bean.buildingId" class="easyui-combobox" data-options="editable:false,disabled:true" style="width:150px;height:30px">   
                		<ts:forEach name='buildingIdList' insertEmpty='0' />
					</select>
				</td>
				<td  nowrap><label   for="bean.repairType">报修类别<span style="color:red">*</span></label></td>
				<td>
					<select name="bean.repairType"  id="bean.repairType" class="easyui-combobox" data-options="editable:false,disabled:true" style="width:150px;height:30px">   
                		<ts:forEach name='repairTypeList' insertEmpty='0' />
					</select>
				</td>
		   	</tr >
		    <tr>
	      		<td  nowrap><label   for="bean.repairerId">维修人员<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.repairerName" id="bean.repairerName" class="easyui-textbox" readonly style="width:150px;height:30px" />
					<input name="bean.repairerId" id="bean.repairerId" type="hidden"  />
				</td>
	      		<td nowrap><label   for="bean.repairDate">报修日期<span style="color:red">*</span></label></td>
			   	<td  ><input name="bean.repairDate" id="bean.repairDate" readonly class="easyui-textbox" style="width:126px;height:28px"></td>
	      		<td  nowrap><label   for="bean.number">报修单号<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.number" id="bean.number" class="easyui-textbox" readonly style="width:150px;height:30px" />
				</td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.repairContent">报修内容<span style="color:red">*</span></label></td>
			   	<td colspan="5" ><input class="easyui-textbox" readonly name="bean.repairContent" id="bean.repairContent" style="width:500px;height:30px;"></td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5"><input class="easyui-textbox" readonly name="bean.description" style="width:500px;height:30px;"></td>
	      	</tr>
		</table>
	</div> 
<div id="tt" class="easyui-tabs">
 	<div title="分摊费用">	
	<table id="damageListGrid" class="easyui-datagrid" style="width:100%" data-options="rownumbers:true,toolbar:'#damageListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:repairApplyScript.removeRowFormatDamage">删除</th>
				<th data-options="field:'empNumber',width:150">工号</th>
                <th data-options="field:'empName',width:150">姓名</th>
                <th data-options="field:'amount',width:100,formatter:repairApplyScript.numberboxInputFormatDamage">金额</th>
                <th data-options="field:'reason',width:200, formatter:repairApplyScript.textInputFormatDamage">原因</th>
				<th data-options="field:'description',width:300, formatter:repairApplyScript.textInputFormatDamage">备注</th>                    
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
	<div id="damageListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="repairApplyScript.chooseCheckInEmp(this);">选择分摊人员</a>
	</div> 
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return repairApplyScript.submitFormEndWork(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>
</form>
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript" src="<ts:base ref='path'/>/RepairApply.js?1"></script> 
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
		$(":radio").click(function(){
			if ($(this).val() == "0") {
				$(".dis").hide();
			} else {
				$(".dis").show();
			}
		});
	} 
	function calFee() {
		var repairFee = $("#bean\\.repairFee").numberbox("getValue");
		if (repairFee > 0) {
			//var $damageListTr = $("#damageListGrid").datagrid("getPanel").find("div[class='datagrid-view2']").children("div[class='datagrid-body']").find("tr");
			var $damageListTr = $("#damageListGrid").datagrid("getPanel").find('div[class="datagrid-body"]').children("table").find("tr:visible").filter(".datagrid-row");
			var len = $damageListTr.length;
			if (len == 0) {
				$.messager.alert("警告", "没有要分摊的人员!");
			} else {
				var avgVal = repairFee/len.toFixed(2);
				$.each($damageListTr,function(i,row) {
					$(this).find('td[field="amount"]').find("input[name='damageList.amount']").val(avgVal);
				});
			}
		}
	}
</script>
</body>
</html>