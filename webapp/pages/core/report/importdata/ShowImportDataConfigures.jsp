<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/c" prefix="c" %>
<%@ taglib uri="/tags/fn" prefix="fn" %>
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head>  
 <%@ include file="/pages/core/common/Include.jsp" %> 
    <title>业务处理进度条</title>
    
  </head>
  <body > 
     <form method="post"  action="${contextPath}/core/importDataCoreService/initPage.do"     >
     <input name="moduleFileName" id="moduleFileName" type="hidden"/>
    <table align="center"  id="dg" title="导入数据配置文件清单"  style="width:auto;height:auto;border:1px solid #ccc;"  data-options="singleSelect:true,rownumbers:true">
       <thead> 
            <tr   >    
					<th data-options="field:'name'">规则名称</td>
					<th data-options="field:'fileName'">规则配置文件</td>
					<th data-options="field:'reportCatalog'">分类</td> 
					<th data-options="field:'desc'">备注</td> 
                    <th data-options="field:'filePath',hidden:'true'" >隐藏</td> 
				  </tr>
            </thead>
				 <c:if test="${!empty reportConfigureFiles.configureList}">
                  <c:forEach items="${reportConfigureFiles.configureList}" var="obj"  varStatus="stat"> 	
				  <tr class="bgwhiteleft"  >  
					<td nowrap="nowrap">${obj.name}</td> 
					<td nowrap="nowrap" >${obj.fileName}</td> 
					<td nowrap="nowrap">${obj.reportCatalog}</td> 
					<td nowrap="nowrap">${obj.desc}</td>  
					<td  ><input name="filePath" type="hidden" value="${obj.fileName}"  ></td> 
				  </tr>
				 </c:forEach>
                 </c:if>
                </table> 
				 
	<div style="text-align:center;padding:5px" class="dialog-toolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save"  onClick=" return getSelected();">确定</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"   onClick="return parent.mainScript.closeCurrentTab(); ">关闭</a>  
        </div>
	</form>
  </body>
   
  <script type="text/javascript"> 
    $('#dg').datagrid({
		onDblClickRow:function(rowIndex,rowData){
			 getSelected();
		}
	 });
	 
	function getSelected(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$("#moduleFileName").val(row.fileName);
				 //document.forms[0].submit();
				 var url="/core/importDataCoreService/initPage.do?moduleFileName="+row.fileName;
				
				 if(row.reportCatalog=="excel"){
					url="/core/importExcelDataCoreService/openUploadExcelFile.do?moduleFileName="+row.fileName;
				 } 
				 window.parent.mainScript.refreshTab("导入"+row.name,url); 
			} else {
				$.messager.alert('提示', "请选择导入配置文件");
			}
		}
  </script>
</html>
