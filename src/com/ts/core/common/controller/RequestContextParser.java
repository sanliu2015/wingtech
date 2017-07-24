package com.ts.core.common.controller;

import javax.servlet.http.HttpServletRequest;
 

import com.ts.core.common.constant.Globals;
import com.ts.core.context.RequestContext;
import com.ts.core.util.StringUtils;

public class RequestContextParser {  
	/**
	 * example:/app/moduleService/json/getLoginJson.do
	 * @param requestContext
	 * @param request
	 * @param prefix
	 * @param methodType:do、json 
	 */
	public void parseRequestPath(RequestContext requestContext,HttpServletRequest request,String prefix,String methodType ){
		String requestPath=requestContext.getRequestPath();
		String[] seps=requestPath.split("/");   
		String method=""; 
		for(int i=0;i<seps.length;i++){  
			if(StringUtils.isNoValue(seps[i])) continue; 
			if(seps[i].indexOf("."+Globals.URL_SUBFIX)>0){ 
				if(StringUtils.isNoValue(requestContext.getAppMethod())){
					method=seps[i].substring(0, seps[i].indexOf("."+Globals.URL_SUBFIX)); 
				}
				if(i>1){ //功能模块服务名称
					String serviceName=seps[i-2];
					if("".equals(methodType))
						serviceName=seps[i-1];
					if(Globals.URL_EXECUTETYPE.equals(serviceName)){
						serviceName=seps[i-2];
					}
					String[] services=serviceName.split("\\.");
					if(services.length==1)
						requestContext.setAppService(serviceName);
					else {
						requestContext.setAppService(services[services.length-1]);
					}
				} 
			} 
		}   
		if(StringUtils.isNoValue(requestContext.getAppKey())){
			requestContext.setAppKey(requestContext.getAppService());
		}
		if(!StringUtils.isNoValue(method) && method.indexOf("_")>0){
			method=method.substring(0,method.indexOf("_")); 
			requestContext.setAppMethod(method);
			requestContext.setToMethod(method.substring(method.indexOf("_")+1));
		}
		if(request!=null){
			String appKey=request.getParameter(Globals.APP_KEY);
			if(!StringUtils.isNoValue(appKey)){ 
				requestContext.setAppKey(appKey);
			}
			String appServiceName= request.getParameter(Globals.APP_SERVICE);
			if(!StringUtils.isNoValue(appServiceName)) {
				requestContext.setAppService(appServiceName);
				if(StringUtils.isNoValue(requestContext.getAppKey())){ 
					requestContext.setAppKey(appServiceName);
				}
			}
			if(!StringUtils.isNoValue(request.getParameter(Globals.APP_METHOD))){
				method=request.getParameter(Globals.APP_METHOD);
			}
			String appUrl=request.getParameter(Globals.APP_URL);
			if(!StringUtils.isNoValue(appUrl)){  
				requestContext.setAppUrl(appUrl);
			}
		}   
	}
	public static void setAppUrl(RequestContext requestContext,Object appService){ 
		if(requestContext.getRequest()==null) return;
		String appUrl=requestContext.getRequest().getParameter(Globals.APP_URL);
		setAppUrl(requestContext,appService,appUrl); 
	}
	public static void setAppUrl(RequestContext requestContext,Object appService,String appUrl){
		if(!StringUtils.isNoValue(appUrl)){ 
			if(appUrl.indexOf("/")<0){ 
				String  path=parseMiddlePackageUrl(appService);
				StringBuffer url=new StringBuffer("");
				url.append(path).append("/");  
				url.append(appUrl);  
				appUrl=url.toString();  
			}
			requestContext.setAppUrl(appUrl);
		}
	}
	public static String parseMiddlePackageUrl(Object appService){
		String serviceClassName=appService.getClass().getName();  
		String  path=serviceClassName.substring(Globals.PACKAGE_PREFIX.length()); 
		path=path.substring(0,path.length()- appService.getClass().getSimpleName().length()-1);
		path="/"+path.replaceAll("\\.", "/");
		return path;
	}
	/**
	 * 如serviceName=com.ts.core.system.user.UserService,method=add,
	 * 则url=/core/system/user/AddUser,如method=query_add,则也为url=/core/system/user/AddUser
	 * @param appService
	 * @param requestContext
	 */
	public void parseUrl(Object appService,RequestContext requestContext){ 
		if(StringUtils.isNoValue(requestContext.getAppUrl())){ 
			String  path=parseMiddlePackageUrl(appService);
			StringBuffer url=new StringBuffer("");
			url.append(path).append("/"); 
			String methodName=requestContext.getAppMethod();
			url.append(StringUtils.upperCaseFirst(methodName));  
			requestContext.setAppUrl(url.toString()); 
		}
		 
	}
}
