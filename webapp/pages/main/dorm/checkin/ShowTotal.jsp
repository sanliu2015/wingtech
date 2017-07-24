<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<title>${appReqeustContext.appName}</title> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
</head>
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<table class="easyui-datagrid" title="宿舍统计表" 
			data-options='
				data:${dataJson},
				fitColumns: true,
				singleSelect: true,
				rownumbers: true,
				showFooter: true
			'>
			<thead>
				<tr>
					<th data-options="field:'buildingName',width:100">楼栋名称</th>
					<th data-options="field:'typeName',width:70">房间属性</th>
					<th data-options="field:'roomSum',width:60">房间数量</th>
					<th data-options="field:'bigNumberSum',width:60">可住人数</th>
					<th data-options="field:'inSum',width:60">现住人数</th>
					<th data-options="field:'freeSum',width:60">未住人数</th>
					<th data-options="field:'emptyRoomSum',width:60">空置房</th>
					<th data-options="field:'inRate',width:60">入住率</th>
				</tr>
			</thead>
		</table>
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery/json2.js"></script>
<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js"></script> 
<script type="text/javascript">
    var checkInScript = new CheckInScript();  
	function modalDialogLoadEvent() {
		checkInScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>