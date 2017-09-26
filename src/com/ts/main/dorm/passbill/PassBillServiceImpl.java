package com.ts.main.dorm.passbill;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.main.dorm.damage.Damage;

@Service("passBillService")
public class PassBillServiceImpl implements IAppService {
	
	public void add(RequestContext requestContext, IBaseServiceManger service, PassBillForm form) {
		
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, PassBillForm form) {
		PassBill bean = form.getBean();
		service.getDb().saveObject(bean, requestContext);
		// 自动退宿
		if ("离职".equals(bean.getReason()) || "外住".equals(bean.getReason()) || "自离".equals(bean.getReason())) {
			service.getDb().executeSqlForJdbc("update DORM_CheckIn set keyStatus=?, remoterKeep=? ,outDate=?,outTime=?,outReason=?,checkOutFlag=1 where employeeId=? and roomId=? and isnull(checkOutFlag,0)=0", new Object[]{bean.getKeyStatus(),bean.getTelStatus(),bean.getCreateDate(),bean.getCreateTime(),bean.getReason(),bean.getEmpId(),bean.getRoomId()});
			Damage damage = form.getDamage();
			if (damage.getAmount() != null && damage.getAmount().intValue() > 0) {
				damage.setEmployeeId(bean.getEmpId());
				damage.setOccurDate(bean.getPassDate());
				service.getDb().saveObject(damage, requestContext);
			}
		}
		sumbitEntryList(requestContext, service, form);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, PassBillForm form) {
		if (form.getDtlList() == null) return;
		PassBill bean = form.getBean();
		for (int i = 0; i < form.getDtlList().size(); i++) {
			PassBillDtl dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				PassBillDtl orignBean = service.getDb().getObject(PassBillDtl.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from DORM_PassBillDtl where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setHdrId(bean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, PassBillForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		PassBill bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), PassBill.class);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
		requestContext.getRequest().setAttribute("dtlListPrint", dtlList);
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, PassBillForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		PassBill bean = form.getBean(); 
		PassBill orignBean = service.getDb().getObject(PassBill.class, bean.getId(), requestContext);
		BeanUtils.copyNoNullProperties(bean, orignBean);
		service.getDb().updateObject(orignBean, requestContext);
		sumbitEntryList(requestContext, service, form);
		opb.setBillId(bean.getId());
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, PassBillForm form) {
		String[] selectParm = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectParm, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		for (Map tempMap : selectParamList) {
			service.getDb().executeSqlForJdbc("delete from Dorm_PassBill where id=?", new Object[]{tempMap.get("id")});
			service.getDb().executeSqlForJdbc("delete from Dorm_PassBillDtl where hdrId=?", new Object[]{tempMap.get("id")});
		}
		OperatePromptBean opb = new OperatePromptBean();
		return opb;
	}
	
	

}
