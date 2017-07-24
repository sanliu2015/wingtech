package com.ts.core.common.exception;
 
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;  
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ts.core.common.constant.Globals;
import com.ts.core.context.RequestContext;
import com.ts.core.util.ExceptionUtil;


@Component
public class CoreExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = Logger.getLogger(CoreExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String exceptionMessage = null;
		Map<String, Object> model = new HashMap<String, Object>();
		if (ex instanceof InvocationTargetException) {		
			InvocationTargetException invEx = (InvocationTargetException)ex;
			exceptionMessage = invEx.getTargetException().getCause() == null ? invEx.getTargetException().getMessage() : invEx.getTargetException().getCause().getMessage();
			model.put("exceptionMessage", exceptionMessage);
		} else {
			exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
			model.put("exceptionMessage", exceptionMessage);
		}
		logger.error(exceptionMessage);
		model.put("ex", ex);
		ModelAndView view=new ModelAndView();
		view.setViewName("/core/common/exception/ShowError");
		view.addAllObjects(model);
		if(request!=null){
			RequestContext requestContext=(RequestContext)request.getAttribute(Globals.APP_REQUESTCONTEXT);
			if(requestContext!=null){
				StringBuffer error=new StringBuffer("");
				error.append(exceptionMessage);
				requestContext.setError(error);
			}
		}
		return view;
	}
}
