<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Exception!</title>
</head>
<body id="ShowErrorPageBody">
 
<% 
Exception e =null;
	if(request!=null){ 
		e=(Exception)request.getAttribute("ex");
	}
%>
<H2 id="ShowErrorPageBodyH2">未知错误: <%= (e!=null?e.getClass().getSimpleName():"")%></H2>
<hr/>
<P>错误描述：</P>
<%=(e!=null?e.getMessage():"")%>
<P>错误信息：<img src="${contextPath}/resource/image/icon/nolines_plus.gif"  id="searchDivBtn" border="0" onClick="displayObtainDtlTable(this);" /></P>
<div style="display:none" id="exceptionDetailContextId">
<%   
if(e!=null) {
   e.printStackTrace(new java.io.PrintWriter(out));
}
out.clear();  
out = pageContext.pushBody(); %>
 </div>
 <script language="javascript">
 var urlContextPath="${contextPath}";
 function displayObtainDtlTable(obj){  		  
		  
			 if(obj.src.indexOf("nolines_minus.gif")>0){
				obj.src=urlContextPath+"/resource/image/icon/nolines_plus.gif";
				document.getElementById("exceptionDetailContextId").style.display="none";
			 }else {
				obj.src=urlContextPath+"/resource/image/icon/nolines_minus.gif";
				document.getElementById("exceptionDetailContextId").style.display="";
			 } 
	} 
 </script>
</body>
</html>