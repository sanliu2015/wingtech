package com.ts.main.dorm.roomset;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("roomSetService")
public class RoomSetServiceImpl implements IAppService {

	public void add(RoomSetForm form,RequestContext requestContext,IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean save(RoomSetForm form,RequestContext requestContext,IBaseServiceManger service)  {
		RoomSet bean = form.getBean();
		OperatePromptBean prompt = new OperatePromptBean();
		service.getDb().saveObject(bean, requestContext);
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public void edit(RoomSetForm form,RequestContext requestContext,IBaseServiceManger service) {
		RoomSet bean = service.getDb().getObject(RoomSet.class, form.getId(), requestContext);
		form.setBean(bean);
	}
	
	public OperatePromptBean update(RoomSetForm form,RequestContext requestContext,IBaseServiceManger service)  {
		RoomSet bean = service.getDb().getObject(RoomSet.class, form.getBean().getId(), requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		service.getDb().mergeObject(bean, requestContext);
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public OperatePromptBean delete(RoomSetForm form,RequestContext requestContext , IBaseServiceManger service) { 
		RoomSet bean = service.getDb().getObject(RoomSet.class, form.getBean().getId(), requestContext);
		service.getDb().deleteObject(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
}
