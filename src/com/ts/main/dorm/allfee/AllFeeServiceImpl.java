package com.ts.main.dorm.allfee;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("allFeeService")
public class AllFeeServiceImpl implements IAppService {

	public void generateAllFee(RequestContext requestContext, IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean doGenerateAllFee(RequestContext requestContext, IBaseServiceManger service) {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String generateAllFeeSp = "" + requestContext.getMessageResource().get("generateAllFeeSp");
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(yearMonth);
		param1.setSqlParameter(new SqlParameter("yearMonth", java.sql.Types.VARCHAR));
		paraList.add(param1);
		service.getDb().sp().call(generateAllFeeSp, paraList);
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	
	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service) {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String deleteAllFeeSp = "" + requestContext.getMessageResource().get("deleteAllFeeSp");
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(yearMonth);
		param1.setSqlParameter(new SqlParameter("yearMonth", java.sql.Types.VARCHAR));
		paraList.add(param1);
		service.getDb().sp().call(deleteAllFeeSp, paraList);
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	
	
	public void exportExcelInit(RequestContext requestContext, IBaseServiceManger service) {
		
	}
	
	public String exportExcel(RequestContext requestContext,IBaseServiceManger service) throws Exception {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		Map<String, Object> dataMap = new HashMap<String, Object> ();	
		String exportSql = "" + requestContext.getMessageResource().get("exportSql");
		
		List<Map<String, Object>> dtlList = service.getDb().findForJdbc(exportSql, new Object[]{yearMonth});
		dataMap.put("totalRow", dtlList.size());
		dataMap.put("dataList", dtlList);
		
		String tempFileName = "";										// 临时文件名
		String windowDownloadName = "";									// 下载文件对话框显示文件名
		
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setServletContextForTemplateLoading(requestContext.getRequest().getSession().getServletContext(), "template");
		Template t = null;
		t = configuration.getTemplate("AllFeeExcel.ftl", "utf-8");
		tempFileName = "AllFeeExcel";
		windowDownloadName = yearMonth + "月份员工费用汇总" + new Date().getTime() + ".xls";

		// 输出文档路径
		String fileOutPath = requestContext.getRequest().getSession().getServletContext().getRealPath("/resource/download/temp");
		String fileOutName = tempFileName + new Date().getTime() + ".xls";
		File outFile = new File(fileOutPath + "\\" + fileOutName);
		Writer out = null;
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

		try {
			t.process(dataMap, out);
			out.close();
		} catch (Exception e) {
			out.close();
			outFile.delete();
			throw e;
		} 

		// 输出文件流到保存下载框（中文名）
		BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(fileOutPath + "\\" + fileOutName));
		byte[] arrayOfByte = new byte[localBufferedInputStream.available()];
		localBufferedInputStream.read(arrayOfByte);
		localBufferedInputStream.close();
		requestContext.getResponse().reset(); // 必要地清除response中的缓存信息
		requestContext.getResponse().setHeader("Content-Disposition", "attachment; filename="+ new String(windowDownloadName.getBytes("GBK"),"ISO8859-1"));
		requestContext.getResponse().setContentType("application/vnd.ms-excel");
		requestContext.getResponse().addHeader("Content-Length", "" + outFile.length());
		BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(requestContext.getResponse().getOutputStream());
	    localBufferedOutputStream.write(arrayOfByte);
	    localBufferedOutputStream.flush();
	    localBufferedOutputStream.close();
		outFile.delete();
		
		return null;
	}
	
	public String exportExcelAll(RequestContext requestContext,IBaseServiceManger service) throws Exception {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String buildingId = requestContext.getRequest().getParameter("buildingId");
		Map<String, Object> dataMap = new HashMap<String, Object> ();	
		String exportSql = "" + requestContext.getMessageResource().get("exportSqlAll");
		String orderBySql = "" + requestContext.getMessageResource().get("orderBySqlAll");
		if (!StringUtil.isNoValue(buildingId)) {
			exportSql += " and b.buildingId=" + buildingId;
		}
		
		List<Map<String, Object>> dtlList = service.getDb().findForJdbc(exportSql + orderBySql, new Object[]{yearMonth});
		dataMap.put("totalRow", dtlList.size());
		dataMap.put("dataList", dtlList);
		
		String tempFileName = "";										// 临时文件名
		String windowDownloadName = "";									// 下载文件对话框显示文件名
		
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setServletContextForTemplateLoading(requestContext.getRequest().getSession().getServletContext(), "template");
		Template t = null;
		t = configuration.getTemplate("RoomFeeExcelAll.ftl", "utf-8");
		tempFileName = "RoomFeeExcelALL";
		windowDownloadName = yearMonth + "月份宿舍所有费用汇总" + new Date().getTime() + ".xls";

		// 输出文档路径
		String fileOutPath = requestContext.getRequest().getSession().getServletContext().getRealPath("/resource/download/temp");
		String fileOutName = tempFileName + new Date().getTime() + ".xls";
		File outFile = new File(fileOutPath + "\\" + fileOutName);
		Writer out = null;
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

		try {
			t.process(dataMap, out);
			out.close();
		} catch (Exception e) {
			out.close();
			outFile.delete();
			throw e;
		} 

		// 输出文件流到保存下载框（中文名）
		BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(fileOutPath + "\\" + fileOutName));
		byte[] arrayOfByte = new byte[localBufferedInputStream.available()];
		localBufferedInputStream.read(arrayOfByte);
		localBufferedInputStream.close();
		requestContext.getResponse().reset(); // 必要地清除response中的缓存信息
		requestContext.getResponse().setHeader("Content-Disposition", "attachment; filename="+ new String(windowDownloadName.getBytes("GBK"),"ISO8859-1"));
		requestContext.getResponse().setContentType("application/vnd.ms-excel");
		requestContext.getResponse().addHeader("Content-Length", "" + outFile.length());
		BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(requestContext.getResponse().getOutputStream());
	    localBufferedOutputStream.write(arrayOfByte);
	    localBufferedOutputStream.flush();
	    localBufferedOutputStream.close();
		outFile.delete();
		
		return null;
	}
	
}
