<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
  <title>${repManager.reportConfigureBean.name}</title> 
  </head>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  id="reportEngineBody"> 
<form method="post"  name="reportEngineForm" id="reportEngineForm" action="${contextPath}/core/reportCoreServiceResolver/json/queryReportResult.do"   > 
  <input name="contextPath" type="hidden" value="${contextPath}"/> 
  <input name="pageSize" id="pageSize" type="hidden"/>  <input name="pageNumber"  id="pageNumber" type="hidden"/>  
  <input name="moduleFileName" id="moduleFileName" type="hidden" value="${repManager.reportConfigureBean.filePath}"/> 
  <input name="appKey" id="appKey" type="hidden" value="${repManager.reportConfigureBean.appKey}"/>  
  <input name="operatePattern" id="operatePattern" type="hidden" value="${repManager.operatePattern}"/>  
  <input name="reportConfigureTitle" id="reportConfigureTitle" type="hidden" value="${repManager.reportConfigureBean.name}"/>  
  <input name="progressBarEventId" id="progressBarEventId" type="hidden"  >
  <input name="blurQuery" id="blurQuery" type="hidden" value="1" >
  
   <input name="next" type="hidden"   value="queryReportResult"/><input name="openQueryResult" id="openQueryResult" type="hidden"   value="${param.openQueryResult}"/>
  <input name="tsFilterSql" id="tsFilterSql" type="hidden" value="${param.tsFilterSql}"/>   
	<ts:report name="hidden" /> 
    <div id="queryConditionPanel" class="easyui-panel"   title="<ts:lang name='queryCondiTitle'/>" style="width:100%" data-options="collapsible:true"> 
				<ts:report name="condition" /> 
 </div>  
				   <div style="text-align:center" id="queryOperateToolbar" >  
						
						<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" id="queryDatasBtn" name="queryDatasBtn" onClick="return reportScript.queryReportResult(null);"><ts:lang name='queryDatasBtn'/></a> &nbsp;&nbsp;&nbsp;&nbsp;
						 <a href="javascript:void(0)"  name="reportBackbtn" id="reportBackbtn" iconCls="icon-cancel" class="easyui-linkbutton"  ><ts:lang name='backbtn'/></a> 
                         &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  name="reportClearBlankBtn" id="reportClearBlankBtn" iconCls="icon-cut" class="easyui-linkbutton"  ><ts:lang name='clearBlankBtn'/></a>
                </div>    
    ${repManager.outHtml} 
   </form>   
   ${loadImportFiles}
   <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js?version=1.0"></script>   
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script>
    
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>   
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>  
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/jquery.validate.js"></script>
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/additional-methods.js"></script>
   <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/messages_zh.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js?version=1.6"></script>
 
 <script type="text/javascript" src="<ts:base ref='path'/>/ReportUtil.js?version=2.0"></script>  
  <script language="javascript">
     var reportScript=new ReportScript();  
	 $(function() {
    	  $("#reportClearBlankBtn").hide();
		  $("#queryConditionPanel").panel({onExpand:function(){
			 	var resultJson = reportDatagrid.datagrid;  
				    var queryConditionHeight=0;
				   if($("#reportQueryConditionTable").length>0){
					   queryConditionHeight=$("#reportQueryConditionTable").height();
				   }  
				 var grid=$('#'+resultJson.tableId);      
				 grid.datagrid("resize",{   
				   height: ($(window).height()-queryConditionHeight-50) 
					 
				}); 
				   
			 },
		 onCollapse:function(){
			 var resultJson = reportDatagrid.datagrid;   
				 var grid=$('#'+resultJson.tableId);  
				 grid.datagrid("resize",{   
				    height: ($(window).height()-50) 
					 
				}); 
		 }});
		  $.ts.EasyUI.titleAppendToolbar("queryOperateToolbar","queryConditionPanel","center");
		 <ts:report name='loadAfterScript'/>  
	 });
	 var reportDatagrid=${reportDatagrid};   
	reportScript.initPage({contextPath:"${contextPath}",appKey:"${repManager.reportConfigureBean.appKey}"});
	${repManager.reportConfigureBean.loadAfterScript}
	if($("#openQueryResult").val()=="1"){
		reportScript.queryReportResult(null);
	}
	
  </script>
  </body>
  
</html>

