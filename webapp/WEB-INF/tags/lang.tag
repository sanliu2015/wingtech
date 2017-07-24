<%@ tag language="java" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="com.tomkysoft.core.context.*" import="com.tomkysoft.core.common.constant.*" 
     import="javax.servlet.http.HttpServletRequest" 
     import="javax.servlet.jsp.*" 
%> 
<%@ attribute name="value" type="java.lang.String" required="true" %>  
<%!
  private String lang(String s) { 
        PageContext pageContext =(PageContext)this.getJspContext(); 
        HttpServletRequest request =(HttpServletRequest)pageContext.getRequest();   
        RequestContext requestContext=(RequestContext)request.getAttribute(Globals.APP_REQUESTCONTEXT);
	String value="";
        if(requestContext!=null && requestContext.getBindMessageResource()!=null){
        	if(requestContext.getBindMessageResource().getLang()!=null){
        		value=(String)requestContext.getBindMessageResource().getLang().get(s);
        	}
        }
        return value;  
  }  
%>  
<%  
  out.println(lang( value));  
%>  