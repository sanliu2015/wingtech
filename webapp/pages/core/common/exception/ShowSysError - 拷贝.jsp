<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Exception!</title>
</head>
<body id="ShowSysErrorPageBody">
 
<% Exception e = (Exception)request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION"); %>
<H2 id="ShowSysErrorPageBodyH2">未知错误: <%=(e!=null?e.getClass().getSimpleName():"")%></H2>
<hr />
<P>错误描述：</P>
<%= (e!=null?e.getMessage():"")%>
<P>错误信息：</P>
<% if(e!=null) {e.printStackTrace(new java.io.PrintWriter(out));} %>
 
</body>
</html>