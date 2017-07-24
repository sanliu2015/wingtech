<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head>
<title>下载附件</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
</head>

<body>
<table width="60%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tb" >
    <tr>
	 <td    colspan="2"  class="bgblueleft">下载页面</td>
	</tr>
    <tr class="tend">
      <td>如果不能自动下载请点击：<a href="${contextPath}/core/downloadFileService/allowDownloadFile.do?number=${fileName}&name=${exportFilePath}"><img src="${contextPath}/resource/image/icon/05.gif" width="20" height="20" border="0"> 下载导出文件</a>
	  &nbsp;&nbsp;&nbsp;&nbsp;
	  
      </td>
	  <td><input name="backSubmit" type="button" class="buttonstyle" onClick="$.ts.EasyUI.closeDialog(this,'0');" value="返回"></td>
    </tr>
</table>

 <script language="javascript"> 
	 	window.location.href ="${contextPath}/core/downloadFileService/stream/allowDownloadFile.do?number=${fileName}&name=${exportFilePath}";
		
 </script>
 </body>
</html>
