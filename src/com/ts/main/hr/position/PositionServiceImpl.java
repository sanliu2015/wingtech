package com.ts.main.hr.position;
   
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

@Service("positionService")
public class PositionServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass());
	 
	  
	public void addPosition(RequestContext requestContext , IBaseServiceManger service,PositionForm form){
		 Integer id=form.getId();
		 if(StringUtils.isNoValue(id)==false){
			 Position bean=service.getDb().getObject(Position.class, id,requestContext); 
			 if(bean!=null){
				 form.getBean().setParentId(id);
				 form.getBean().setParentName(bean.getName());
			 }
		 }
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,PositionForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public String saveForwad(RequestContext requestContext , IBaseServiceManger service,PositionForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return "SaveForwad";
	}
	public void editPosition(RequestContext requestContext , IBaseServiceManger service,PositionForm form){
		Position bean=service.getDb().getObject(Position.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,PositionForm form){
		 Position bean=service.getDb().getObject(Position.class, form.getBean().getId(),requestContext); 
		 //BeanUtils.copyNoNullProperties(form.getBean(), bean,"parentId");
		 BeanUtils.copyNoNullProperties(form.getBean(), "parentId");
		 service.getDb().updateObject(bean,requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	private void queryChildNodes(Position bean,  IBaseServiceManger service,RequestContext requestContext ){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from Position as a where a.parentId="+bean.getId());
		List<Position> list=service.getDb().queryHqlForList(sql.toString(),requestContext);
		for(int i=0;i<list.size();i++){
			Position childNode=list.get(i); 
			queryChildNodes(childNode, service,requestContext);
		}
		service.getDb().deleteList(list,requestContext);
	}
	public OperatePromptBean deletePosition(RequestContext requestContext , IBaseServiceManger service,PositionForm form){ 
		Position bean=service.getDb().getObject(Position.class, form.getId(),requestContext); 
		if(!"1".equals(form.getNeedValidate())){
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(id) from HR_Position where parentId="+form.getId());
			int rec=service.getDb().queryForInt(sql.toString());
			if(rec>0){
				requestContext.setConfirm(new StringBuffer("存在下级职位，确认要删除吗？"));
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
