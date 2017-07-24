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
public class CoreLoginController {
	 
	 
	@RequestMapping(value = "/index."+Globals.URL_SUBFIX)
    public Object invokeMethod(NativeWebRequest nativeWebRequest,HttpServletRequest request,HttpServletResponse response,RedirectAttributesModelMap attr, IBaseModel form) throws Exception{
		String methodName="index"; 
		InvocableMethodHandler handler=new InvocableMethodHandler("",Globals.URL_COREPREFIX,methodName,nativeWebRequest,request,response,attr,form);		
		Object obj=handler.executeMethod(); 
        return obj;
    } 
}
