<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head>  
  <title>${repManager.reportConfigureBean.name}</title> 
   <%@ include file="/pages/core/common/Include.jsp" %> 
   <link href="${contextPath}/style/stylelist.css" rel="stylesheet" type="text/css"> 
  <link href="{contextPath}/style/listTable.css" rel="stylesheet" type="text/css"> 
  <link href="{contextPath}/style/StandardStyle.css" rel="stylesheet" type="text/css" /> 
  </head>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  id="reportEngineBody"> 
<form method="post"  name="reportEngineForm" id="reportEngineForm" action="${contextPath}/core/importDataCoreService/queryImportResult.do"   > 
  <input name="contextPath" type="hidden" value="${contextPath}"/> 
  <input name="pageSize" id="pageSize" type="hidden"/>  <input name="pageNumber"  id="pageNumber" type="hidden"/>  
  <input name="moduleFileName" id="moduleFileName" type="hidden" value="${repManager.reportConfigureBean.filePath}"/> 
  <input name="appKey" id="appKey" type="hidden" value="${repManager.reportConfigureBean.appKey}"/>  
  <input name="operatePattern" id="operatePattern" type="hidden" value="${repManager.operatePattern}"/>  
  <input name="reportConfigureTitle" id="reportConfigureTitle" type="hidden" value="${repManager.reportConfigureBean.name}"/>  
  <input name="progressBarEventId" id="progressBarEventId" type="hidden"  >
   <input name="next" type="hidden"   value="queryReportResult"/><input name="openQueryResult" id="openQueryResult" type="hidden"   value="${param.openQueryResult}"/>
  <input name="tsFilterSql" id="tsFilterSql" type="hidden" value="${param.tsFilterSql}"/>   
	<ts:report name="hidden" /> 
    <div id="queryConditionPanel" class="easyui-panel"   title="<ts:lang name='queryCondiTitle'/>" style="width:100%" data-options="collapsible:true"> 
				<ts:report name="condition" /> 
 </div>  
				   <div style="text-align:center" id="queryOperateToolbar" >  
						
						<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" id="queryDatasBtn" name="queryDatasBtn" onClick="return reportScript.queryReportResult(null);"><ts:lang name='queryDatasBtn'/></a> &nbsp;&nbsp;&nbsp;&nbsp;
						 <a href="javascript:void(0)"  name="reportBackbtn" id="reportBackbtn" iconCls="icon-cancel" class="easyui-linkbutton"  ><ts:lang name='backbtn'/></a>
                         &nbsp;&nbsp;&nbsp;&nbsp;<input name="blurQuery" id="blurQuery" type="checkbox" value="1"  checked="true"  ><ts:lang name='blurQuery'/> 
                         &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  name="reportClearBlankBtn" id="reportClearBlankBtn" iconCls="icon-cut" class="easyui-linkbutton"  ><ts:lang name='clearBlankBtn'/></a>
                </div>    
     
   </form>   
   <div id="importDataContentPanel" class="easyui-panel" title="导入数据清单" style="width:100%;padding:10px;" data-options="collapsible:true">
     
	</div>
   ${loadImportFiles}
   
 
 <script type="text/javascript" src="<ts:base ref='path'/>/ReportUtil.js?version=2.0"></script>  
  <script language="javascript">
     var reportScript=new ReportScript();  
	 $(function() {
    	  $("#reportClearBlankBtn").hide();
		  
		  $.ts.EasyUI.titleAppendToolbar("queryOperateToolbar","queryConditionPanel","center");
		 <ts:report name='loadAfterScript'/>  
	 });
	     
	reportScript.initPage({contextPath:"${contextPath}",appKey:"${repManager.reportConfigureBean.appKey}"});
	${repManager.reportConfigureBean.loadAfterScript}
	 
	
  </script>
  </body>
  
</html>

