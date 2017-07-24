package com.ts.main.dorm.repairer;

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
import com.ts.main.dorm.repairer.Repairer;
import com.ts.main.dorm.repairer.RepairerForm;
import com.ts.main.util.StringUtil;

@Service("repairerService")
public class RepairerServiceImpl implements IAppService {

	public void add(RepairerForm form,RequestContext requestContext,IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean save(RepairerForm form,RequestContext requestContext,IBaseServiceManger service)  {
		for (int i = 0; i < form.getDtlList().size(); i++) {
			Repairer dtl = form.getDtlList().get(i); 
			service.getDb().saveObject(dtl, requestContext);
		}
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	
	public void edit(RepairerForm form,RequestContext requestContext,IBaseServiceManger service) {
		Repairer bean = service.getDb().getObject(Repairer.class, form.getId(), requestContext);
		form.setBean(bean);
	}
	
	public OperatePromptBean update(RepairerForm form,RequestContext requestContext,IBaseServiceManger service)  {
		Repairer bean = service.getDb().getObject(Repairer.class, form.getBean().getId(), requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		service.getDb().mergeObject(bean, requestContext);
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public OperatePromptBean delete(RepairerForm form,RequestContext requestContext , IBaseServiceManger service) { 
		Repairer bean = service.getDb().getObject(Repairer.class, form.getId(), requestContext);
		service.getDb().deleteObject(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		service.getDb().mergeObject(bean, requestContext);
		return prompt;
	}
}
