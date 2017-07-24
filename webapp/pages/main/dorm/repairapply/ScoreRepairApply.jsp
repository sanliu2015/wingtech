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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doScore.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  	<input type="hidden" name="bean.id" /> 
  	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
        		<td  nowrap><label   for="bean.scoreResult">满意度评分<span style="color:red">*</span></label></td>
				<td>
					<span id="scoreResult"></span>
					<input name="bean.scoreResult" id="bean.scoreResult" type="hidden"  />
				</td>
		   	</tr >
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
			   	<td  ><input name="bean.repairDate" id="bean.repairDate" readonly class="easyui-textbox" style="width:150px;height:30px"></td>
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
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return repairApplyScript.submitFormScore(this);">确定</a>  
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
    var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		repairApplyScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
		$('#scoreResult').raty({
			hints: ['极不满意','不满意','一般', '满意', '非常满意'],
			path: "${contextPath}/scripts/jquery/raty/images"
		});
	});
	function modalDialogLoadEvent() {
	} 
</script>
</body>
</html>