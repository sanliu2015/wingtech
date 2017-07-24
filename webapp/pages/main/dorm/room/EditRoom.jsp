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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  	<input type="hidden" name="bean.id" /> 
  	<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.name">房间名称<span style="color:red">*</span></label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox" style="width:150px;height:30px"  /></td>
				<td  nowrap><label   for="bean.number">房间编号<span style="color:red">*</span></label></td>
				<td><input name="bean.number" id="bean.number" class="easyui-textbox"   value=""style="width:150px;height:30px"  /></td>
		   		<td  nowrap><label   for="bean.floorId">楼层位置<span style="color:red">*</span></label></td>
				<td><input name="bean.floorId" id="bean.floorId" class="easyui-textbox"  style="width:150px;height:30px" /></td>
		   	</tr >
		    <tr>
			   	<td nowrap><label   for="bean.waterFee">水费单价</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.waterFee" id="bean.waterFee" data-options="min:0,precision:2"  style="width:150px;height:30px"></td>
			   	<td nowrap><label   for="bean.powerFee">电费单价</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.powerFee" id="bean.powerFee" data-options="min:0,precision:2"  style="width:150px;height:30px"></td>
			   	<td nowrap><label   for="bean.gasFee">气费单价</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.gasFee"  id="bean.gasFee" data-options="min:0,precision:2"  style="width:150px;height:30px"></td>
	      	</tr>
	      	<tr>
			   	<td nowrap><label   for="bean.sharedFee">公摊水电费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.sharedFee"  id="bean.sharedFee" data-options="min:0,precision:2"  style="width:150px;height:30px"></td>
	      		<td  nowrap><label   for="bean.parentName">房间租金</label></td>
				<td><input name="bean.rentStandard" id="bean.rentStandard" class="easyui-numberbox" data-options="min:0,precision:2,editable:true" style="width:150px;height:30px" /></td>
	      		<td  nowrap><label   for="bean.parentId">上级房间</label></td>
				<td><input name="bean.parentId" id="bean.parentId" class="easyui-textbox"  style="width:150px;height:30px" /></td>
	      	</tr>          
	      	<tr>
	      		
				<td  nowrap><label   for="bean.bigNumber">最大可容纳人数</label></td>
				<td><input name="bean.bigNumber" id="bean.bigNumber" class="easyui-numberspinner" data-options="min:1,max:100,editable:true" style="width:150px;height:30px" /></td>
	      		<td nowrap><label   for="bean.type">属性</label></td>
	      		<td>
	      			<select name="bean.type"  id="bean.type" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='typeList' insertEmpty='1' value="" />
					</select>
	      		</td>
	      		<td nowrap><label   for="bean.corpBear">是否公司承担房租</label></td>
	      		<td>
	      			<select name="bean.corpBear"  id="bean.corpBear" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='whetherList' insertEmpty='0'  value="0" />
					</select>
	      		</td>
	      		<%-- <td nowrap><label   for="bean.disabled">状态</label></td>
	      		<td>
	      			<select name="bean.disabled"  id="bean.disabled" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='disabledList' insertEmpty='1'  />
					</select>
	      		</td> --%>
	      	</tr>
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5"><input class="easyui-textbox" name="bean.description" style="width:500px;height:30px;"></td>
	      	</tr>
	      	</tr>
		</table>
	</div> 
    
    <table id="dtlListGrid" class="easyui-datagrid" style="width:100%" data-options="rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:roomScript.removeRowFormat">操作</th>
                <th data-options="field:'thingsName',width:150">物品名称</th>
				<th data-options="field:'thingsUnit',width:90">物品单位</th>
				<th data-options="field:'thingsQty',width:70, formatter:roomScript.numberspinnerInputFormat">物品个数</th>
				<th data-options="field:'description',width:200, formatter:roomScript.textInputFormat">备注</th>                    
				<th data-options="field:'principalName',width:150, formatter:roomScript.principalNameFormat">负责人</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:roomScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'thingsId',width:10, formatter:roomScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:roomScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'principalId',width:10, formatter:roomScript.hiddenColumnFormat,hidden:true"></th> 
				
			</tr>
		</thead>
	</table> 
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="roomScript.chooseThings(this);">添加物品</a>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return roomScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div>    
</form>
<script type="text/javascript" src="<ts:base ref='path'/>/Room.js"></script> 
<style>
.datagrid-row {
        height: 27px;
    }
</style>
<script type="text/javascript">
    var roomScript=new RoomScript();  
    var dtlList = ${dtlList};
    var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		roomScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	});
	function modalDialogLoadEvent() {
		$("#bean\\.parentId").combotree({
			multiple:false,
			idFiled:'id',    
	        textFiled:'name',
			parentField:'parentId',
			data:<ts:forEach name="roomList" toJson="1"/>
		});
		$("#bean\\.floorId").combotree({
			multiple:false,
			idFiled:'id',    
	        textFiled:'name',
			parentField:'parentId',
			data:<ts:forEach name="parentList" toJson="1"/>,
			onBeforeSelect:function(node) {
				if (node.kind != "2") {
					$.messager.alert("警告", "只能选择楼层!");
					return false;
				}
			},
			onSelect:function(node) {
				$("#bean\\.waterFee").numberbox("setValue", node.waterFee);
				$("#bean\\.powerFee").numberbox("setValue", node.powerFee);
				$("#bean\\.gasFee").numberbox("setValue", node.gasFee);
				$("#bean\\.sharedFee").numberbox("setValue", node.sharedFee);
				$("#bean\\.cleanFee").numberbox("setValue", node.cleanFee);
			}
		});
		for(var i=0;i<dtlList.length;i++){ 
			var tmp = {"dtlList" : dtlList[i]}; 
			roomScript.insertDatagridRow(tmp);   
		} 
	} 
</script>
</body>
</html>