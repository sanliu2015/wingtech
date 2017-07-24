package com.ts.main.hr.department;
   
import java.util.List;  

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;    
import com.ts.core.common.bean.OperatePromptBean; 
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;   
import com.ts.core.util.StringUtils;

@Service("departmentService")
public class DepartmentServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass());
	 
	  
	public void addDepartment(RequestContext requestContext , IBaseServiceManger service,DepartmentForm form){
		 Integer deptId=form.getId();
		 if(StringUtils.isNoValue(deptId)==false){
			 Department bean=service.getDb().getObject(Department.class, deptId,requestContext); 
			 form.getBean().setParentId(deptId);
			 form.getBean().setParentName(bean.getName());
			 form.getBean().setCompany(bean.getCompany());
		 }
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,DepartmentForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public void editDepartment(RequestContext requestContext , IBaseServiceManger service,DepartmentForm form){
		Department bean=service.getDb().getObject(Department.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,DepartmentForm form){
		Department bean=service.getDb().getObject(Department.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().updateObject(bean,requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	private void queryChildNodes(Department bean,  IBaseServiceManger service,RequestContext requestContext ){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from Department as a where a.parentId="+bean.getId());
		List<Department> list=service.getDb().queryHqlForList(sql.toString(),requestContext);
		for(int i=0;i<list.size();i++){
			Department childNode=list.get(i); 
			queryChildNodes(childNode, service,requestContext);
		}
		service.getDb().deleteList(list,requestContext);
	}
	public OperatePromptBean deleteDepartment(RequestContext requestContext , IBaseServiceManger service,DepartmentForm form){ 
		Department bean=service.getDb().getObject(Department.class, form.getId(),requestContext); 
		if(!"1".equals(form.getNeedValidate())){
			StringBuffer sql = new StringBuffer();
			sql.append(" select id from HR_Department where parentId="+form.getId());
			int rec=service.getDb().queryForInt(sql.toString());
			if(rec>0){
				requestContext.setConfirm(new StringBuffer("存在下级部门，确认要删除吗？"));
				OperatePromptBean prompt=new OperatePromptBean();
				prompt.setId(bean.getId().toString());
				prompt.setStatememt(prompt.hint_failure); 
				return prompt;
			}
		}
		queryChildNodes(bean,service,requestContext);
		service.getDbService().deleteObject(bean,requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
	 
}
