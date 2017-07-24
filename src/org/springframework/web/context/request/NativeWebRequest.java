package org.springframework.web.context.request;
import java.util.Map;
public interface NativeWebRequest extends WebRequest { 
	Object getNativeRequest(); 
	Object getNativeResponse(); 
	 
	<T> T getNativeRequest(Class<T> requiredType);
 
	<T> T getNativeResponse(Class<T> requiredType);
}
