package com.ts.main.daily.feekind;
   
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

@Service("feeKindService")
public class FeeKindServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass());
	 
	  
	public void addFeeKind(RequestContext requestContext , IBaseServiceManger service,FeeKindForm form){
		 Integer id=form.getId();
		 if(StringUtils.isNoValue(id)==false){
			 FeeKind bean=service.getDb().getObject(FeeKind.class, id,requestContext); 
			 form.getBean().setParentId(id);
			 form.getBean().setParentName(bean.getName()); 
		 }
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,FeeKindForm form){
		 service.getDb().saveObjectAndId(form.getBean(),requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public void editFeeKind(RequestContext requestContext , IBaseServiceManger service,FeeKindForm form){
		FeeKind bean=service.getDb().getObject(FeeKind.class, form.getId(),requestContext); 
		form.setBean(bean); 
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,FeeKindForm form){
		 FeeKind bean=service.getDb().getObject(FeeKind.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().updateObject(bean,requestContext); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	private void queryChildNodes(FeeKind bean,  IBaseServiceManger service,RequestContext requestContext ){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from FeeKind as a where a.parentId="+bean.getId());
		List<FeeKind> list=service.getDb().queryHqlForList(sql.toString(),requestContext);
		for(int i=0;i<list.size();i++){
			FeeKind childNode=list.get(i); 
			queryChildNodes(childNode, service,requestContext);
		}
		service.getDb().deleteList(list,requestContext);
	}
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,FeeKindForm form){ 
		FeeKind bean=service.getDb().getObject(FeeKind.class, form.getId(),requestContext); 
		if(!"1".equals(form.getNeedValidate())){
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(id) from DAILY_FeeKind where parentId="+form.getId());
			int rec=service.getDb().queryForInt(sql.toString());
			if(rec>0){
				requestContext.setConfirm(new StringBuffer("validate.existChildNodes"));
				OperatePromptBean prompt=new OperatePromptBean();
				prompt.setId(bean.getId().toString());
				prompt.setStatememt(prompt.hint_failure); 
				return prompt;
			}   
			sql.setLength(0);
			sql.append(" select count(id) from DAILY_TravelFeeDtl where feeKindId="+form.getId());
			rec=service.getDb().queryForInt(sql.toString());
			if(rec>0){
				requestContext.setConfirm(new StringBuffer("validate.relateTravelFee"));
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
