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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td nowrap><label   for="bean.subsidies">补贴金额<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.subsidies" id="bean.subsidies" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="model">模式选择<span style="color:red">*</span></label></td>
			   	<td  >
			   		<input type="radio" name="model" value="pos" />按职务
			   		<input type="radio" name="model" value="emp" />按人员
			   	</td>
		   	</tr >
          	<tr id="emp">
			   	<td  nowrap><label   for="bean.empNumber">工号</label></td>
				<td><input name="bean.empNumber" id="bean.empNumber" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.empName">姓名<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.employeeId" id="bean.employeeId" type="hidden" />
					<input name="bean.empName" id="bean.empName" class="easyui-textbox" style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return subsidiesScript.chooseEmployee(this);"  plain='true'></a>
				</td>
	      	</tr>
	      	<tr id="pos">
	      		<td nowrap><label   for="bean.positionId">职务</label></td>
	      		<td colspan="3">
	      			<input name="bean.positionId" id="bean.positionId" class="easyui-textbox" style="width:150px;height:30px"/>
	      		</td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:300px"></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return subsidiesScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Subsidies.js"></script> 
<script type="text/javascript">
    var subsidiesScript = new SubsidiesScript();  
	function modalDialogLoadEvent() {
		$("tr[id='pos']").hide();
		$("tr[id='emp']").hide();
		$("#bean\\.positionId").combotree({
			multiple:false,
			idFiled:'id',    
	        textFiled:'name',
			parentField:'pid',
			data:${posList},
			onSelect:function(node) {
				$("#bean\\.positionId").val(node.id);
			}
		});
		
		$(":radio").change(function(){
			var checkedVal = $(":radio:checked").val();
			if (checkedVal == "pos") {
				$("tr[id='pos']").show();
				$("tr[id='emp']").hide();
				$("#bean\\.employeeId").val("");
				$("#bean\\.empNumber").val("");
				$("#bean\\.empName").val("");
			} else {
				$("tr[id='pos']").hide();
				$("tr[id='emp']").show();
				$("#bean\\.positionId").val("");
				$("#bean\\.positionId").combotree("clear");
			}
		});
		subsidiesScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>