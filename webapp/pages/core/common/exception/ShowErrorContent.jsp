<%@ page contentType="text/html; charset=UTF-8"%>
<% 
Exception e =null;
	if(request!=null){ 
		e=(Exception)request.getAttribute("ex");
	}
%>
<%=(e!=null?e.getMessage():"")%>