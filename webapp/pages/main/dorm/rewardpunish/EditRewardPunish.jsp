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
	<input name="bean.employeeId" id="bean.employeeId" type="hidden" />
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.occurDate">日期<span style="color:red">*</span></label></td>
				<td><input name="bean.occurDate" id="bean.occurDate" class="easyui-my97" style="width:126px;height:28px"  /></td>
				<td  nowrap><label   for="bean.occurTime">时间</label></td>
				<td><input name="bean.occurTime" id="bean.occurTime" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'HH:MM:ss'})" /></td>
				<td  nowrap><label   for="bean.empName">姓名<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox"   style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return rewardPunishScript.chooseEmployee(this);"  plain='true'></a>
				</td>
		   	</tr >
		   	<tr>
		   		<td  nowrap><label   for="bean.empNumber">工号</label></td>
				<td><input name="bean.empNumber" id="bean.empNumber" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.depName">部门/科室</label></td>
				<td><input name="bean.depName" id="bean.depName" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.empNumber">岗位</label></td>
				<td><input name="bean.posName" id="bean.posName" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
		   	</tr>
		   	<tr>
		   		<td nowrap><label   for="bean.reason">违规违纪内容<span style="color:red">*</span></label></td>
			   	<td colspan="5" ><input class="easyui-textbox" name="bean.reason" id="bean.reason" style="width:400px;height:30px" /></td>
		   	</tr>
          	<tr>
			   	<td nowrap><label   for="bean.executivePunish">行政处罚</label></td>
			   	<td  >
			   		<select name="bean.executivePunish" id="bean.executivePunish" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='executivePunishList' insertEmpty='1'/>
					</select>
			   	</td>
			   	<td nowrap><label   for="bean.amount">经济处罚</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.amount" id="bean.amount" data-options="min:0,precision:0" style="width:150px;height:30px" /></td>
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
				<th data-options="field:'operateField',width:40,formatter:rewardPunishScript.removeRowFormat">操作</th>
                <th data-options="field:'empName',width:150">姓名</th>
				<th data-options="field:'empNumber',width:150">工号</th>                    
				<th data-options="field:'depName',width:150">部门/科室</th> 
				<th data-options="field:'amount',width:90,formatter:rewardPunishScript.numberboxInputFormat">奖励金额</th>
				<th data-options="field:'reason',width:200, formatter:rewardPunishScript.textInputFormat">奖励原因</th>    
				<th data-options="field:'description',width:200, formatter:rewardPunishScript.textInputFormat">备注</th>    
				<th data-options="field:'employeeId',width:10, formatter:rewardPunishScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:rewardPunishScript.hiddenColumnFormat,hidden:true"></th>
				<th data-options="field:'recordOperateStatus',width:10, formatter:rewardPunishScript.hiddenColumnFormat,hidden:true"></th>  
			</tr>
		</thead>
	</table> 
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="rewardPunishScript.chooseRewardEmp(this);">添加奖励人员</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return rewardPunishScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript" src="<ts:base ref='path'/>/RewardPunish.js"></script> 
<script type="text/javascript">
    var rewardPunishScript = new RewardPunishScript();  
    var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
    var dtlList= ${dtlList};
    $(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		rewardPunishScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
		for(var i=0;i<dtlList.length;i++){ 
			var tmp = {"dtlList" : dtlList[i]}; 
			rewardPunishScript.insertDatagridRow(tmp);   
		} 
	} 
</script>      
</form> 
</body>
</html>