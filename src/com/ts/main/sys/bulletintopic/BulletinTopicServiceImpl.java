package com.ts.main.sys.bulletintopic;
    
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;    
import com.ts.core.common.bean.OperatePromptBean; 
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;    

@Service("bulletinTopicService")
public class BulletinTopicServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass()); 
	public void addBulletinTopic(RequestContext requestContext , IBaseServiceManger service,BulletinTopicForm form){
		 
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,BulletinTopicForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public void editBulletinTopic(RequestContext requestContext , IBaseServiceManger service,BulletinTopicForm form){
		BulletinTopic bean=service.getDb().getObject(BulletinTopic.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,BulletinTopicForm form){
		 BulletinTopic bean=service.getDb().getObject(BulletinTopic.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().updateObject(bean,requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	 
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,BulletinTopicForm form){ 
		BulletinTopic bean=service.getDb().getObject(BulletinTopic.class, form.getId(),requestContext); 
		if(!"1".equals(form.getNeedValidate())){
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(id) from Sys_Bulletin where bulletinTopicId="+form.getId());
			int rec=service.getDb().queryForInt(sql.toString());
			if(rec>0){
				requestContext.setConfirm(new StringBuffer("信息发布内容引用了该栏目，确认要删除吗？"));
				OperatePromptBean prompt=new OperatePromptBean();
				prompt.setId(bean.getId().toString());
				prompt.setStatememt(prompt.hint_failure); 
				return prompt;
			}
		}
		 
		service.getDbService().deleteObject(bean,requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
	 
}
