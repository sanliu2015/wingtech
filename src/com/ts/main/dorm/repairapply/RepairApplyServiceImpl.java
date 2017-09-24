package com.ts.main.dorm.repairapply;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.util.DateTimeUtil;
import com.ts.main.dorm.SendLog;
import com.ts.main.dorm.building.BuildingForm;
import com.ts.main.dorm.damage.Damage;
import com.ts.main.dorm.repairer.Repairer;
import com.ts.main.dorm.reward.Reward;
import com.ts.main.util.SendSmsUtil;

@Service("repairApplyService")
public class RepairApplyServiceImpl implements IAppService {
	
	public void add(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		RepairApply bean = form.getBean();
		service.getDb().saveObject(bean, requestContext);
		sumbitEntryList(requestContext, service, form);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		if (form.getDtlList() == null) return;
		RepairApply bean = form.getBean();
		for (int i = 0; i < form.getDtlList().size(); i++) {
			RepairApplyDtl dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				RepairApplyDtl orignBean = service.getDb().getObject(RepairApplyDtl.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from DORM_RepairApplyDtl where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setHdrId(bean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		RepairApply bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), RepairApply.class);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		RepairApply bean = form.getBean(); 
		RepairApply orignBean = service.getDb().getObject(RepairApply.class, bean.getId(), requestContext);
		BeanUtils.copyNoNullProperties(bean, orignBean);
		service.getDb().updateObject(orignBean, requestContext);
		sumbitEntryList(requestContext, service, form);
		opb.setBillId(bean.getId());
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		String[] selectParm = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectParm, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		for (Map tempMap : selectParamList) {
			service.getDb().executeSqlForJdbc("delete from Dorm_RepairApply where id=?", new Object[]{tempMap.get("id")});
			service.getDb().executeSqlForJdbc("delete from Dorm_RepairApplyDtl where hdrId=?", new Object[]{tempMap.get("id")});
		}
		OperatePromptBean opb = new OperatePromptBean();
		return opb;
	}
	
	public void audit(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		this.edit(requestContext, service, form);
	}
	
	public OperatePromptBean doAudit(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		RepairApply orignBean = service.getDb().getObject(RepairApply.class, form.getBean().getId(), requestContext);
		// 发送短信给修理人员
		Repairer repairer = (Repairer) service.getDb().getObject(Repairer.class, orignBean.getRepairerId(), requestContext);
		if (repairer.getPhone() != null && !"".equals(repairer.getPhone())) {
			StringBuilder content = new StringBuilder(500);
			content.append("收到一条报修申请：【单号】").append(orignBean.getNumber())
					.append("，【维修日期】").append(orignBean.getRepairDate()).append("，【维修内容】").append(orignBean.getRepairContent());
			
			SendLog log = new SendLog();
			try {
				String sb = SendSmsUtil.sendSms(repairer.getPhone(),content.toString());
				Document document;
				try {
	    			document = DocumentHelper.parseText(sb.toString());
	    			Element root = document.getRootElement();
	    		    Element node = root.element("info").element("state");  
	    		    String state = node.getText();
	    		    if ("0".equals(state)) {
	    		    	orignBean.setSendToRepFlag("1");
	    		    }
	    		    log.setSendStatus(state);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			log.setReason("解析xml出现异常!");
	    		}
			} catch (Exception e) {
				e.printStackTrace();
				log.setReason("调用接口出现异常!");
			}
			service.getDb().saveObject(log);
		}
		
		orignBean.setAuditStatus("1");
		service.getDb().updateObject(orignBean, requestContext);
		sumbitEntryList(requestContext, service, form);
		opb.setBillId(form.getBean().getId());
		return opb;
	}
	

	public void endWork(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		RepairApply bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), RepairApply.class);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
	}
	
	// 完工
	public OperatePromptBean doEndWork(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		RepairApply orignBean = service.getDb().getObject(RepairApply.class, form.getBean().getId(), requestContext);
		orignBean.setAutoScoreDate(form.getBean().getAutoScoreDate());
		orignBean.setNotifyFlag(form.getBean().getNotifyFlag());
		orignBean.setEmpPhone(form.getBean().getEmpPhone());
		orignBean.setEndWorkDate(form.getBean().getEndWorkDate());
		orignBean.setStatus("1");
		if (orignBean.getNotifyFlag().intValue() == 1) {
			StringBuilder content = new StringBuilder(500);
			content.append("您的报修申请已完工，【单号】").append(orignBean.getNumber())
					.append("，【维修内容】").append(orignBean.getRepairContent())
					.append("，若要对此次维修做出评价，请于").append(orignBean.getAutoScoreDate()).append("前（含当日）前往物业管理中心处理，谢谢您的配合！");
			SendLog log = new SendLog();
			try {
				String sb = SendSmsUtil.sendSms(orignBean.getEmpPhone(),content.toString());
				Document document;
				try {
	    			document = DocumentHelper.parseText(sb.toString());
	    			Element root = document.getRootElement();
	    		    Element node = root.element("info").element("state");  
	    		    String state = node.getText();
	    		    if ("0".equals(state)) {
	    		    	orignBean.setSendToEmpFlag("1");
	    		    }
	    		    log.setSendStatus(state);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			log.setReason("解析xml出现异常!");
	    		}
			} catch (Exception e) {
				e.printStackTrace();
				log.setReason("调用接口出现异常!");
			}
		}
		service.getDb().updateObject(orignBean, requestContext);
		if (form.getDamageList() != null && form.getDamageList().size() > 0) {
			for (Damage dtl : form.getDamageList()) {
				dtl.setRepairApplyId(orignBean.getId());
				dtl.setOccurDate(DateTimeUtil.formatDate());
				dtl.setDescription("报修扣款费用，报修单号：" + orignBean.getNumber());
				service.getDb().saveObject(dtl);
			}
		}
		
		if (form.getBean().getRewardFee().doubleValue() > 0) {
			Reward reward = new Reward();
			reward.setOccurDate(DateUtil.formatDate(new Date()));
			reward.setAmount(form.getBean().getRewardFee());
			StringBuilder sql = new StringBuilder(100);
			sql.append("select a.id from hr_employee a inner join Dorm_Repairer b on a.idCard=b.idCard where a.status=1 and b.id=?");
			List<Map<String, Object>> rsList = service.getDb().findForJdbc(sql.toString(), orignBean.getRepairerId());
			reward.setEmployeeId(Integer.valueOf(rsList.get(0).get("id").toString()));
			reward.setDescription("报修奖励费用，报修单号：" + orignBean.getNumber());
			service.getDb().saveObject(reward);
		}
		
		return opb;
	}
	
	public void score(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		RepairApply bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), RepairApply.class);
		form.setBean(bean);
	}
	
	public OperatePromptBean doScore(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		RepairApply orignBean = service.getDb().getObject(RepairApply.class, form.getBean().getId(), requestContext);
		orignBean.setScoreDate(DateTimeUtil.formatDate());
		orignBean.setScoreResult(form.getBean().getScoreResult());
		orignBean.setStatus("2");
		service.getDb().updateObject(orignBean, requestContext);
		return opb;
	}
	
	public void look(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		RepairApply bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), RepairApply.class);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
		
		String queryDamageSql = "" + requestContext.getMessageResource().get("queryDamageSql");
		List<Map<String,Object>> damageList = service.getDb().findForJdbc(queryDamageSql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("damageList", JSON.toJSONString(damageList));
	}
	
	public void print(RequestContext requestContext, IBaseServiceManger service, RepairApplyForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		RepairApply bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), RepairApply.class);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", dtlList);
	}
}
