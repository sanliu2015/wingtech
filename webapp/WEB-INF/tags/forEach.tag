<%@ tag language="java" %>
<%@ tag import="com.tomkysoft.core.context.*" 
        import="com.tomkysoft.core.common.constant.*" 
	import="java.util.*" %> 
<%@ attribute name="listName" type="java.lang.String" required="true" %> 
<%@ attribute name="value" type="java.lang.String" required="false" %> 
<%!
  private String forEach(String listName,String value) { 
        RequestContext requestContext=(RequestContext)getJspContext().getAttribute(Globals.APP_REQUESTCONTEXT); 
	StringBuffer sb=new StringBuffer("");
        if(requestContext!=null && requestContext.getBindMessageResource()!=null){
        	if(requestContext.getBindMessageResource().getSqlScript()!=null){
        		if(requestContext.getBindMessageResource().getSqlScript().getSqlResultMap()!=null){
        			List<Map> resultMap=(List)requestContext.getBindMessageResource().getSqlScript().getSqlResultMap().get(listName);
        			if(resultMap!=null && resultMap.size()>0){
				        sb.append("<option value=\"abc\">iewww</option>");
        				for(int i=0;i<resultMap.size();i++){
        					Map row=resultMap.get(i);
        					sb.append("<option value=\"").append(row.get("code")).append("\"");
        					if(row.get("code")==null) row.put("code","");
        					if(value.equals(row.get("code"))){
        						sb.append(" selected ");
        					}
        					sb.append(">");
        					sb.append(row.get("name")).append("</option>");
        				}
        			}
        		} 
        	}
        }
        return sb.toString();  
  }  
%>  
<%  
  out.println(forEach( listName,value));  
%>  