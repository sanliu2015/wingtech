package com.ts.main.util;

import java.io.PrintWriter;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.util.DateTimeUtil;

@Service("publicUtilService")
public class PublicUtilServiceImpl implements IAppService{	

	public String exportExcel(RequestContext requestContext,IBaseServiceManger service) {
		try {
			requestContext.getResponse().reset(); // 必要地清除response中的缓存信息
			requestContext.getResponse().setCharacterEncoding("utf-8");
			requestContext.getResponse().setHeader("Content-Disposition", "attachment; filename=" + new String(requestContext.getRequest().getParameter("excelName").getBytes("GBK"),"ISO8859-1") + DateTimeUtil.formatDateTime() + ".xls");
			requestContext.getResponse().setContentType("application/vnd.ms-excel");
			PrintWriter out = requestContext.getResponse().getWriter();
			out.write("<html>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<head>\n");
			out.write("<style type=\"text/css\">\n.pb{font-size:13px;border-collapse:collapse;} "
					+ "\n.pb th{font-weight:bold;text-align:center;border:0.5pt solid windowtext;padding:2px;} "
					+ "\n.pb td{border:0.5pt solid windowtext;padding:2px;}\n</style>\n</head>\n");
			out.write("<body>\n"
					+ requestContext.getRequest().getParameter("excelContent")
					+ "\n</body>\n</html>");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
