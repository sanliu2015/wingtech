package com.ts.main.hr.interim;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("interimService")
public class InterimServiceImpl implements IAppService{
	public void add(RequestContext requestContext , IBaseServiceManger service,InterimForm form){		 
	}
	
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,InterimForm form) throws Throwable{
		Interim  bean = form.getBean(); 
		service.getDbService().saveObjectAndId(bean, requestContext); 
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setBillId(bean.getId()); 
		return prompt;
	}
	public void edit(RequestContext requestContext , IBaseServiceManger service,InterimForm form){
		Interim bean = service.getDbService().getObject(Interim.class, form.getId(), requestContext);
		form.setBean(bean);
	}	
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,InterimForm form){
		Interim oldAsk = service.getDbService().getObject(Interim.class, form.getBean().getId(), requestContext);
		Interim newAsk = form.getBean();
		BeanUtils.copyProperties(newAsk, oldAsk);
		service.getDbService().updateObject(oldAsk, requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(form.getBean().getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}	
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,InterimForm form){
		edit(requestContext,service,form);
		Interim bean = form.getBean(); 
		service.getDbService().deleteObject(bean, requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setBillId(bean.getId());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
}
