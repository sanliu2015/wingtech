package com.ts.main.hr.company;
    

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;   
import com.ts.core.common.bean.OperatePromptBean; 
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext; 

@Service("companyService")
public class CompanyServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass());
	 
	  
	public void addCompany(RequestContext requestContext , IBaseServiceManger service,CompanyForm form){
		 
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,CompanyForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public void editCompany(RequestContext requestContext , IBaseServiceManger service,CompanyForm form){
		Company bean=service.getDb().getObject(Company.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,CompanyForm form){
		 Company bean=service.getDb().getObject(Company.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().updateObject(bean,requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public OperatePromptBean deleteCompany(RequestContext requestContext , IBaseServiceManger service,CompanyForm form){ 
		Company bean=service.getDb().getObject(Company.class, form.getId(),requestContext);  
		service.getDbService().deleteObject(bean,requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
}
