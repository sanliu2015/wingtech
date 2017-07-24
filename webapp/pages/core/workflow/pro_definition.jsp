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
<title>流程定义</title>       
<!--table的默认样式  -->       
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css">     
<!--easyui  -->
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">    
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<!-- ajax上传文件 -->
<script type="text/javascript" src="${contextPath}/scripts/ts/ajaxfileupload.js"></script>

<!--jquery验证插件  -->
<script type="text/javascript" src="${contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${contextPath}/js/additional-methods.js"></script>
<script type="text/javascript" src="${contextPath}/js/messages_zh.js"></script> 
<script type="text/javascript">
$(function(){
	$('#dd').window('close');  // 关闭窗口
//*********************************************************************************************	数据表格 	 
	$('#dg').datagrid({
		title:'流程定义',
		iconCls:'',
		width:1120,                             
		height:540,                             
		nowrap: true,
		autoRowHeight: false,
		fitColumns:true,
		striped: true,
		url:'${contextPath}/core/workflowService/json/getdefinitions.do',  
		sortName: 'id',
		sortOrder: 'asc',                      
		remoteSort: false,
		selectOnCheck:true,
		singleSelect:true,
		pagination:true, 
		rownumbers:true,
		columns:[[                              //************************************字段名
				  {field:'',checkbox:true},               //********************设置复选框
		          {field:'id',title:'id',width:120,hidden:true},
		          {field:'key',title:'key',width:180},
		          {field:'name',title:'名称',width:180},
		          {field:'version',title:'版本',width:90},
		          {field:'suspensionstate',title:'状态',width:90,formatter: function(value,row,index){
						if (value==1){
							return "已激活";
						} else {
							return "已挂起";
						}
					}
}
		      ]],
//**********************************************************************设置单即一行记录时触发,按需添加
		//onClickRow:function(rowIndex,rowData){
		//	alert(rowData.id);
	//	},
//******************************************************************工具栏		
		toolbar:[{
			id:'btnupload',
			text:'上传流程文件',
			iconCls:'icon-add',
			handler:function(){
				$('#btnupload').linkbutton('enable');
				openupload();
			}
		},{
			id:'btnimage',
			text:'查看流程图',
			iconCls:'icon-redo',
			handler:function(){
				$('#btnimage').linkbutton('enable');
				readimage();
			}
		},{
			id:'btnxml',
			text:'查看xml',
			iconCls:'icon-redo',
			handler:function(){
				$('#btnxml').linkbutton('enable');
				readxml();
			}
		},{
			id:'btnaorh',
			text:'激活或挂起',
			iconCls:'icon-redo',
			handler:function(){
				$('#btnaorh').linkbutton('enable');
				aorh();
			}
		},{
			id:'btnredo',
			text:'转换为模型',
			iconCls:'icon-redo',
			handler:function(){
				$('#btnredo').linkbutton('enable');
				converttomodel();
			}
		},{
			id:'btnno',
			text:'删除',
			iconCls:'icon-no',
			handler:function(){
				$('#btnno').linkbutton('enable');
				dele_dg();
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
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		 $.post("${contextPath}/core/workflowService/json/deleteDefinition.do", {key:selected.key},function(data){
			 alert("删除成功!");
			$('#dg').datagrid('reload');
		} );
	}else{
		alert("请选择一条记录!");
	}
}
//******************************************************************************************************编辑
function readimage(){
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		window.open("${contextPath}/core/workflowService/stream/readDefinitionresource.do?resourcetype=image&processDefinitionId="+selected.id);
	}else{
		alert("请选择一条记录!");
	}
}
function readxml(){
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		window.open("${contextPath}/core/workflowService/stream/readDefinitionresource.do?resourcetype=xml&processDefinitionId="+selected.id);
	}else{
		alert("请选择一条记录!");
	}
}
function converttomodel(){
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		 $.post("${contextPath}/core/workflowService/json/converttomodel.do", {processDefinitionId:selected.id},function(data){
			 	alert("转换成功!");
				$('#dg').datagrid('reload');
			} );
	}else{
		alert("请选择一条记录!");
	}
}
function aorh(){
	var selected = $('#dg').datagrid('getSelected');
	if(selected!=null){
		 $.post("${contextPath}/core/workflowService/json/aorhDefinition.do", {processDefinitionId:selected.id,status:selected.suspensionstate},function(data){
			 alert("操作完成!");
			$('#dg').datagrid('reload');
		} );
	}else{
		alert("请选择一条记录!");
	}
}
function openupload(){
	$("#restuploadid").click();
	$('#btn').linkbutton('enable');
	$('#dd').window('open');  // 打开窗口
}
function toupload(){
 	var filename=$('#fileid').val();
	var obj=filename.substring(filename.length-3, filename.length);
	var obj1=filename.substring(filename.length-4, filename.length);
	var obj2=filename.substring(filename.length-10, filename.length);
	if(obj=="zip"||obj=="bar"||obj1=="bpmn"||obj2=="bpmn20.xml"){
 		$('#btn').linkbutton('disable');
		ajaxupload("fileid");
	}else{
		alert("请上传后缀名为bpmn20.xml,bpmn,zip或bar文件!");
	}
}
	function ajaxupload(data) {
		$.ajaxFileUpload({
			url : '${contextPath}/core/workflowService/json/uploadfile.do', //用于文件上传的服务器端请求地址
			secureuri : false, //一般设置为false
			fileElementId : ''+data, //文件上传控件的id属性  <input type="file" id="file1" name="file" />
			//data : {"arr" : "qwe"},
			dataType : 'json', //返回值类型 一般设置为json
			success : function(data, status) {
				$('#dd').window('close');
				alert("部署成功!");
				$('#dg').datagrid('reload');
			}
		});
	}
</script>

</head>

<body>
<table id="dg"></table> 
<div id="dd" class="easyui-dialog" title="上传文件" style="width:400px;height:144px;"
        data-options="modal:true">
        <form>
	          <table class="tablestyle" style="width: 386px;height: 64px;">
					<tr>
						<th colspan="2">流程文件(支持bpmn20.xml,bpmn,zip或bar文件)</th>
					</tr>
					<tr class="haveborder">
						<td class="alt">上传文件</td>
						<td>
							<input id="fileid" name="file" style="width:255px" type="file">
						</td>
					</tr>
					<tr class="footstyle">
						<td class="alt" colspan="2">
							<a id="btn" href="javascript:;" class="easyui-linkbutton" onclick="toupload();">提交</a>
							<input type="reset" style="display: none;" id="restuploadid"/>
						</td>
					</tr>
				</table>
<!-- 				支持bpmn20.xml,bpmn,zip或bar文件(注意:通过activity desiginer插件设计的流程图必须通过打包部署(包中含有bpmn和png文件)) -->
		</form>
 </div>
</body>
</html>