package com.ts.main.dorm.checkin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.exception.SysException;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.core.util.DateTimeUtil;
import com.ts.main.dorm.damage.Damage;
import com.ts.main.excel.ImportExcelUtil;
import com.ts.main.util.HrorcDbUtil;
import com.ts.main.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("checkInService")
public class CheckInServiceImpl implements IAppService {
	
	public void show(RequestContext requestContext, IBaseServiceManger service) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select id,name from DORM_Building where kind='0'");
		List<Map<String, Object>> buildList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("buildList", JSON.toJSONString(buildList));
		sql.setLength(0);
		sql.append("select id,name,parentId from Dorm_Building where kind='1'");
		List<Map<String, Object>> unitList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("unitList", JSON.toJSONString(unitList));
//		sql.setLength(0);
//		sql.append("select id,name,parentId from HR_Department");
//		List<Map<String, Object>> deptList = service.getDb().findForJdbc(sql.toString());
//		// 增加空数据选项
//		Map<String, Object> tempMap = new HashMap<String, Object>();
//		tempMap.put("id", "");
//		tempMap.put("name", "空数据");
//		tempMap.put("parentId", null);
//		deptList.add(tempMap);
//		requestContext.getRequest().setAttribute("deptList", JSON.toJSONString(deptList));
	}
	
	// 查询房间信息：未入住或未住满
	public String queryRoom(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String build = requestContext.getRequest().getParameter("build");
		String unit = requestContext.getRequest().getParameter("unit");
		String roomName = requestContext.getRequest().getParameter("roomName");
		String roomType = requestContext.getRequest().getParameter("roomType");
		String canInBg = requestContext.getRequest().getParameter("canInBg");
		String canInEd = requestContext.getRequest().getParameter("canInEd");
		String queryRoom = "" + requestContext.getMessageResource().get("queryRoom");
		StringBuilder whereSql = new StringBuilder(100);
		if (StringUtil.isNoValue(canInBg)) {
			whereSql.append(" where a.bigNumber-isnull(b.inSum,0)>=1 ");
		} else {
			whereSql.append(" where a.bigNumber-isnull(b.inSum,0)>=").append(canInBg).append(" ");
		}
		if (!StringUtil.isNoValue(canInEd)) {
			whereSql.append("and a.bigNumber-isnull(b.inSum,0)<=").append(canInEd).append(" ");
		}
		
		if (!StringUtil.isNoValue(build)) {
			whereSql.append("and a.buildingId=").append(build).append(" ");
		}
		if (!StringUtil.isNoValue(unit)) {
			whereSql.append("and a.unitId=").append(unit).append(" ");
		}
		if (!StringUtil.isNoValue(roomName)) {
			whereSql.append("and a.roomName like '%").append(roomName).append("%' ");
		}
		if (!StringUtil.isNoValue(roomType)) {
			whereSql.append("and a.type='").append(roomType).append("' ");
		}
		List<Map<String, Object>> roomList = service.getDb().findForJdbc(queryRoom+whereSql);
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(roomList));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	// 查询入住员工
	public String queryEmp(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String empName = requestContext.getRequest().getParameter("empName");
		String number = requestContext.getRequest().getParameter("number");
		String idCard = requestContext.getRequest().getParameter("idCard");
		String gender = requestContext.getRequest().getParameter("gender");
		String interimId = requestContext.getRequest().getParameter("interimId");
		String queryEmp = "" + requestContext.getMessageResource().get("queryEmp");
		String orderSql = "" + requestContext.getMessageResource().get("orderSql");
		StringBuilder whereSql = new StringBuilder(100);
		if (!StringUtil.isNoValue(empName)) {
			whereSql.append("and a.name='").append(empName).append("' ");
		}
		if (!StringUtil.isNoValue(number)) {
			whereSql.append("and a.number like '%").append(number).append("%' ");
		}
		if (!StringUtil.isNoValue(idCard)) {
			whereSql.append("and a.idCard like'%").append(idCard).append("%' ");
		}
		if (!StringUtil.isNoValue(gender)) {
			whereSql.append("and a.gender='").append(gender).append("' ");
		}
		if (!StringUtil.isNoValue(interimId)) {
			whereSql.append("and a.interimId=").append(interimId).append(" ");
		}
		List<Map<String, Object>> roomList = service.getDb().findForJdbc(queryEmp+whereSql+orderSql);
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(roomList));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return null;
	}
	
	// 快速入住
	public String doCheckIn(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String dataList = requestContext.getRequest().getParameter("dataList");
		List<Map<String, Object>> dtlList = JSON.parseObject(dataList, new TypeReference<List<Map<String,Object>>>(){});
		String saveBeforeSp = "" + requestContext.getMessageResource().get("saveBeforeSp");
		String currentDate = DateTimeUtil.formatDate();
		String currentTime = DateTimeUtil.formatTime();
		for (int i=0,len=dtlList.size();i<len;i++) {
			List<CallableParameter> paraList = new ArrayList<CallableParameter>();
			CallableParameter param1 = new CallableParameter(); 
			param1.setParameterValue(dtlList.get(i).get("empId"));
			param1.setSqlParameter(new SqlParameter("empId", java.sql.Types.INTEGER));
			paraList.add(param1);
			service.getDb().sp().call(saveBeforeSp, paraList);
			CheckIn bean = new CheckIn();
			bean.setRoomId(Integer.valueOf(dtlList.get(i).get("roomId").toString()));
			bean.setEmployeeId(Integer.valueOf(dtlList.get(i).get("empId").toString()));
			bean.setKeyStatus("借用");
			bean.setInDate(currentDate);
			bean.setInTime(currentTime);
			bean.setCheckOutFlag(0);
			bean.setHandleEmpId(requestContext.getUser().getEmployeeId());	// 经办人
			service.getDb().saveObject(bean);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "操作成功");
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(resultMap));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
	public void add(RequestContext requestContext, IBaseServiceManger service, CheckInForm form) throws SQLException {
//		QueryRunner qr = new QueryRunner();
//		String sql = "select * from TEST ";
//		List<Object[]> list = (List) qr.query(JDBCUtil.getConnection(),sql, new ArrayListHandler());
//		System.out.println(list.size());
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, CheckInForm form) {
		String saveBeforeSp = "" + requestContext.getMessageResource().get("saveBeforeSp");
		CheckIn bean = form.getBean();
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(bean.getEmployeeId());
		param1.setSqlParameter(new SqlParameter("empId", java.sql.Types.INTEGER));
		paraList.add(param1);
		service.getDb().sp().call(saveBeforeSp, paraList);
		service.getDb().saveObject(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}
	
	public void edit(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) {
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		CheckIn bean = JSON.parseObject(JSON.toJSONString(beanMap), CheckIn.class);
		form.setBean(bean); 
	}
	
	public OperatePromptBean update(CheckInForm form,RequestContext requestContext,IBaseServiceManger service) {
		CheckIn bean = service.getDb().getObject(CheckIn.class, form.getBean().getId(), requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		service.getDb().mergeObject(bean, requestContext);
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public void out(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) {
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		CheckIn bean = JSON.parseObject(JSON.toJSONString(beanMap), CheckIn.class);
		form.setBean(bean); 
	}
	
	public OperatePromptBean doOut(CheckInForm form,RequestContext requestContext,IBaseServiceManger service) {
		CheckIn bean = service.getDb().getObject(CheckIn.class, form.getBean().getId(), requestContext);
		OperatePromptBean opb = new OperatePromptBean();
		if (bean.getCheckOutFlag() != null && bean.getCheckOutFlag().intValue() > 0) {
			opb.setError("已经退宿，不能重复操作！");
		} else {
			BeanUtils.copyNoNullProperties(form.getBean(), bean);
			bean.setCheckOutFlag(1);
			service.getDb().mergeObject(bean, requestContext);
			Damage damage = form.getDamage();
			if (damage.getAmount() != null && damage.getAmount().intValue() > 0) {
				damage.setCheckInId(bean.getId());
				damage.setEmployeeId(bean.getEmployeeId());
				damage.setOccurDate(bean.getOutDate());
				service.getDb().saveObject(damage, requestContext);
			}
		}
		
		return opb;
	}
	
	public void editCheckOut(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) {
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		CheckIn bean = JSON.parseObject(JSON.toJSONString(beanMap), CheckIn.class);
		form.setBean(bean); 
		StringBuilder sql = new StringBuilder(100);
		sql.append("select id from dorm_damage where checkInId=").append(form.getId());
		List<Map<String, Object>> rs = service.getDb().findForJdbc(sql.toString());
		if (rs != null && rs.size() > 0) {
			Damage damage = service.getDb().getObject(Damage.class, (Integer)rs.get(0).get("id"), requestContext);
			form.setDamage(damage);
		}
	}
	
	public void outBatch(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) {
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		String ids = "(" + StringUtil.listMapToStrByKey(selectParamList, "id") + ")";	
		form.setIds(ids);
		String sql = "" + requestContext.getMessageResource().get("batchOutSql");
		sql = sql.replace("?", ids);
		List<Map> rsList = service.getDb().queryForList(sql, requestContext);
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(rsList));
	}
	
	public OperatePromptBean doOutBatch(CheckInForm form,RequestContext requestContext,IBaseServiceManger service) {
		OperatePromptBean opb = new OperatePromptBean();
		StringBuilder sql = new StringBuilder(100); 
		sql.append("select 1 from Dorm_CheckIn where checkOutFlag=1 and id in ").append(form.getIds());
		List<Map> rs = service.getDb().queryForList(sql.toString(), requestContext);
		if (rs != null && rs.size() > 0) {
			opb.setError("有人已经退宿，不能重复操作！");
		} else {
			service.getDb().executeSqlForJdbc("update Dorm_CheckIn set checkOutFlag=1,outDate=?,outTime=?,outReason=? where id in " + form.getIds(), new Object[]{form.getBean().getOutDate(),form.getBean().getOutTime(),form.getBean().getOutReason()});
			for(Damage temp : form.getDtlList()) {
				if (temp.getAmount() != null && temp.getAmount().intValue() > 0) {
					temp.setOccurDate(form.getBean().getOutDate());
					service.getDb().saveObject(temp, requestContext);
				}
			}
		}
		return opb;
	}
	
	public OperatePromptBean delete(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			String deleteSp = "" + requestContext.getMessageResource().get("deleteSp");
			// 删除
			if (!StringUtil.isNoValue(deleteSp)) {
				for (int i=0,len=idList.size(); i<len; i++) {
					List<CallableParameter> paraList1 = new ArrayList<CallableParameter>();
					CallableParameter param1 = new CallableParameter(); 
					param1.setParameterValue(idList.get(i));
					param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
					paraList1.add(param1);
					service.getDb().sp().call(deleteSp, paraList1);
				}
			}
			
		}
		return opb;	
	}
	
	public OperatePromptBean doUndo(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			String undoSp = "" + requestContext.getMessageResource().get("undoSp");
			// 删除
			if (!StringUtil.isNoValue(undoSp)) {
				for (int i=0,len=idList.size(); i<len; i++) {
					List<CallableParameter> paraList1 = new ArrayList<CallableParameter>();
					CallableParameter param1 = new CallableParameter(); 
					param1.setParameterValue(idList.get(i));
					param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
					paraList1.add(param1);
					service.getDb().sp().call(undoSp, paraList1);
				}
			}
			
		}
		return opb;	
	}
	
	public void adjust(RequestContext requestContext, IBaseServiceManger service) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select id,name from DORM_Building where kind='0'");
		List<Map<String, Object>> buildList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("buildList", JSON.toJSONString(buildList));
		sql.setLength(0);
		sql.append("select id,name,parentId from Dorm_Building where kind='1'");
		List<Map<String, Object>> unitList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("unitList", JSON.toJSONString(unitList));
	}
	
	public String queryRoomEmp(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		Integer roomId = Integer.valueOf(requestContext.getRequest().getParameter("roomId"));
		String querySql = "" + requestContext.getMessageResource().get("queryRoomEmp");
		List<Map<String,Object>> roomList = service.getDb().findForJdbc(querySql, new Object[]{roomId});
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(roomList));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return null;
	}
	
	
	public String doAdjust(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		String dataList = requestContext.getRequest().getParameter("dataList");
		List<Map<String, Object>> dtlList = JSON.parseObject(dataList, new TypeReference<List<Map<String,Object>>>(){});
		String currentDate = DateTimeUtil.formatDate();
		String currentTime = DateTimeUtil.formatTime();
		for (int i=0,len=dtlList.size();i<len;i++) {
			service.getDb().executeSqlForJdbc("update Dorm_CheckIn set checkOutFlag=1,outDate=? where employeeId=? and isnull(checkOutFlag,0)=0", new Object[]{currentDate,dtlList.get(i).get("empId")});
			CheckIn bean = new CheckIn();
			bean.setRoomId(Integer.valueOf(dtlList.get(i).get("roomId").toString()));
			bean.setEmployeeId(Integer.valueOf(dtlList.get(i).get("empId").toString()));
			bean.setDescription("住宿调整");
			bean.setInDate(currentDate);
			bean.setInTime(currentTime);
			bean.setCheckOutFlag(0);
			service.getDb().saveObject(bean);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "操作成功");
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(resultMap));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	public void importExcel(CheckInForm form,RequestContext requestContext , IBaseServiceManger service) {
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
	
	public String doSubmit(RequestContext requestContext, IBaseServiceManger service) throws Exception {
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
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (ei.getLastDataRowNum() <= ei.getDataRowNum()-1) {
			returnMap.put("statememt", "没有要处理的的数据行!");
			returnMap.put("needClose", "0");
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
					List<Map<String, Object>> errList = service.getDb().findForJdbc("select * from im_CheckinCheck where error is not null");
					returnMap.put("statememt", retMsgMap.get("retMsg"));
					returnMap.put("errList", errList);
					returnMap.put("needClose", "0");
				} else {
					
					CallableParameter param = new CallableParameter(); 
					param.setSqlParameter(new SqlParameter("currentEmpId", java.sql.Types.INTEGER));
					param.setParameterValue(requestContext.getUser().getEmployeeId());
					paraList.clear();
					paraList.add(param);
					service.getDb().sp().call("ipexl_checkinCheck_last(?)", paraList);
					
					int rowCount = ei.getLastDataRowNum() - ei.getDataRowNum() + 2;
					returnMap.put("statememt", "导入成功,共" + rowCount + "行受影响!");
					returnMap.put("needClose", "1");		// 表示成功
				}
				
			}
			
		}
		
		try {
			requestContext.getResponse().getWriter().write(JSON.toJSONString(returnMap));
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
	public String exportExcel(RequestContext requestContext,IBaseServiceManger service) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object> ();
		List<Map<String, Object>> buildingList = service.getDb().findForJdbc("select id,name from Dorm_Building where kind=0");
		if (buildingList != null && buildingList.size() > 0) {
			String exportSql = "" + requestContext.getMessageResource().get("exportSql");
			for (Map tempMap : buildingList) {
				List<Map<String, Object>> dtlList = service.getDb().findForJdbc(exportSql, new Object[]{tempMap.get("id")});
				tempMap.put("dtlList", dtlList);
			}
		}
		
		dataMap.put("dataList", buildingList);
		
		String tempFileName = "";										// 临时文件名
		String windowDownloadName = "";									// 下载文件对话框显示文件名
		
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setServletContextForTemplateLoading(requestContext.getRequest().getSession().getServletContext(), "template");
		Template t = null;
		t = configuration.getTemplate("CheckInExcel.ftl", "utf-8");
		tempFileName = "CheckInExcel";
		windowDownloadName = "宿舍入住导出报表" + new Date().getTime() + ".xls";

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
	
	
	public void showTotal(RequestContext requestContext, IBaseServiceManger service) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = service.getDb().findForJdbc("select buildingName,buildingId,type,typeName,roomSum,emptyRoomSum,bigNumberSum,inSum,bigNumberSum-inSum freeSum,cast(cast(cast(inSum as decimal)*100/cast(bigNumberSum as decimal) as decimal(9,2)) as varchar(50)) + '%' inRate from vm_RoomTotal order by type,buildingName");
		Integer roomSumCommon = 0;
		Integer emptyRoomSumCommon = 0;
		Integer bigNumberSumCommon = 0;
		Integer inSumCommon = 0;
		Integer freeSumCommon = 0;
		
		// 高管或者夫妻房
		
		Integer roomSumOther = 0;
		Integer emptyRoomSumOther = 0;
		Integer bigNumberSumOther = 0;
		Integer inSumOther = 0;
		Integer freeSumOther = 0;
		for(Map<String, Object> tempMap : dataList) {
			if ("3".equals(tempMap.get("type")) || "4".equals(tempMap.get("type"))) {
				roomSumOther += Integer.valueOf(tempMap.get("roomSum").toString());
				emptyRoomSumOther += Integer.valueOf(tempMap.get("emptyRoomSum").toString());
				bigNumberSumOther += Integer.valueOf(tempMap.get("bigNumberSum").toString());
				inSumOther += Integer.valueOf(tempMap.get("inSum").toString());
				freeSumOther += Integer.valueOf(tempMap.get("freeSum").toString());
			} else {
				roomSumCommon += Integer.valueOf(tempMap.get("roomSum").toString());
				emptyRoomSumCommon += Integer.valueOf(tempMap.get("emptyRoomSum").toString());
				bigNumberSumCommon += Integer.valueOf(tempMap.get("bigNumberSum").toString());
				inSumCommon += Integer.valueOf(tempMap.get("inSum").toString());
				freeSumCommon += Integer.valueOf(tempMap.get("freeSum").toString());
			}
		}
		
		resultMap.put("rows", dataList);
		List<Map<String, Object>> footerList = new ArrayList<Map<String, Object>>();
		Map<String, Object> commonMap = new HashMap<String, Object>();
		Map<String, Object> otherMap = new HashMap<String, Object>();
		Map<String, Object> totalMap = new HashMap<String, Object>();
		commonMap.put("buildingName", "普工住宿合计");
		commonMap.put("roomSum", roomSumCommon);
		commonMap.put("emptyRoomSum", emptyRoomSumCommon);
		commonMap.put("bigNumberSum", bigNumberSumCommon);
		commonMap.put("inSum", inSumCommon);
		commonMap.put("freeSum", freeSumCommon);
		
		otherMap.put("buildingName", "高管/夫妻房合计");
		otherMap.put("roomSum", roomSumOther);
		otherMap.put("emptyRoomSum", emptyRoomSumOther);
		otherMap.put("bigNumberSum", bigNumberSumOther);
		otherMap.put("inSum", inSumOther);
		otherMap.put("freeSum", freeSumOther);
		
		totalMap.put("buildingName", "总计");
		totalMap.put("roomSum", roomSumOther + roomSumCommon);
		totalMap.put("emptyRoomSum", emptyRoomSumOther + emptyRoomSumCommon);
		totalMap.put("bigNumberSum", bigNumberSumOther + bigNumberSumCommon);
		totalMap.put("inSum", inSumOther + inSumCommon);
		totalMap.put("freeSum", freeSumOther + freeSumCommon);
		
		footerList.add(commonMap);
		footerList.add(otherMap);
		footerList.add(totalMap);
		resultMap.put("footer", footerList);
		requestContext.getRequest().setAttribute("dataJson", JSON.toJSONString(resultMap));
		
	}
	
	
}
