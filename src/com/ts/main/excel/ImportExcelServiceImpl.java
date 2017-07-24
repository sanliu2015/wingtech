package com.ts.main.excel;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.ts.core.common.am.StringUtil;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.exception.SysException;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.excel.importplan.ImportPlanForm;

@Service("importExcelService")
public class ImportExcelServiceImpl implements IAppService {

	public void init(RequestContext requestContext, IBaseServiceManger service) {
		String planNumber = requestContext.getRequest().getParameter("planNumber");
		String headNo = requestContext.getRequest().getParameter("headNo");
		if (StringUtil.isNoValue(headNo)) {
			headNo = "1";
		}
		StringBuilder sql = new StringBuilder(100);
		sql.append("select id planId,templateName from Excel_ImportPlan where number=?");
		List<Map<String, Object>> rsList = service.getDb().findForJdbc(sql.toString(), new Object[]{planNumber});
		if (rsList != null && rsList.size() > 0) {
			requestContext.getRequest().setAttribute("headNo", headNo);
			requestContext.getRequest().setAttribute("planId", rsList.get(0).get("planId"));
			requestContext.getRequest().setAttribute("templateName", rsList.get(0).get("templateName"));
		} else {
			requestContext.setError(new StringBuffer("找不到导入模板，请联系管理员!"));
		}
	}
	
	public OperatePromptBean doSubmit(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String planId = requestContext.getRequest().getParameter("planId");		// 导入方案id
		String headNo = requestContext.getRequest().getParameter("headNo");		// 标题行号
		String sheetNo = requestContext.getRequest().getParameter("sheetNo");	// sheet位置号从1开始
		int headerNum = 1;
		int sheetIndex = 0;
		if (!"".equals(headNo)) {
			headerNum = Integer.valueOf(headNo);
		}
		if (!"".equals(sheetNo)) {
			sheetIndex = Integer.valueOf(sheetNo) - 1;
		}
		DefaultMultipartHttpServletRequest mvcRequest = (DefaultMultipartHttpServletRequest) requestContext.getRequest();
		MultipartFile file = mvcRequest.getFile("excelFile");
		ImportExcelUtil ei = new ImportExcelUtil(file, headerNum-1, sheetIndex);		// 包括标题行的数据
		OperatePromptBean opb = new OperatePromptBean();
		if (ei.getLastDataRowNum() <= ei.getDataRowNum()-1) {
			opb.setError("没有要处理的的数据行!");
		} else {
			StringBuilder sql = new StringBuilder(100);
			sql.append("select beforeSp,middleSp,afterSp from Excel_ImportPlan where id=?");
			List<Map<String, Object>> planList = service.getDb().findForJdbc(sql.toString(), new Object[]{planId});
			String beforeSp = planList.get(0).get("beforeSp").toString();
			String middleSp = planList.get(0).get("middleSp").toString();
			String afterSp = planList.get(0).get("afterSp").toString();
			
			// 导入前
			service.getDb().sp().call(beforeSp, null);
			
			// 导入时
			sql.setLength(0);
			sql.append("select title,colName,colType from Excel_ImportPlanDtl where hdrId=?");
			List<Map<String, Object>> dtlList = service.getDb().findForJdbc(sql.toString(), new Object[]{planId});
			Map<String, Object> titleMap = new HashMap<String, Object>();
			Map<String, Map> colNameMap = new HashMap<String, Map>();
			for (int i=0,len=dtlList.size();i<len;i++) {
				titleMap.put(dtlList.get(i).get("title").toString(), dtlList.get(i).get("colName"));
				Map<String, Object> newMap = new HashMap<String, Object>();
				newMap.put("colType", dtlList.get(i).get("colType"));
				colNameMap.put(dtlList.get(i).get("colName").toString(), newMap);
			}
			
			Row titleRow = ei.getRow(ei.getDataRowNum()-1);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(titleRow, j);
				if (titleMap.containsKey(val)) {
					colNameMap.get(titleMap.get(val)).put("cellIndex", j);
				}
			}
			
			String middleSpName = middleSp.substring(0, middleSp.indexOf("("));
			middleSpName += "(";
			String[] params = middleSp.substring(middleSp.indexOf("(")+1, middleSp.indexOf(")")).split(",");
			for (int i=0,len=params.length; i<len; i++) {
				middleSpName += "?";
				if (i!=len-1) {
					middleSpName += ",";
				}
			}
			middleSpName += ")";
			List<CallableParameter> paraList = new ArrayList<CallableParameter>();
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
				Row row = ei.getRow(i);	
				for (int j=0,len=params.length; j<len; j++) {
					CallableParameter param = new CallableParameter(); 
					param.setParameterValue(ei.getCellValue(row, Integer.valueOf(colNameMap.get(params[j]).get("cellIndex").toString())));
					if ("".equals(param.getParameterValue())) {
						param.setParameterValue(null);
					}
					if ("int".equals(colNameMap.get(params[j]).get("colType").toString())) {
						param.setSqlParameter(new SqlParameter(params[j], java.sql.Types.INTEGER));
					} else if ("decimal".equals(colNameMap.get(params[j]).get("colType").toString())) {
						param.setSqlParameter(new SqlParameter(params[j], java.sql.Types.DECIMAL));
					} else {
						param.setSqlParameter(new SqlParameter(params[j], java.sql.Types.VARCHAR));
					}
					paraList.add(param);
				}
				service.getDb().sp().call(middleSpName, paraList);
				paraList.clear();
			}
			
			
			// 导入后
			if (afterSp.indexOf("(") == -1) {
				service.getDb().sp().call(afterSp, null);
			} else {
				String afterSpName = afterSp.substring(0, afterSp.indexOf("("));
				afterSpName += "(";
				String[] afterParams = afterSp.substring(afterSp.indexOf("(")+1, afterSp.indexOf(")")).split(",");
				for (int i=0,len=afterParams.length; i<len; i++) {
					afterSpName += "?";
					if (i!=len-1) {
						afterSpName += ",";
					}
				}
				afterSpName += ")";
				
				for (int j=0,len=afterParams.length; j<len; j++) {
					CallableParameter param = new CallableParameter(); 
					if ("currentEmpId".equals(afterParams[j])) {
						param.setSqlParameter(new SqlParameter("currentEmpId", java.sql.Types.INTEGER));
						param.setParameterValue(requestContext.getUser().getEmployeeId());
					} else if ("retMsg".equals(afterParams[j])) {
						param.setParameterValue("");
						param.setSqlParameter(new SqlOutParameter("retMsg", Types.VARCHAR)); 
					} else {
						throw new SysException("只支持限定参数:currentEmpId,retMsg");
					}
					
					paraList.add(param);
				}
				
				Map<String,Object> retMsgMap = service.getDb().sp().call(afterSpName, paraList);
				
				if (retMsgMap.get("retMsg") != null && !"".equals(retMsgMap.get("retMsg"))) {
					opb.setStatememt(retMsgMap.get("retMsg").toString());
				} else {
					opb.setStatememt("导入成功!");
				}
				
			}
			
		}
		
		
		return opb;
	}
	
}
