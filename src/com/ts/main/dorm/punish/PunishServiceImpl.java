package com.ts.main.dorm.punish;

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
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.util.StringUtil;

@Service("punishService")
public class PunishServiceImpl implements IAppService {

	public void add(PunishForm form,RequestContext requestContext,IBaseServiceManger service) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select name id, name text from TS_SysCode where codeKind=301");
		List<Map<String, Object>> reasonList = service.getDb().queryForList(sql.toString(), requestContext);
		requestContext.getRequest().setAttribute("reasonList", JSON.toJSONString(reasonList));
	}
	
	public OperatePromptBean save(PunishForm form,RequestContext requestContext,IBaseServiceManger service)  {
		 Punish bean = form.getBean();
		 OperatePromptBean prompt = new OperatePromptBean();
		 service.getDb().saveObject(bean, requestContext);
		 prompt.setId(bean.getId().toString()); 
		 return prompt;
	}
	
	public void edit(PunishForm form,RequestContext requestContext , IBaseServiceManger service) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select name id, name text from TS_SysCode where codeKind=301");
		List<Map<String, Object>> reasonList = service.getDb().queryForList(sql.toString(), requestContext);
		requestContext.getRequest().setAttribute("reasonList", JSON.toJSONString(reasonList));
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		Punish bean = JSON.parseObject(JSON.toJSONString(beanMap), Punish.class);
		form.setBean(bean); 
	}
	
	public OperatePromptBean update(PunishForm form,RequestContext requestContext,IBaseServiceManger service) {
		 Punish bean = service.getDb().getObject(Punish.class, form.getBean().getId(), requestContext);
		 OperatePromptBean prompt = new OperatePromptBean();
		 if (bean.getLockFlag() != null && bean.getLockFlag().intValue() == 1) {
			 prompt.setError("已经锁定，不能修改!"); 
		 } else {
			 BeanUtils.copyNoNullProperties(form.getBean(), bean);
			 service.getDb().mergeObject(bean, requestContext);
			 prompt.setId(bean.getId().toString()); 
		 }
		
		 return prompt;
	}
	
	public OperatePromptBean delete(PunishForm form,RequestContext requestContext , IBaseServiceManger service) { 
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
}
