package com.ts.main.excel.importplan;

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
import com.ts.main.excel.importplan.ImportPlan;
import com.ts.main.excel.importplan.ImportPlanDtl;
import com.ts.main.excel.importplan.ImportPlanForm;

@Service("importPlanService")
public class ImportPlanServiceImpl implements IAppService {

	public void add(RequestContext requestContext, IBaseServiceManger service, ImportPlanForm form) {
	
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, ImportPlanForm form) {
		ImportPlan bean = form.getBean();
		service.getDb().saveObject(bean, requestContext);
		sumbitEntryList(requestContext, service, form);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, ImportPlanForm form) {
		if (form.getDtlList() == null) return;
		ImportPlan bean = form.getBean();
		for (int i = 0; i < form.getDtlList().size(); i++) {
			ImportPlanDtl dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				ImportPlanDtl orignBean = service.getDb().getObject(ImportPlanDtl.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from Excel_ImportPlanDtl where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setHdrId(bean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, ImportPlanForm form) {
		ImportPlan bean = service.getDb().getObject(ImportPlan.class, form.getId(), requestContext);
		form.setBean(bean);
		List<ImportPlanDtl> dtlList = service.getDb().queryHqlForList("select a from ImportPlanDtl a where a.hdrId=" + form.getId());
		form.setDtlList(dtlList);
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, ImportPlanForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		ImportPlan bean = form.getBean(); 
		ImportPlan orignBean = service.getDb().getObject(ImportPlan.class, bean.getId(), requestContext);
		BeanUtils.copyNoNullProperties(bean, orignBean);
		service.getDb().updateObject(orignBean, requestContext);
		sumbitEntryList(requestContext, service, form);
		opb.setBillId(bean.getId());
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, ImportPlanForm form) {
		String[] selectParm = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectParm, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		for (Map tempMap : selectParamList) {
			service.getDb().executeSqlForJdbc("delete from Excel_ImportPlan where id=?", new Object[]{tempMap.get("id")});
			service.getDb().executeSqlForJdbc("delete from Excel_ImportPlanDtl where hdrId=?", new Object[]{tempMap.get("id")});
		}
		OperatePromptBean opb = new OperatePromptBean();
		opb.setBillId(1);
		return opb;
	}
}
