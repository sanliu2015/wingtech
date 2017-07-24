<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<title>添加${appReqeustContext.appName}</title> 
</head>
<body id="${appReqeustContext.appKey}Body"   >  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.name">节点名称<span style="color:red">*</span></label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox" style="width:150px;height:30px"  /></input></td>
				<td  nowrap><label   for="bean.number">唯一编码<span style="color:red">*</span></label></td>
				<td><input name="bean.number" id="bean.number" class="easyui-textbox"   value=""style="width:150px;height:30px"  /></input></td>
		   		<td nowrap><label   for="bean.type">楼栋类型</label></td>
	      		<td>
	      			<select name="bean.type"  id="bean.type" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='typeList' insertEmpty='1' value="" />
					</select>
	      		</td>
		   	</tr >
		   	<tr>
          		<td  nowrap><label   for="bean.parentName">上级节点</label></td>
				<td><input name="bean.parentId" id="bean.parentId" class="easyui-textbox"  style="width:150px;height:30px" /></input></td>
		   		<td nowrap><label   for="bean.kind">节点属性<span style="color:red">*</span></label></td>
	      		<td>
	      			<select name="bean.kind"  id="bean.kind" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='kindList' insertEmpty='0' />
					</select>   
	      		</td>
		   		<td nowrap><label for="bean.principal.name">负责人</label></td>
				<td><input name="bean.principal.name" id="bean.principal.name" class="easyui-textbox" style="width:150px;height:30px" readonly/></input>
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return buildingScript.chooseEmployee(this);"  plain='true'></a>
					<input name="bean.principal.id" id="bean.principal.id" type="hidden"  />
				</td> 		
		   	</tr >
          	<tr>
			   	<td nowrap><label   for="bean.waterFee">水费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.waterFee" id="bean.waterFee" style="width:150px;height:30px" data-options="precision:2"></input></td>
			   	<td nowrap><label   for="bean.powerFee">电费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.powerFee" id="bean.powerFee" style="width:150px;height:30px" data-options="precision:2"></input></td>
			   	<td nowrap><label   for="bean.gasFee">气费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.gasFee"  id="bean.gasFee" style="width:150px;height:30px" data-options="precision:2"></input></td>
	      	</tr>
	      	<tr>
			   	<td nowrap><label   for="bean.sharedFee">公摊水电费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.sharedFee"  id="bean.sharedFee" style="width:150px;height:30px" data-options="precision:2"></input></td>
			   	<td nowrap><label   for="bean.cleanFee">卫生清洗费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.cleanFee"  id="bean.cleanFee" style="width:150px;height:30px" data-options="precision:2"></input></td>
	      		<td nowrap><label   for="bean.hasGas">是否供气</label></td>
	      		<td>
	      			<select name="bean.hasGas"  id="bean.hasGas" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='whetherList' insertEmpty='0'/>
					</select>
	      		</td>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5" ><input class="easyui-textbox" name="bean.description"   data-options="multiline:true" style="height:30px;width:400px"></input></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return buildingScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Building.js"></script> 
<script type="text/javascript">
    var buildingScript=new BuildingScript();  
	function modalDialogLoadEvent() {
		$("#bean\\.parentId").combotree({
			multiple:false,
			idFiled:'id',    
	        textFiled:'name',
			parentField:'parentId',
			data:${parentList}
		});
		var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
		var formJson = jQuery.parseJSON(jsonContentObj.text()); 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		buildingScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>