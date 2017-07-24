package com.ts.main.hr.outsiders;
import org.springframework.beans.BeanUtils;  
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext; 

@Service("outsidersService")
public class OutsidersServiceImpl implements IAppService{ 
	public void add(RequestContext requestContext , IBaseServiceManger service,OutsidersForm form){
		  
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,OutsidersForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext);  
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setBillId(form.getBean().getId() );
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public void edit(RequestContext requestContext , IBaseServiceManger service,OutsidersForm form){
		Outsiders bean=service.getDb().getObject(Outsiders.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,OutsidersForm form){
		Outsiders bean=service.getDb().getObject(Outsiders.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().updateObject(bean,requestContext);  
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success);  
		 return prompt;
	}
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,OutsidersForm form){ 
		Outsiders bean=service.getDb().getObject(Outsiders.class, form.getId(),requestContext);  
		service.getDbService().deleteObject(bean,requestContext); 
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
//	private void queryChildNodes(Outsiders bean,  IBaseServiceManger service,RequestContext requestContext ){
//		StringBuffer sql=new StringBuffer();
//		sql.append(" select a from Outsiders as a where a.parentId="+bean.getId());
//		List<Outsiders> list=service.getDb().queryHqlForList(sql.toString(),requestContext);
//		for(int i=0;i<list.size();i++){
//			Room childNode=list.get(i); 
//			queryChildNodes(childNode, service,requestContext);
//		}
//		service.getDb().deleteList(list,requestContext);
//	} 
}
