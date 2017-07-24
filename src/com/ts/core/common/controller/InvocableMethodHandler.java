package com.ts.core.common.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ts.core.api.cache.SimpleCachePool;
import com.ts.core.api.script.ScriptParseUtil;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.form.IBaseModel;
import com.ts.core.common.service.IBaseInvokeEvent;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.AppContext;
import com.ts.core.context.AppContextResource;
import com.ts.core.context.RequestContext;
import com.ts.core.context.messageresource.BindMessageResource;
import com.ts.core.context.messageresource.MessageResourceResolver;
import com.ts.core.system.module.ModuleServiceImpl;
import com.ts.core.util.ReflectUtils;
import com.ts.core.util.ResourceUtil;
import com.ts.core.util.StringUtils;

public class InvocableMethodHandler {
	private     Log log = LogFactory.getLog(this.getClass()); 
	private String methodName;
	private NativeWebRequest nativeWebRequest;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RedirectAttributesModelMap attr;
	private IBaseModel form;
	private String methodType;
	private String urlPrefix; 
	
	
 
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public RedirectAttributesModelMap getAttr() {
		return attr;
	}
	public void setAttr(RedirectAttributesModelMap attr) {
		this.attr = attr;
	}
	public IBaseModel getForm() {
		return form;
	}
	public void setForm(IBaseModel form) {
		this.form = form;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	
	
	public NativeWebRequest getNativeWebRequest() {
		return nativeWebRequest;
	}
	public void setNativeWebRequest(NativeWebRequest nativeWebRequest) {
		this.nativeWebRequest = nativeWebRequest;
	}
	public String getUrlPrefix() {
		return urlPrefix;
	}
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	public InvocableMethodHandler( String methodType,String urlPrefix,String methodName,  NativeWebRequest nativeWebRequest, HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr, IBaseModel form){
		this.methodName=methodName;
		this.request=request;
		this.response=response;
		this.attr=attr;
		this.form=form;
		this.methodType=methodType;
		this.urlPrefix=urlPrefix;
		this.nativeWebRequest=nativeWebRequest;
	}
	private void setFormToAttribute(RequestContext requestContext,int paramIndex){
		if(paramIndex==-1 || form==null) return ; 
		String paraKey=requestContext.getAppService()+"-"+requestContext.getAppMethod();
		String formPara=SimpleCachePool.serviceFormMap.get(paraKey);
		if(formPara!=null){ 
			requestContext.setFormName(formPara);
			request.setAttribute(formPara, form);
		}  else { 
			Class cl;
			try {
				cl = Class.forName(requestContext.getServiceClassName());
				Method m=ReflectUtils.findMethod(cl, requestContext.getAppMethod());
				if(m!=null){
					formPara=m.getParameters()[paramIndex].getName();
					requestContext.setFormName(formPara);
					request.setAttribute(formPara, form);
					SimpleCachePool.serviceFormMap.put(paraKey, formPara);
				} 
			} catch (ClassNotFoundException e) { 
				e.printStackTrace();
			} 
		}
		if(requestContext.getMessageResource()!=null && requestContext.getRequest()!=null 
				&& !StringUtils.isNoValue(requestContext.getMessageResource().getFormToJsonMethods())){
			String[] methods=requestContext.getMessageResource().getFormToJsonMethods().split(",");
			for(int i=0;i<methods.length;i++){
				if(methods[i].equals(requestContext.getAppMethod()) || methods[i].equals("*")){
					requestContext.getRequest().setAttribute(formPara+"Json",JSON.toJSONString(form,SerializerFeature.DisableCircularReferenceDetect));
				}
			}
		}
	}
	public Object executeMethod(  ) throws Exception{ 
		if(request==null) throw new Exception("request Object is empty!");
		RequestContextParser pathParser=new RequestContextParser();
		RequestContext requestContext=(RequestContext)request.getAttribute(Globals.APP_REQUESTCONTEXT);
		 
		requestContext.setNativeWebRequest(nativeWebRequest); 
		requestContext.setMethodType(methodType);
		requestContext.setAppMethod(methodName);  
		pathParser.parseRequestPath(requestContext,request,urlPrefix,methodType);  
		if(requestContext.getAppService()==null){
			requestContext.setAppService("userService");
		}
		Object appService=AppContext.getBean(requestContext.getAppService()); 
		if(appService==null) {
			String error="Could not find service [" + requestContext.getAppService() + "] ";
			log.error(error);
			throw new  java.lang.NullPointerException(error);
		}
		String className=appService.getClass().getName(); 
		if(className.indexOf("$$")>0)
			className=className.substring(0, className.indexOf("$$"));
		requestContext.setServiceClassName(className);
		RequestContextParser.setAppUrl(requestContext, appService);
		pathParser.parseUrl(appService, requestContext);
		String methodNameStr=requestContext.getAppMethod();  
		Method method=ReflectUtils.findMethod(appService.getClass(), methodNameStr);
		if(method == null){ 
			String error="Could not find method [" + methodNameStr + "] on target [" + className + "]";
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		requestContext.setAppServiceBean(appService);
		IBaseServiceManger baseService= AppContext.getBaseService();
		IBaseInvokeEvent messageResourceResolver=AppContext.getBean(AppContextResource.getStr(Globals.messageResourceResolver), IBaseInvokeEvent.class);
		BindMessageResource resource=null;
		if(messageResourceResolver!=null){
			resource=(BindMessageResource)messageResourceResolver.execute(requestContext,null,baseService,appService,null,"");
			if(resource!=null) {
				requestContext.setMessageResource(resource);
				requestContext.setAppName(resource.getAppName());
			}
		}
		method.setAccessible(true); 
		Object[] params = new Object[method.getParameterCount()];
		int formIndex=-1;
		for(int i=0;i<params.length;i++){ 
			if(ReflectUtils.isImplementsInterface(request.getClass(),method.getParameters()[i].getType())==true){
				params[i]=request;
			} else if(ReflectUtils.isImplementsInterface(response.getClass(),method.getParameters()[i].getType())==true){
				params[i]=response;
			}   else if(NativeWebRequest.class.isAssignableFrom(method.getParameters()[i].getType() )==true){
				params[i]=nativeWebRequest;
			}   else if(IBaseModel.class.isAssignableFrom(method.getParameters()[i].getType() )==true){
				params[i]=form;  
				formIndex=i; 
			} else if(method.getParameters()[i].getType()==RequestContext.class){
				params[i]=requestContext;
			}  else if(IBaseServiceManger.class.isAssignableFrom(method.getParameters()[i].getType())==true){
				params[i]=baseService;
			} else if(method.getParameters()[i].getType()==RedirectAttributesModelMap.class){
				params[i]=attr;
			}  else  
				params[i]=null;
		}
		requestContext.setModel(form);
		String appUrl=requestContext.getAppUrl();
		Object value=method.invoke(appService, params);
		setFormToAttribute(requestContext,formIndex);
		requestContext.setReturnValue(value);
		Object v=value;
		 if(request!=null && ServletFileUpload.isMultipartContent(request)
			  && "1".equals(AppContextResource.getStr("startResponseWrite"))){
			 if( value!=null &&  Void.class.isAssignableFrom(value.getClass() )==false && 
						ReflectUtils.existPropertyOfBean(value, "needWriteResponse") && 
						ModelAndView.class.isAssignableFrom(value.getClass() )==false){
				 String needWriteResponse=(String)ReflectUtils.getFieldValue(value, "needWriteResponse");
				 if(!"0".equals(needWriteResponse)) v= null ;
			 } else 
				 v= null ;
		 }
		 formatOperatePromptContent(v,requestContext);
		 ModelAndView errorView=forwardAlertMsg(v,  requestContext);
		if(Globals.URL_JSONTYPE.equals(methodType)){
			return v;
		} else if(Globals.URL_RECRTOJSONTYPE.equals(methodType)){
			return v;
		}  else if(Globals.URL_STREAMTYPE.equals(methodType)){
			return v;
		} 
		if(errorView!=null) return errorView;
		if(!StringUtils.isNoValue(requestContext.getAppUrl())){
			if(!requestContext.getAppUrl().equals(appUrl)){
				RequestContextParser.setAppUrl(requestContext, appService,requestContext.getAppUrl());
			}
		}
		if(method.getReturnType()!=void.class){
			if(ModelAndView.class.isAssignableFrom(method.getReturnType() )==true){//为跳转视图
				return value;
			} else {
				if(value!=null && String.class.isAssignableFrom(value.getClass())){//为跳转页面 
					String url=(String)value;
					if(url.indexOf("/")<0){
						url=RequestContextParser.parseMiddlePackageUrl(appService)+"/"+StringUtils.upperCaseFirst(url); 
					}
					return url;
				} else {
					ModelAndView view=new ModelAndView(); 
					appendUrlSuffix(methodNameStr,requestContext,resource); 
					view.setViewName( requestContext.getAppUrl());
					return view;
				}
			}
		} else {
			ModelAndView view=new ModelAndView();   
			appendUrlSuffix(methodNameStr,requestContext,resource); 
			view.setViewName( requestContext.getAppUrl());
			return view;
		}  
		 
	}
	private void formatOperatePromptContent(Object v,RequestContext rc){
		if(v!=null && v.getClass()!=void.class){
			 if( OperatePromptBean.class.isAssignableFrom(v.getClass())==true){//为跳转视图
				OperatePromptBean op=(OperatePromptBean)v;
				String value="";
				if(StringUtils.isNoValue(op.getStatememt())){
					String key="operatePrompt."+rc.getAppMethod();
					value=rc.getLang(key); 
				} else {
					if(rc.containsLangKey(op.getStatememt())){
						value=rc.getLang(op.getStatememt());
					} else {
						value=op.getStatememt();
					}
				}
				if(StringUtils.isNoValue(value))
					value=rc.getLang("operatePrompt.defaultOperate");
				if(ScriptParseUtil.validateExecScript(value)){
					Map contextMap=new HashMap();
					contextMap.put(rc.getFormName(), rc.getModel());
					contextMap.put(Globals.APP_REQUESTCONTEXT,rc);
					value=ScriptParseUtil.parseScriptFromExpress(value,contextMap);    
					op.setStatememt(value);
				} else {
					op.setStatememt(value);
				}
			 }
		}
	}
	private String getStrContext(String str,RequestContext rc){
		String value=str; 
		if(rc.containsLangKey(str)){
			value=rc.getLang(str);
		}  
		if(ScriptParseUtil.validateExecScript(value)){
			Map contextMap=new HashMap();
			contextMap.put(rc.getFormName(), rc.getModel());
			contextMap.put(Globals.APP_REQUESTCONTEXT,rc);
			value=ScriptParseUtil.parseScriptFromExpress(value,contextMap);    
		}
		return value;
	}
	private ModelAndView forwardAlertMsg(Object v,RequestContext rc){
		if(request!=null){ 
			if(rc!=null){
				if(rc.getError()!=null || rc.getConfirm()!=null || rc.getAlertMsg()!=null){
					if(v!=null && v.getClass()!=void.class){
						 if( OperatePromptBean.class.isAssignableFrom(v.getClass())==true){//为跳转视图
							OperatePromptBean op=(OperatePromptBean)v;
							if(rc.getError()!=null)
								op.setError(getStrContext(rc.getError().toString(),rc));
							if(rc.getConfirm()!=null)
								op.setConfirm(getStrContext(rc.getConfirm().toString(),rc));
							if(rc.getAlertMsg()!=null)
								op.setAlertMsg(getStrContext(rc.getAlertMsg().toString(),rc));
						}
						  
					}
					ModelAndView view=new ModelAndView();   
					Map<String, Object> model = new HashMap<String, Object>();
					if(rc.getError()!=null)
						model.put("alertMessage", getStrContext(rc.getError().toString(),rc)); 
					else if(rc.getConfirm()!=null)
							model.put("alertMessage", getStrContext(rc.getConfirm().toString(),rc)); 
					else if(rc.getAlertMsg()!=null)
						model.put("alertMessage", getStrContext(rc.getAlertMsg().toString(),rc));
					view.setViewName(AppContextResource.getStr("forwardAlertMsgPage"));
					view.addAllObjects(model); 
					return view;
				}
			}
		}
		return null;
	}
	private void appendUrlSuffix(String methodNameStr,RequestContext requestContext,BindMessageResource resource){
		String methodPrefix=AppContextResource.getStr("methodPrefix"); 
		String url=requestContext.getRequest().getParameter(Globals.APP_URL);
		if(!StringUtils.isNoValue(url)) return ;
		if(resource!=null && methodPrefix!=null && resource.getViewFileSuffix()!=null){
			String[] methodPrefixs=methodPrefix.split(",");
			for(int k=0;k<methodPrefixs.length;k++){
				if(methodPrefixs[k].equals(methodNameStr)){
					requestContext.setAppUrl(requestContext.getAppUrl()+StringUtils.upperCaseFirst(resource.getViewFileSuffix()));
					break;
				}
				 
			}
		} 
	}
}
