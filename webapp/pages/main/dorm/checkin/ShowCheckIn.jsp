<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<title>员工入住</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">   
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
.emp {
	width: 60px;
	height: 20px;
	margin: 5px;
	border: 1px solid #ccc;
	background: #AACCFF;
	float:left;
	font-weight: normal;
	color: black;
}

.dp {
	opacity: 0.5;
	filter: alpha(opacity = 50);
}

.over {
	background: #FBEC88;
}
body {
	margin:0px;
}
.room{
	border:1px solid #ccc;
	width:100px;
	min-height:50px;
	height:auto;
	float:left;
	margin:3px;
	font-weight: bold;
	color: red;
}
.total {
	margin: 0px auto;
	background-color: #FFFACD;
	font-family: 宋体;
	font-size: 12px;
}
</style>
</head>
<body id="${appReqeustContext.appKey}Body" class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<!-- 左侧 -->
	<div data-options="region:'west',split:true,title:'查询条件', fit:false" style="width:200px;">
		<div style="background:#0081c2;height:20px;line-height:16px;font-size:16px;font-weight:bold;color:#fff;">房间筛选</div>
		<br/>
		<table>
			<tr>
				<td>楼栋：<input name="build" id="build" style="width:130px"></td>
			</tr>
			<tr>
				<td>单元：<input name="unit" id="unit" style="width:130px"></td>
			</tr>
			<tr>
				<td>类型：<select name="roomType" id="roomType" class="easyui-combobox" style="width:130px">   
                		<ts:forEach name='roomTypeList' insertEmpty='1' value="" />
					</select></td>
			</tr>
			<tr>
				<td>房间：<input class="easyui-textbox" name="roomName" id="roomName" style="width:130px"></td>
			</tr>
			<tr>
				<td>可住人数：<input class="easyui-numberspinner" name="canInBg" id="canInBg" data-options="min:1" value="1" style="width:45px">--<input class="easyui-numberspinner" name="canInEd" id="canInEd" style="width:45px"></td>
			</tr>
			<tr>
				<td style="text-align:center">
					<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="queryRoom()">查询</a>
					<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearRoomCondition()">清空</a>
				</td>
			</tr>
		</table>
		<br/>
		<table>
			<tr>
				<td><div style="border: 1px solid #ccc;width:60px;height: 20px;font-weight: bold;color: red;background-color:#90EE90;"></td>
				<td><strong style="color: red">有人</strong></td>
				<td><div style="border: 1px solid #ccc;width:60px;height: 20px;font-weight: bold;color: red;background-color:#FFFFFF;"></td>
				<td><strong style="color: red">空房</strong></td>
			</tr>
		</table>
		<br/>
		<div style="background:#0081c2;height:20px;line-height:16px;font-size:16px;font-weight:bold;color:#fff;">员工筛选</div>
		<br/>
		<table>
			<tr>
				<td>劳务：<select name="interimId" id="interimId" class="easyui-combobox" style="width:130px">   
                		<ts:forEach name='interimList' insertEmpty='1' value="" /></select></td>
			</tr>
			<tr>
				<td>姓名：<input class="easyui-textbox" name="empName" id="empName" style="width:130px"></td>
			</tr>
			<tr>
				<td>工号：<input class="easyui-textbox" name="number" id="number" style="width:130px"></td>
			</tr>
			<tr>
				<td>ID号：<input class="easyui-textbox" name="idCard" id="idCard" style="width:130px"></td>
			</tr>
			<tr>
				<td>性别：<select name="gender" id="gender" class="easyui-combobox" style="width:130px">   
                		<ts:forEach name='genderList' insertEmpty='1' value="" /></select></td>
			</tr>
			<tr>
				<td style="text-align:center">
					<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="queryEmp()">查询</a>
					<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearEmpCondition()">清空</a>
				</td>
			</tr>
		</table>
		
	</div>
	<div data-options="title:'',region:'center'" style="background:#eee;width:100%;">
		<div class="easyui-panel" title="房间信息" id="roomPanel" style="width:100%;height:40%;background:#fafafa;" >
		</div>
		<div class="easyui-panel" title="未入住员工" id="empPanel" style="width:100%;height:60%;background:#fafafa;" >
		</div>
	</div>
	<div style="clear:both"></div>
	<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/jquery/json2.js"></script>
	<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js?v2"></script>
	<script>
	    var tsContextPath = "${contextPath}";
		var buildList = ${buildList};
		var unitList = ${unitList};
		function initUnit(buildId) {
			var resultJson=[];
			for (var i=0; i<unitList.length; i++) {
				if (unitList[i].parentId==buildId) {
					resultJson.push(unitList[i]);
				}
			}
			$("#unit").combobox("clear").combobox("loadData", resultJson);
		}
		$(function(){
			$("#build").combobox({
				valueField:'id',
				textField:'name',
				data:buildList,
				onSelect:function(rec) {
					initUnit(rec.id);
				}
			});
			$("#unit").combobox({
				valueField:'id',
				textField:'name',
				data:unitList
			});
		});
	</script>

</body>
</html>