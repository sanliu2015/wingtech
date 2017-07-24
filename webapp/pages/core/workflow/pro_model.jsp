<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/c" prefix="c" %>
<%@ taglib uri="/tags/fn" prefix="fn" %>
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />  
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<title>模型工作区</title>       
<!--table的默认样式  -->       
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css">     
<!--easyui  -->
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">    
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>

<!--jquery验证插件  -->
<script type="text/javascript" src="${contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${contextPath}/js/additional-methods.js"></script>
<script type="text/javascript" src="${contextPath}/js/messages_zh.js"></script> 
<script type="text/javascript">
$(function(){
	  var validate = $("#fromid").validate({
	            submitHandler: function(form){
	                form.submit();   //提交表单   
	            	$('#dd').window('close');  // 关闭窗口
	            },
	           	errorPlacement: function(error, element) {
 					error.attr("style","color: red;position:absolute;float:left");
 			   	 	error.appendTo(element.parent());  
 				},
				rules:{
						name:{
					            required:true,
					           	customcheck:"^[\u4e00-\u9fa5]{0,}$",
					           	remote: "${contextPath}/core/workflowService/json/getexitstatus.do?status=name"
					    },
					    key:{
				            	required:true,
				           		customcheck:"^[A-Za-z0-9]+$",
				           		remote: "${contextPath}/core/workflowService/json/getexitstatus.do?status=key"
				    	}
				},
				messages:{
					name: {
						    remote:"该名称已存在!",
							customcheck:"请输入汉字(首尾无空格)温柔温柔温柔为的风格大范甘迪"
					 },
					 key: {
						    remote:"该KEY已存在!",
						    customcheck:"请输入数字或字母的组合(首尾无空格)"
					 }
				}
		});
	$('#dd').window('close');  // 关闭窗口

//*********************************************************************************************	数据表格 	 
	$('#dg').datagrid({
		title:'数据表格',
		iconCls:'',
		width:1120,                             
		height:540,                             
		nowrap: true,
		autoRowHeight: false,
		fitColumns:true,
		striped: true,
		checkbox:true,
		url:'${contextPath}/core/workflowService/json/getmodellist.do',  
		sortName: 'id',
		sortOrder: 'asc',                      
		remoteSort: false,
		selectOnCheck:true,  
		 singleSelect:"true",             
		pagination:true, 
		rownumbers:true,
		columns:[[   
				  {field:'',checkbox:true},
		          {field:'id',title:'id',width:120},
		          {field:'key',title:'key',width:435},
		          {field:'name',title:'名称',width:435},   
		          {field:'revision',title:'修订版',width:120}  
		      ]],
//**********************************************************************
		//onClickRow:function(rowIndex,rowData){
		//	alert(rowData.id);
	//	},
//******************************************************************	
		toolbar:[{
			id:'btnadd',
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				$('#btnadd').linkbutton('enable');
				add_dg();
			}
		},{
			id:'btnedit',
			text:'编辑',
			iconCls:'icon-edit',
			handler:function(){
				$('#btnedit').linkbutton('enable');
				edit_dg();
			}
		},{
			id:'btnremove',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				$('#btnremove').linkbutton('enable');
				dele_dg();
			}
		},{
			id:'btnredo',
			text:'部署',
			iconCls:'icon-redo',
			handler:function(){
				$('#btnredo').linkbutton('enable');
				deploymodel();
			}
		},{
			id:'btnpng',
			text:'查看流程图',
			iconCls:'icon-search',
			handler:function(){
				$('#btnpng').linkbutton('enable');
				readimage();
			}
		},{
			id:'btnexport',
			text:'导出BPMN',
			iconCls:'icon-redo',
			handler:function(){
				$('#btnexport').linkbutton('enable');
				exportxml();
			}
		}]
	});
//**********************************************************************************************************		
 	  $('#dg').datagrid('getPager').pagination({
		  beforePageText:'当前第',
		 afterPageText:'页,共{pages}页',
		 displayMsg:'共{total}记录',
		 pagesize:10,
		pageList: [10,15,20,25,30,35]
	});
	 
});

//******************************************************************************************************删除
function dele_dg(){
	var checked = $('#dg').datagrid('getChecked');               //
	if(checked.length==0){
		alert('请选择一条记录!');
		return;
	}
	if(checked[0]!=null){
		var v='';
		for(var i=0;i<checked.length;i++){
			v+=checked[i].id+',';
		}
		var str=v.substring(0,v.length-1);                     //
		$.post("${contextPath}/core/workflowService/json/deletemodel.do", {modelids:str},function(data){
			alert("删除成功!");
			$('#dg').datagrid('reload');
		} );
	}
}
//******************************************************************************************************编辑
function edit_dg(){
	var checked = $('#dg').datagrid('getChecked');
	if(checked.length>1||checked.length==0){
		alert('请选择一条记录!');
		return;
	}
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		window.open("${contextPath}/service/editor?id="+selected.id);
	}
}
function exportxml(){
	var checked = $('#dg').datagrid('getChecked');
	if(checked.length>1||checked.length==0){
		alert('请选择一条记录!');
		return;
	}
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		window.open("${contextPath}/core/workflowService/stream/readmodelResources.do?resourcetype=bpmn&modelId="+selected.id);
	}
}
function readimage(){
	var checked = $('#dg').datagrid('getChecked');
	if(checked.length>1||checked.length==0){
		alert('请选择一条记录!');
		return;
	}
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		window.open("${contextPath}/core/workflowService/stream/readmodelResources.do?resourcetype=image&modelId="+selected.id);
	}
}
function deploymodel(){
	var checked = $('#dg').datagrid('getChecked');
	if(checked.length>1||checked.length==0){
		alert('请选择一条记录!');
		return;
	}
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		$.post("${contextPath}/core/workflowService/json/deploymodel.do", {modelId:selected.id},function(data){
			$('#dg').datagrid('reload');
			alert("部署成功!");
		} );
	}
}
function add_dg(){
	$("#restid").click();
	$('#dd').window('open');  // 打开窗口
}
function submitform(){
	$("#fromid").submit();
	$("#restid").click();
	$('#dd').window('close');  // 关闭窗口
}
</script>

</head>

<body>
<table id="dg"></table> 

<div id="dd" class="easyui-dialog" title="新建" style="width:500px;height:300px;"
        data-options="iconCls:'icon-save',resizable:true,modal:true">
        <form action="${contextPath}/core/workflowService/createmodel.do" method="post" target="_blank" id="fromid">
         <table class="tablestyle" style="width: 486px;height: 264px;">
			<tr>
				<th colspan="2">模型信息</th>
			</tr>
			<tr class="haveborder">
				<td class="alt">流程名称</td>
				<td><input type="text" name="name"/></td>
			</tr>
			<tr class="haveborder">
				<td class="alt">流程key</td>
				<td><input type="text" name="key"/></td>
			</tr>
			<tr class="haveborder">
				<td class="alt">流程描述</td>
				<td><input type="text" name="description"/></td>
			</tr>
			<tr class="footstyle">
				<td class="alt" colspan="2">
					<input type="submit" value="创建"/>
					<input type="reset" style="display: none;" id="restid"/>
				</td>
			</tr>
		</table>
		</form>
</div>


</body>
</html>