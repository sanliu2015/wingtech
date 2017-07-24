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
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
</head>
<body id="${appReqeustContext.appKey}Body">  
<table class="easyui-datagrid" title="宿舍费用汇总" id="dg"></table>
<div id="tb" style="padding:2px 5px;">
		选择月份: <input name="yearMonth" id="yearMonth" class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="queryTotal();">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel'" onClick="exportExcel('宿舍物业费用统计报表', $('#dg'));">导出excel</a>  
	</div>
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery/json2.js"></script>
<script type="text/javascript" src="<ts:base ref='path'/>/Total.js?v5"></script> 
<script type="text/javascript">
    var initScript = new InitScript();  
    var buildList = ${buildList};
    var columnsGlobal = "[[{field:'deptName',title:'部门名称',align:'center',rowspan:2,width:130}" ;
    if (buildList.length > 0) {
        columnsGlobal = columnsGlobal + ",{field:'',title:'水电气分项',align:'center',colspan:" + (buildList.length+1) + "}";
        columnsGlobal = columnsGlobal + ",{field:'',title:'水电公摊分项',align:'center',colspan:" + (buildList.length+1) + "}";
        columnsGlobal = columnsGlobal + ",{field:'',title:'房租补贴',align:'center',colspan:" + (buildList.length+1) + "}";
        columnsGlobal = columnsGlobal + ",{field:'',title:'个人房租扣款',align:'center',colspan:" + (buildList.length+1) + "}";
        <!--columnsGlobal = columnsGlobal + ",{field:'',title:'损坏扣款分项',align:'center',colspan:" + (buildList.length+1) + "}";-->
        
        columnsGlobal = columnsGlobal + "],[";
        for (var i=0;i<buildList.length;i++) {
            columnsGlobal = columnsGlobal + "{field:'" + buildList[i].id + "wtpwgsFee',title:'" + buildList[i].name + "',width:70},";
        }
        columnsGlobal = columnsGlobal + "{field:'wtpwgsTotal',title:'本项合计',width:70},";
        
        for (var i=0;i<buildList.length;i++) {
            columnsGlobal = columnsGlobal + "{field:'" + buildList[i].id + "sharedFee',title:'" + buildList[i].name + "',width:70},";
        }
        columnsGlobal = columnsGlobal + "{field:'sharedTotal',title:'本项合计',width:70},";
        
        for (var i=0;i<buildList.length;i++) {
            columnsGlobal = columnsGlobal + "{field:'" + buildList[i].id + "corpFee',title:'" + buildList[i].name + "',width:70},";
        }
        columnsGlobal = columnsGlobal + "{field:'corpTotal',title:'本项合计',width:70},";
        
        for (var i=0;i<buildList.length;i++) {
            columnsGlobal = columnsGlobal + "{field:'" + buildList[i].id + "rentFee',title:'" + buildList[i].name + "',width:70},";
        }
        columnsGlobal = columnsGlobal + "{field:'rentTotal',title:'本项合计',width:70}";
        
    }
    
    columnsGlobal = columnsGlobal + "]]";
    
    $(function(){
    	$('#dg').datagrid({
            rownumbers:true,
//          showFooter:true,
            toolbar:'#tb',
            columns:eval(columnsGlobal)
        });
    });
    
    
	function modalDialogLoadEvent() {
		initScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</body>
</html>