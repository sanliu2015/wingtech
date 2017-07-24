package com.ts.core.common.controller;
 
  
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.context.request.NativeWebRequest;  
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap; 

import com.ts.core.common.constant.Globals;
import com.ts.core.common.form.IBaseModel;

@Controller
@RequestMapping(value = "/"+Globals.URL_MAINPREFIX+"/**")
public class MainController {
	 
	 
	@RequestMapping(value = "/"+Globals.URL_JSONTYPE+"/{methodName}."+Globals.URL_SUBFIX, produces = "application/json;charset=UTF-8") 
	@ResponseBody
    public  Object returnJsonContent(NativeWebRequest nativeWebRequest, HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr,@PathVariable(value="methodName") String methodName,IBaseModel form) throws Exception{    
		InvocableMethodHandler handler=new InvocableMethodHandler(Globals.URL_JSONTYPE,Globals.URL_MAINPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		Object obj=handler.executeMethod();
        return obj;
    }
	
	@RequestMapping(value = "/"+Globals.URL_RECJSONTYPE+"/{methodName}."+Globals.URL_SUBFIX)
    public Object recJsonAction(NativeWebRequest nativeWebRequest,HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr,@PathVariable(value="methodName") String methodName, @RequestBody(required=false)IBaseModel  form) throws Exception{
		InvocableMethodHandler handler=new InvocableMethodHandler(Globals.URL_RECJSONTYPE,Globals.URL_MAINPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		Object obj=handler.executeMethod();
        return obj;
    }
	@RequestMapping(value = "/"+Globals.URL_RECRTOJSONTYPE+"/{methodName}."+Globals.URL_SUBFIX)//, produces = "application/json;charset=UTF-8"
	@ResponseBody
    public Object recAndReturnJson(NativeWebRequest nativeWebRequest,HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr,@PathVariable(value="methodName") String methodName, @RequestBody IBaseModel  form) throws Exception{
		InvocableMethodHandler handler=new InvocableMethodHandler(Globals.URL_RECRTOJSONTYPE,Globals.URL_MAINPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		Object obj=handler.executeMethod();
        return obj;
    }
	@RequestMapping(value = "/"+Globals.URL_STREAMTYPE+"/{methodName}."+Globals.URL_SUBFIX ) 
    public  void requestStream(NativeWebRequest nativeWebRequest, HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr,@PathVariable(value="methodName") String methodName,IBaseModel form) throws Exception{    
		InvocableMethodHandler handler=new InvocableMethodHandler(Globals.URL_STREAMTYPE,Globals.URL_MAINPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		 handler.executeMethod(); 
    }
	@RequestMapping(value = "/"+Globals.URL_EXECUTETYPE+"/{methodName}."+Globals.URL_SUBFIX)
    public Object execute(NativeWebRequest nativeWebRequest,HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr,@PathVariable(value="methodName") String methodName,IBaseModel form) throws Exception{
		InvocableMethodHandler handler=new InvocableMethodHandler(Globals.URL_EXECUTETYPE,Globals.URL_MAINPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		Object obj=handler.executeMethod();
        return obj;
    }
	@RequestMapping(value = "/{methodName}."+Globals.URL_SUBFIX)
    public Object invokeMethod(NativeWebRequest nativeWebRequest,HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr,@PathVariable(value="methodName") String methodName,IBaseModel form) throws Exception{
		InvocableMethodHandler handler=new InvocableMethodHandler("",Globals.URL_MAINPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		Object obj=handler.executeMethod();
        return obj;
    } 
	
	 
}
