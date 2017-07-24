<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
 <%  
response.setHeader("Pragma","No-cache");//HTTP 1.1 
response.setHeader("Cache-Control","no-cache");//HTTP 1.0 
response.setHeader("Expires","0");//防止被proxy 
%>
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js?version=1.0"></script>  
<script src="pdfobject.js" type="text/javascript"></script>
 <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script>
 <TITLE>阅读PDF文档</TITLE>   
  <script type="text/javascript">
     $(function () {
      var w = $(document).width();
      var h = $(document).height();			
      $("#pdf1").css("width", w).css("height", h);
       
      // 下面代码都是处理IE浏览器的情况 
      if(/msie/.test(navigator.userAgent.toLowerCase()) ) { 
        document.getElementById("pdf").setShowToolbar( false );
      }  else { 
	    window.onload = function (){
			var myPDF = new PDFObject({ url: "${contextPath}/${form.name}" }).embed();
		  };
	   
      }
    });
  </script>
 </head>  
	  
  <body  > 
 <form id="form1" runat="server">	
   	 <object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="550" border="0"    id ="pdf" name="pdf">   
                <param name="toolbar" value="false">   
                <param name="_Version" value="65539">    
                <param name="_ExtentX" value="20108">    
                <param name="_ExtentY" value="10866">    
                <param name="_StockProps" value="0">      
                <param name="SRC" value="${contextPath}${form.name}">   
                </object> 
  </form>
</BODY>   
</html>