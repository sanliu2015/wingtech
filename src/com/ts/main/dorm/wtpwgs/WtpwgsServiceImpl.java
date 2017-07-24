package com.ts.main.dorm.wtpwgs;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.ts.main.dorm.wtpwgs.Wtpwgs;
import com.ts.main.dorm.wtpwgs.WtpwgsForm;
import com.ts.main.util.StringUtil;

@Service("wtpwgsService")
public class WtpwgsServiceImpl implements IAppService {

	public void add(WtpwgsForm form,RequestContext requestContext,IBaseServiceManger service) {
	
	}
	
	public OperatePromptBean save(WtpwgsForm form,RequestContext requestContext,IBaseServiceManger service)  {
		 Wtpwgs bean = form.getBean();
		 OperatePromptBean prompt = new OperatePromptBean();
		 service.getDb().saveObject(bean, requestContext);
		 prompt.setId(bean.getId().toString()); 
		 return prompt;
	}
	
	public void edit(WtpwgsForm form,RequestContext requestContext , IBaseServiceManger service) {
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		Wtpwgs bean = JSON.parseObject(JSON.toJSONString(beanMap), Wtpwgs.class);
		form.setBean(bean); 
	}
	
	public OperatePromptBean update(WtpwgsForm form,RequestContext requestContext,IBaseServiceManger service) {
		 Wtpwgs bean = service.getDb().getObject(Wtpwgs.class, form.getBean().getId(), requestContext);
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
	
	public OperatePromptBean delete(WtpwgsForm form,RequestContext requestContext , IBaseServiceManger service) { 
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
	
	
	public String getLastResult(RequestContext requestContext, IBaseServiceManger service) throws IOException {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String roomId = requestContext.getRequest().getParameter("roomId");
		StringBuilder sql = new StringBuilder(100);
		sql.append("select top 1 thisWaterNum,thisPowerNum,thisGasNum from Dorm_Wtpwgs where roomId=? and yearMonth<? order by yearMonth desc ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rsList = service.getDb().findForJdbc(sql.toString(), new Object[]{roomId, yearMonth});
		if (rsList != null && rsList.size() > 0) {
			resultMap.putAll(rsList.get(0));
		}
		
		
		requestContext.getResponse().getWriter().write(JSON.toJSONString(resultMap));
		requestContext.getResponse().getWriter().flush();
		requestContext.getResponse().getWriter().close();
		
		return null;
	}
}
