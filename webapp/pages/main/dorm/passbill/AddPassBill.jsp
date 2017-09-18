<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts"%>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>添加${appReqeustContext.appName}</title>  
</head>
<body id="${appReqeustContext.appKey}Body"   >  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
<input name="bean.id" type="hidden"  /><input name="bean.auditStatus" type="hidden" value="0" /><input name="bean.status" type="hidden" value="0" /> 
	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
        		<td  nowrap><label   for="bean.employeeId">姓名<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.empName" id="bean.empName" class="easyui-textbox" readonly style="width:150px;height:30px" />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return passBillScript.chooseEmployee(this);"  plain='true'>在住</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return passBillScript.chooseEmployeeOut(this);"  plain='true'>退宿</a>
					<input name="bean.empId" id="bean.empId" type="hidden"  />
					<input name="damage.checkInId" id="damage.checkInId" type="hidden" />
				</td>
          		<td  nowrap><label   for="bean.empNumber">工号</label></td>
				<td>
					<input name="bean.empNumber" id="bean.empNumber" class="easyui-textbox" readonly style="width:150px;height:30px" />
				</td>
				<td nowrap><label   for="bean.passDate">日期<span style="color:red">*</span></label></td>
				<td  ><input name="bean.passDate" id="bean.passDate" class="easyui-my97" style="width:126px;height:28px" value="${appReqeustContext.currentDate}"></td>
		   	</tr >
		    <tr>
		    	<td  nowrap><label   for="bean.buildId">所在楼栋</label></td>
				<td>
					<input name="bean.buildName" id="bean.buildName" class="easyui-textbox" readonly style="width:150px;height:30px" />
					<input name="bean.buildId" id="bean.buildId" type="hidden"  />
				</td>
	      		<td  nowrap><label   for="bean.roomId">入住房间</label></td>
				<td>
					<input name="bean.roomName" id="bean.roomName" class="easyui-textbox" readonly style="width:150px;height:30px" />
					<input name="bean.roomId" id="bean.roomId" type="hidden"  />
				</td>
				<td nowrap><label   for="bean.reason">离厂原因<span style="color:red">*</span></label></td>
				<td>
					<select name="bean.reason"  id="bean.reason" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='reasonList' insertEmpty='0' value="" />
					</select>
				</td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.packageNum">行李数量</label></td>
				<td>
					<input name="bean.packageNum" id=bean.packageNum style="width:150px;height:30px" />
				</td>
	      		<td nowrap><label   for="bean.keyStatus">钥匙</label></td>
			   	<td>
			   		<select name="bean.keyStatus"  id="bean.keyStatus" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='statusList' insertEmpty='0' value="" />
					</select>
			   	</td>
			   	<td nowrap><label   for="bean.telStatus">空调遥控器</label></td>
			   	<td>
			   		<select name="bean.telStatus"  id="bean.telStatus" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='statusList' insertEmpty='0' value="" />
					</select>
			   	</td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3"><input class="easyui-textbox" name="bean.description" style="width:500px;height:30px;"></td>
	      	</tr>
		</table>
	</div> 
	<div class="easyui-tabs">
	<div title="贵重物品清单">
		<table id="dtlListGrid" class="easyui-datagrid" style="width:100%" data-options="title:'',rownumbers:true,toolbar:'#dtlListGridToolbar'">
			<thead>
				<tr>
					<th data-options="field:'operateField',width:40,formatter:passBillScript.removeRowFormat">操作</th>
	                <th data-options="field:'name',width:200, formatter:passBillScript.comboboxFormat">物品名称</th>
					<th data-options="field:'quantity',width:100, formatter:passBillScript.numberspinnerInputFormat">数量</th>
					<th data-options="field:'description',width:300, formatter:passBillScript.textInputFormat">备注</th>                                      
					<th data-options="field:'recordOperateStatus',width:10, formatter:passBillScript.hiddenColumnFormat,hidden:true"></th> 
					<th data-options="field:'id',width:10, formatter:passBillScript.hiddenColumnFormat,hidden:true"></th> 
				</tr>
			</thead>
		</table> 
		<div id="dtlListGridToolbar"  >
	        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="passBillScript.appendRow(this);">添加</a>
		</div> 
	</div>
	<div title="遗失顺坏费用" style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr>
			   	<td nowrap><label   for="damage.amount">扣款金额</label></td>
			   	<td  ><input class="easyui-numberbox" name="damage.amount" id="damage.amount" data-options="min:0,precision:0" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="damage.reason">扣款原因</label></td>
			   	<td  colspan="3">
			   		<input name="damage.reason" id="damage.reason" style="width:250px;height:30px" />
			   	</td>
	      	</tr>
		</table>
	</div>
	</div>
	
	
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return passBillScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>
<style>
.datagrid-row {
        height: 27px;
    }
</style>      
<script type="text/javascript" src="<ts:base ref='path'/>/PassBill.js?v1709182201"></script> 
<script type="text/javascript">
    var passBillScript=new PassBillScript();   
    passBillScript.nameList = '<ts:forEach name="nameList" insertEmpty="0" toJson="1"/>'
	function modalDialogLoadEvent() {   
    	$("#damage\\.reason").combobox({
			multiple:true,
			valueField:"name",    
	        textField:"name",
			data:<ts:forEach name="damageReasonList" toJson="1"/>,
		});
	}
	$(function() { 
		$("#bean\\.packageNum").combobox({
			valueField:'id',    
	        textFiled:'text',
	        data:<ts:forEach name="numList" toJson="1"/>
		});
	   passBillScript.initPage({ appKey:"${appReqeustContext.appKey}"});    
	    
	}); 
	 
</script>      
</form> 
</body>
</html>