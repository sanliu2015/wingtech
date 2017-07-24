package com.ts.main.dorm.coupleset;

import java.sql.Types;
import java.util.ArrayList;
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
import com.ts.main.util.StringUtil;

@Service("coupleSetService")
public class CoupleSetServiceImpl implements IAppService {

	public void add(CoupleSetForm form,RequestContext requestContext,IBaseServiceManger service) {
	
	}
	
	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, CoupleSetForm form) {
		if (form.getDtlList() == null) return;
		for (int i = 0; i < form.getDtlList().size(); i++) {
			CoupleSet dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				CoupleSet orignBean = service.getDb().getObject(CoupleSet.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				dtl.setStatus("0");
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("update a set a.payPersonId=null from HR_Employee a inner join Dorm_CoupleSet b on a.id=b.orgEmpId where b.id=?", new Object[]{dtl.getId()});
				service.getDb().executeSqlForJdbc("delete from Excel_CoupleSet where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setStatus("0");
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	public OperatePromptBean save(CoupleSetForm form,RequestContext requestContext,IBaseServiceManger service)  {
		 CoupleSet bean = form.getBean();
		 OperatePromptBean prompt = new OperatePromptBean();
		 sumbitEntryList(requestContext, service, form);
		 return prompt;
	}
	
	public void edit(CoupleSetForm form,RequestContext requestContext , IBaseServiceManger service) {
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql + " (" + org.apache.commons.lang3.StringUtils.join(idList,",") + ")");
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
	}
	
	public OperatePromptBean update(CoupleSetForm form,RequestContext requestContext,IBaseServiceManger service) {
		 OperatePromptBean prompt = new OperatePromptBean();
		 sumbitEntryList(requestContext, service, form);
		 return prompt;
	}
	
	public OperatePromptBean delete(CoupleSetForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			service.getDb().executeSqlForJdbc("update a set a.payPersonId=null from HR_Employee a inner join Dorm_CoupleSet b on a.id=b.orgEmpId where b.id in(" + org.apache.commons.lang3.StringUtils.join(idList,",") + ")");
			service.getDb().executeSqlForJdbc("delete from Dorm_CoupleSet where id in(" + org.apache.commons.lang3.StringUtils.join(idList,",") + ")");
		}
		return opb;	
	}
	
	public OperatePromptBean doEffective(CoupleSetForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			String doEffectiveSp = "" + requestContext.getMessageResource().get("doEffectiveSp");
			// 删除
			if (!StringUtil.isNoValue(doEffectiveSp)) {
				for (int i=0,len=idList.size(); i<len; i++) {
					List<CallableParameter> paraList1 = new ArrayList<CallableParameter>();
					CallableParameter param1 = new CallableParameter(); 
					param1.setParameterValue(idList.get(i));
					param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
					paraList1.add(param1);
					CallableParameter param2 = new CallableParameter(); 
					param2.setParameterValue(1);
					param2.setSqlParameter(new SqlParameter("isEffect", java.sql.Types.INTEGER));
					paraList1.add(param2);
					CallableParameter param3 = new CallableParameter();
					param3.setParameterValue("");
					param3.setSqlParameter(new SqlOutParameter("retMsg", Types.VARCHAR)); 
					paraList1.add(param3);
					service.getDb().sp().call(doEffectiveSp, paraList1);
				}
			}
			
		}
		return opb;	
	}
	
	
	public OperatePromptBean doUnEffective(CoupleSetForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			String doEffectiveSp = "" + requestContext.getMessageResource().get("doEffectiveSp");
			// 删除
			if (!StringUtil.isNoValue(doEffectiveSp)) {
				for (int i=0,len=idList.size(); i<len; i++) {
					List<CallableParameter> paraList1 = new ArrayList<CallableParameter>();
					CallableParameter param1 = new CallableParameter(); 
					param1.setParameterValue(idList.get(i));
					param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
					paraList1.add(param1);
					CallableParameter param2 = new CallableParameter(); 
					param2.setParameterValue(0);
					param2.setSqlParameter(new SqlParameter("isEffect", java.sql.Types.INTEGER));
					paraList1.add(param2);
					CallableParameter param3 = new CallableParameter();
					param3.setParameterValue("");
					param3.setSqlParameter(new SqlOutParameter("retMsg", Types.VARCHAR)); 
					paraList1.add(param3);
					service.getDb().sp().call(doEffectiveSp, paraList1);
				}
			}
			
		}
		return opb;	
	}
	

}
