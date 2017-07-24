package com.ts.main.dorm.rewardpunish;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.core.util.DateTimeUtil;
import com.ts.main.dorm.punish.Punish;
import com.ts.main.dorm.reward.Reward;
import com.ts.main.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("rewardPunishService")
public class RewardPunishServiceImpl implements IAppService {
	
	public void add(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		RewardPunish bean = form.getBean();
		service.getDb().saveObject(bean, requestContext);
		sumbitEntryList(requestContext, service, form);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		if (form.getDtlList() == null) return;
		RewardPunish bean = form.getBean();
		for (int i = 0; i < form.getDtlList().size(); i++) {
			RewardPunishDtl dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				RewardPunishDtl orignBean = service.getDb().getObject(RewardPunishDtl.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from DORM_RewardPunishDtl where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setHdrId(bean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		RewardPunish bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), RewardPunish.class);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		RewardPunish bean = form.getBean(); 
		RewardPunish orignBean = service.getDb().getObject(RewardPunish.class, bean.getId(), requestContext);
		if ("1".equals(orignBean.getStatus())) {
			opb.setError("已经审核，不能修改!"); 
		} else {
			BeanUtils.copyNoNullProperties(bean, orignBean);
			service.getDb().updateObject(orignBean, requestContext);
			sumbitEntryList(requestContext, service, form);
			opb.setBillId(bean.getId());
		}
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
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
					CallableParameter param2 = new CallableParameter();
					param2.setParameterValue("");
					param2.setSqlParameter(new SqlOutParameter("retMsg", Types.VARCHAR)); 
					paraList1.add(param2);
					Map<String,Object> spValue1 = service.getDb().sp().call(deleteSp, paraList1);
				}
			}
			
		}
		return opb;
	}
	
	public void audit(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		this.edit(requestContext, service, form);
	}
	
	
	
	public OperatePromptBean doAudit(RequestContext requestContext, IBaseServiceManger service, RewardPunishForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		RewardPunish bean = form.getBean(); 
		RewardPunish orignBean = service.getDb().getObject(RewardPunish.class, bean.getId(), requestContext);
		if ("1".equals(orignBean.getStatus())) {
			opb.setError("已经审核过了!"); 
		} else {
			// 保存惩罚记录
			Punish punish = new Punish();
			punish.setRpId(orignBean.getId());
			punish.setEmployeeId(orignBean.getEmployeeId());
			punish.setOccurDate(DateTimeUtil.formatDate());
			punish.setAmount(orignBean.getAmount());
			punish.setReason(orignBean.getReason());
			punish.setExecutivePunish(orignBean.getExecutivePunish());
			punish.setDescription((orignBean.getDescription() == null ? "" : orignBean.getDescription()) + "【来源" + orignBean.getNumber() + "】");
			service.getDb().saveObject(punish);
			// 保存奖励明细
			StringBuilder hql = new StringBuilder(100);
			hql.append("select a from RewardPunishDtl a where a.hdrId=" + orignBean.getId());
			List<RewardPunishDtl> dtlList = service.getDb().queryHqlForList(hql.toString());
			if (dtlList != null && dtlList.size() > 0) {
				for (RewardPunishDtl dtl : dtlList) {
					Reward reward = new Reward();
					reward.setRpDtlId(dtl.getId());
					reward.setEmployeeId(dtl.getEmployeeId());
					reward.setOccurDate(DateTimeUtil.formatDate());
					reward.setAmount(dtl.getAmount());
					reward.setReason(dtl.getReason());
					reward.setDescription((dtl.getDescription() == null ? "" : dtl.getDescription()) + "【来源" + orignBean.getNumber() + "】");
					service.getDb().saveObject(reward);
				}
			}
			orignBean.setStatus("1");
			service.getDb().mergeObject(orignBean, requestContext);
			opb.setBillId(bean.getId());
		}
		return opb;
	}
	
	public void exportExcelInit(RequestContext requestContext, IBaseServiceManger service) {
		
	}
	
	public String exportExcel(RequestContext requestContext,IBaseServiceManger service) throws Exception {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		Map<String, Object> dataMap = new HashMap<String, Object> ();	
		String dtlSql = "" + requestContext.getMessageResource().get("dtlSql");
		String hdrSql = "" + requestContext.getMessageResource().get("hdrSql");
		List<Map<String, Object>> hdrList = service.getDb().findForJdbc(hdrSql, new Object[]{yearMonth+"%"});
		dataMap.put("totalRowHdr", hdrList.size());
		dataMap.put("dataListHdr", hdrList);
		
		List<Map<String, Object>> dtlList = service.getDb().findForJdbc(dtlSql, new Object[]{yearMonth+"%"});
		dataMap.put("totalRowDtl", dtlList.size());
		dataMap.put("dataListDtl", dtlList);
		
		String tempFileName = "";										// 临时文件名
		String windowDownloadName = "";									// 下载文件对话框显示文件名
		
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setServletContextForTemplateLoading(requestContext.getRequest().getSession().getServletContext(), "template");
		Template t = null;
		t = configuration.getTemplate("RewardPunishExcel.ftl", "utf-8");
		tempFileName = "RewardPunishExcel";
		windowDownloadName = yearMonth + "违规违纪登记导出" + new Date().getTime() + ".xls";

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
