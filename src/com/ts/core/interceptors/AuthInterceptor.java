package com.ts.core.interceptors;

import com.alibaba.fastjson.JSON;
import com.ts.core.api.cache.SimpleCachePool;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.context.AppContext;
import com.ts.core.context.AppContextResource;
import com.ts.core.context.MatchPatternParser;
import com.ts.core.context.RequestContext;
import com.ts.core.loader.SessionListener;
import com.ts.core.system.module.ModuleBean;
import com.ts.core.system.user.UserBean;
import com.ts.core.util.DateTimeUtil;
import com.ts.core.util.ExceptionUtil;
import com.ts.core.util.ResourceUtil;
import com.ts.core.util.StringUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class AuthInterceptor implements HandlerInterceptor {
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	public static String[] filterUrls;
	public static String[] loginUrls;

	public static String[] getFilterUrls() {
		return filterUrls;
	}

	public static void setFilterUrls(String[] filterUrls) {
		filterUrls = filterUrls;
	}

	public static String[] getLoginUrls() {
		return loginUrls;
	}

	public static void setLoginUrls(String[] loginUrls) {
		loginUrls = loginUrls;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		if (DateTimeUtil.formatDate().compareTo("2020-12-20") > 0) {
//			return false;
//		}
		String requestPath = request.getServletPath();
		HttpSession session = request.getSession();

		boolean isValateLogin = true;
		if (!(StringUtils.isNoValue(requestPath))) {
			if (requestPath.indexOf("/") != 0)
				requestPath = "/" + requestPath;
			if (filterUrls != null) {
				for (int i = 0; i < filterUrls.length; ++i) {
					if (requestPath.indexOf(filterUrls[i]) >= 0) {
						isValateLogin = false;
						break;
					}
					if (filterUrls[i].indexOf("*") > 0) {
						boolean isMatch = MatchPatternParser.validateMatchContent(requestPath, filterUrls[i]);
						if (isMatch) {
							isValateLogin = false;
							break;
						}
					}
				}
				if (isValateLogin) {
					UserBean user = (UserBean) session.getAttribute(Globals.LOGIN_USER);
					if ((user == null) || (requestPath.equals("/"))) {
						if (requestPath.indexOf("/app/") >= 0)
							response.sendRedirect(request.getContextPath() + loginUrls[1]);
						else {
							response.sendRedirect(request.getContextPath() + loginUrls[0]);
						}
						return false;
					}
				}
			}
		}
		UserBean user = (UserBean) session.getAttribute(Globals.LOGIN_USER);

		RequestContext requestContext = new RequestContext();
		requestContext.setRequest(request);
		requestContext.setResponse(response);
		requestContext.setSessionId(session.getId());
		requestContext.setCurrentDate(DateTimeUtil.formatDate());
		requestContext.setCurrentTime(DateTimeUtil.formatTime());
		requestContext.setStartTime(DateTimeUtil.formatTimestamp());
		requestContext.setRequestPath(requestPath);
		requestContext.setIpAddr(AppContext.getIpAddr(request));
		requestContext.setHandler(handler);

		if (user != null) {
			requestContext.initRequestContext(user);
			if ((user != null) && (!(SessionListener.isOnline(user, requestContext)))) {
				SessionListener.addLogin(user, requestContext);
			}

			if ((!(SessionListener.isOnline(user, requestContext))) && (isValateLogin)) {
				if (requestPath.indexOf("/app/") >= 0)
					response.sendRedirect(request.getContextPath() + loginUrls[1]);
				else {
					response.sendRedirect(request.getContextPath() + loginUrls[0]);
				}
				return false;
			}
		} else
			if ((requestPath.indexOf("/index") >= 0) && (!("userService".equals(request.getParameter("appService"))))) {
			if (requestPath.indexOf("/app/") >= 0)
				response.sendRedirect(request.getContextPath() + loginUrls[1]);
			else {
				response.sendRedirect(request.getContextPath() + "/");
			}
			return false;
		}

		request.setAttribute(Globals.APP_REQUESTCONTEXT, requestContext);
		String urlPath = ResourceUtil.getRequestFullPath(request);
		if ((user != null) && (urlPath.indexOf("/app/") < 0) && (urlPath.indexOf("operateAuthCheckFlag=0") < 0)) {
			urlPath = clearExcessParam(urlPath, "timeStamp");
			urlPath = clearExcessParam(urlPath, "openQueryResult");
			urlPath = clearExcessParam(urlPath, "targetName");
			boolean haveAuth = operateAuthCheck(urlPath, user);
			if (!(haveAuth)) {
				response.sendRedirect(request.getContextPath() + "/pages/core/common/ShowNoOperateAuth.jsp");
				return false;
			}
		}
		return true;
	}

	public static String clearExcessParam(String urlPath, String param) {
		if (urlPath.indexOf(param) > 0) {
			String s = urlPath.substring(0, urlPath.indexOf(param) - 1);
			String afterStr = urlPath.substring(urlPath.indexOf(param));
			String[] arr = afterStr.split("&");
			afterStr = "";
			if (arr.length > 1) {
				for (int i = 1; i < arr.length; ++i) {
					if (i == 1)
						afterStr = arr[i];
					else {
						afterStr = afterStr + "&" + arr[i];
					}
				}
			}
			if (s.indexOf("?") == s.length() - 1)
				if (afterStr.equals(""))
					s = s.substring(0, s.length() - 1);
				else
					s = s + afterStr;
			else if (!(afterStr.equals(""))) {
				s = s + "?" + afterStr;
			}
			return s;
		}
		return urlPath;
	}

	public static boolean operateAuthCheck(String urlPath, UserBean user) {
		if (user.isAdmin())
			return true;
		List modules = (List) SimpleCachePool.loginModuleMap.get(user.getId());
		if (modules == null)
			return true;
		List idList = null;
		for (int i = 0; i < modules.size(); ++i) {
			Integer id = (Integer) modules.get(i);
			ModuleBean bean = (ModuleBean) SimpleCachePool.moduleMap.get(id);
			if (bean != null)
				if (!(StringUtils.isNoValue(bean.getUrlName()))) {
					String url = bean.getUrlName();
					if (url.indexOf("openQueryResult=") > 0)
						url = clearExcessParam(url, "openQueryResult");
					if (urlPath.equals(url)) {
						return true;
					}
					if (urlPath.indexOf(url) == 0) {
						if (idList == null)
							idList = new ArrayList();
						idList.add(id);
					}
				}
		}
		boolean notExistUrl = false;
		for (int i = 0; i < SimpleCachePool.moduleList.size(); ++i) {
			ModuleBean bean = (ModuleBean) SimpleCachePool.moduleList.get(i);
			String url = bean.getUrlName();
			if (!(StringUtils.isNoValue(url))) {
				if (url.indexOf("openQueryResult=") > 0)
					url = clearExcessParam(url, "openQueryResult");
				if (urlPath.equals(url))
					return false;
				if (urlPath.indexOf(url) == 0) {
					notExistUrl = false;
					if ((idList != null) && (idList.size() > 0)) {
						for (int k = 0; k < idList.size(); ++k) {
							Integer id = (Integer) idList.get(k);
							if (bean.getId().intValue() == id.intValue()) {
								notExistUrl = true;
								break;
							}
						}
					}
					if (!(notExistUrl))
						return false;
				}
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
		if ((request != null) && (ServletFileUpload.isMultipartContent(request))) {
			RequestContext requestContext = (RequestContext) request.getAttribute(Globals.APP_REQUESTCONTEXT);
			if ((requestContext != null) && ("1".equals(AppContextResource.getStr("startResponseWrite")))) {
				Object returnValue = requestContext.getReturnValue();
				if ((returnValue == null) || (Void.class.isAssignableFrom(returnValue.getClass()))
						|| (ModelAndView.class.isAssignableFrom(returnValue.getClass())))
					return;
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
					String jsonString = JSON.toJSONString(returnValue);
					out.println(jsonString);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
		RequestContext requestContext = (RequestContext) request.getAttribute(Globals.APP_REQUESTCONTEXT);
		if (requestContext == null)
			return;
		if (request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE) != null) {
			Exception ex = (Exception) request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
			if (requestContext.getError() == null)
				requestContext.setError(new StringBuffer(""));
			String exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
			requestContext.getError().append(exceptionMessage);
		}
		requestContext.setEndTime(DateTimeUtil.formatTimestamp());
		Date startDate = DateTimeUtil.parseStringToDate(requestContext.getStartTime(), "yyyy-MM-dd HH:mm:ss.SSS");
		long seconds = DateTimeUtil.twoDateIntervalSeconds(startDate, new Date());
		requestContext.setExecuteSeconds(Long.valueOf(seconds));
		StringBuffer logStr = new StringBuffer();
		logStr.append("startDate：" + requestContext.getStartTime());
		logStr.append(" total time:" + seconds + "秒");
		if (requestContext.getUser() != null)
			logStr.append(" operator：" + requestContext.getUser().getName());
		logStr.append(" handle Module Service:" + requestContext.getAppKey());
		logStr.append(" handle Module Name:" + requestContext.getAppName());
		logStr.append(" handle Method：" + requestContext.getAppMethod());
		String requestPath = requestContext.getRequestPath();
		logStr.append("<br/> URL:" + requestPath);
		if ((requestContext.getReturnValue() != null)
				&& (!(Void.class.isAssignableFrom(requestContext.getReturnValue().getClass())))
				&& (OperatePromptBean.class.isAssignableFrom(requestContext.getReturnValue().getClass()))) {
			OperatePromptBean promptBean = (OperatePromptBean) requestContext.getReturnValue();
			logStr.append("<br/> ");
			logStr.append(" id=" + promptBean.getId());
			logStr.append(" number=" + promptBean.getNumber());
			logStr.append(" statemember=" + promptBean.getStatememt());
			if ((promptBean.getCustomProperty() != null) && (promptBean.getCustomProperty().size() > 0)) {
				logStr.append(" map=" + promptBean.getCustomProperty().toString());
			}

		}

		if ((requestContext.getError() != null) && (!(StringUtils.isNoValue(requestContext.getError().toString())))) {
			logStr.append("<br/> ");
			logStr.append(requestContext.getError().toString());
		}
		logger.warn(logStr);
	}

	@RequestMapping(params = { "forword" })
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	private void forward(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("webpage/login/timeout.jsp").forward(request, response);
		System.out.println("forward=");
	}
}