<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/c" prefix="c" %>
<%@ taglib uri="/tags/fn" prefix="fn" %>
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head>  
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">    
    <title>业务处理进度条</title>
    <script type="text/javascript">
         function checkForm() { 
			 if (publicLib.hasSelectOneRadio(document.getElementsByName("moduleFileName"))==false){
				alert("请选择导入规则!");
				return false;
			 }
			document.getElementById("submitBtn").disabled=true;
			return true;
		} 
    </script>
  </head>
  <body > 
     <form method="post"  action="${contextPath}/core/reportResolver/initPage.do"     >
     <input name="moduleFileName" id="moduleFileName" type="hidden"/>
    <table align="center"  id="dg" title="报表配置文件清单"  style="width:auto;height:auto;border:1px solid #ccc;"  data-options="singleSelect:true,rownumbers:true">
       <thead> 
            <tr   >    
					<th data-options="field:'name'">规则名称</td>
					<th data-options="field:'fileName'">规则配置文件</td>
					<th data-options="field:'memoField'">备注</td> 
                    <th data-options="field:'filePath',hidden:'true'" >隐藏</td> 
				  </tr>
            </thead>
				 <c:if test="${!empty reportConfigureFiles.configureList}">
                  <c:forEach items="${reportConfigureFiles.configureList}" var="obj"  varStatus="stat"> 	
				  <tr class="bgwhiteleft"  >  
					<td nowrap="nowrap">${obj.name}</td> 
					<td nowrap="nowrap">${obj.fileName}</td> 
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
  <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
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
				 document.forms[0].submit();
			} else {
				$.messager.alert('提示', "请选择报表");
			}
		}
  </script>
</html>
