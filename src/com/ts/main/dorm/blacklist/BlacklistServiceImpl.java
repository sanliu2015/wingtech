package com.ts.main.dorm.blacklist;

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

@Service("blacklistService")
public class BlacklistServiceImpl implements IAppService {

	public void add(BlacklistForm form,RequestContext requestContext,IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean save(BlacklistForm form,RequestContext requestContext,IBaseServiceManger service)  {
		for (int i = 0; i < form.getDtlList().size(); i++) {
			Blacklist dtl = form.getDtlList().get(i); 
			service.getDb().saveObject(dtl, requestContext);
		}
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	
	public void edit(BlacklistForm form,RequestContext requestContext,IBaseServiceManger service) {
		Blacklist bean = service.getDb().getObject(Blacklist.class, form.getId(), requestContext);
		form.setBean(bean);
	}
	
	public OperatePromptBean update(BlacklistForm form,RequestContext requestContext,IBaseServiceManger service)  {
		Blacklist bean = service.getDb().getObject(Blacklist.class, form.getBean().getId(), requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		service.getDb().mergeObject(bean, requestContext);
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public OperatePromptBean delete(BlacklistForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		String ids = "(" + StringUtil.listMapToStrByKey(selectParamList, "id") + ")";
		service.getDb().executeUpdate("delete from Dorm_blacklist where id in " + ids);
		return opb;	
	}
}
