package com.ts.main.sys.subscribe;
     

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;     

import com.ts.core.common.bean.OperatePromptBean; 
import com.ts.core.common.constant.Globals;
import com.ts.core.common.constant.Globals.recordOperateStatus;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;     
import com.ts.main.sys.bulletin.BulletinForm;

@Service("subscribeService")
public class SubscribeServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass()); 
	public void addSubscribe(RequestContext requestContext , IBaseServiceManger service,SubscribeForm form){
		 
	}
	 
	private void submitEmployeeReceiverList(RequestContext requestContext,IBaseServiceManger service,SubscribeForm form){
		if(form.getEmployeeReceiverList()==null || form.getEmployeeReceiverList().size()<=0) return;
		SubscribeReceiver bean=form.getBean();
		for(int i=0;i<form.getEmployeeReceiverList().size();i++){
			SubscribeReceiver dtl=form.getEmployeeReceiverList().get(i); 
			dtl.setTopicKind(bean.getTopicKind());
			dtl.setBulletinTopicId(bean.getBulletinTopicId());
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
				   SubscribeReceiver orignBean=service.getDb().getObject(SubscribeReceiver.class, dtl.getId(), requestContext);
					BeanUtils.copyNoNullProperties(dtl, orignBean);
					dtl=orignBean;
					service.getDb().updateObject(dtl, requestContext);
					break; 
			    case delete: 
					service.getDb().deleteObject(dtl, requestContext);
					break; 
				default:   
					service.getDb().saveObjectAndId(dtl, requestContext); 
			} 
		}
	}
	 
	 
	   
	 
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,SubscribeForm form){
	 	 
		 submitEmployeeReceiverList(requestContext,service,form); 
		 OperatePromptBean prompt=new OperatePromptBean(); 
		 return prompt;
	}
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,SubscribeForm form){
		String[] ids=form.getSelectedRecordIds();
		for(String id:ids){
			id=id.substring(1, id.length()-1);
			service.getDb().executeUpdateHql("delete from SubscribeReceiver where id="+id, requestContext);
		}
		OperatePromptBean prompt=new OperatePromptBean(); 
		return prompt;
	}
	 
}
