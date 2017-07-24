package com.ts.main.sys.knowledge;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.service.IHibernateService;

@Service("knowledgeTypeService")
public class KnowledgeTypeServiceImpl implements IAppService {
	private     Log log = LogFactory.getLog(this.getClass());
	public String addType(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		String hql = "select id,typeName from SYS_KnowledgeType";
		List<KnowledgeType> listType = service.getDbService().executeSqlToList(hql);
		form.setTypeList(listType);
		return "main/sys/knowledge/addType";
	}
	
	public OperatePromptBean save(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		KnowledgeType type = form.getKnowType();
		service.getDbService().saveObjectAndId(type, requestContext);
		OperatePromptBean opb = new OperatePromptBean();
		opb.setStatememt(opb.hint_success);
		return opb;
	}
	public String updateType(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Integer id = Integer.parseInt(requestContext.getRequest().getParameter("id"));
		String hql = "from KnowledgeType where id="+id;
		KnowledgeType type = (KnowledgeType)service.getDbService().queryHqlForList(hql, requestContext).get(0);
		form.setKnowType(type);
		String sql = "select id,typeName from SYS_KnowledgeType where id !="+id;
		List<KnowledgeType> listType = service.getDbService().executeSqlToList(sql);
		form.setTypeList(listType);
		return "main/sys/knowledge/updateType";
	}
	
	public OperatePromptBean update(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		KnowledgeType oldType = service.getDbService().getObject(KnowledgeType.class, form.getKnowType().getId(), requestContext);
		KnowledgeType newType = form.getKnowType();
		BeanUtils.copyNoNullProperties(newType,oldType);
		service.getDbService().updateObject(oldType, requestContext);
		OperatePromptBean opb = new OperatePromptBean();
		opb.setStatememt(opb.hint_success);
		return opb;
	}
	
	public OperatePromptBean deleteType(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Integer id = Integer.parseInt(requestContext.getRequest().getParameter("id"));
		KnowledgeType type = service.getDbService().getObject(KnowledgeType.class, id, requestContext);
		
		if(!"1".equals(form.getNeedValidate())){
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(id) from SYS_KnowledgeType where knowledgeTypeId="
					+ id);
			int rec = service.getDb().queryForInt(sql.toString());
			if (rec > 0) {
				requestContext.setConfirm(new StringBuffer("存在下级目录，确认要删除吗？"));
				OperatePromptBean prompt = new OperatePromptBean();
				prompt.setId(type.getId().toString());
				prompt.setStatememt(prompt.hint_failure);
				return prompt;
			}
		}
		queryChildNodes(type, service, requestContext);
		service.getDbService().deleteObject(type, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(type.getId().toString());
		prompt.setStatememt(prompt.hint_success);
		/*
		String sql = "from Knowledge where knowledgeType="+id;
		List<Knowledge> listKnow = service.getDb().queryHqlForList(sql);
		if(listKnow.size()>0){
			for(Knowledge know : listKnow){
				service.getDbService().deleteObject(know, requestContext);
			}
		}
		service.getDbService().deleteObject(type, requestContext);*/
		return prompt;
	}
	
	public void queryChildNodes(KnowledgeType type,IBaseServiceManger service,
			RequestContext requestContext){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a from KnowledgeType as a where a.knowledgeTypeId="
				+ type.getId());
		List<KnowledgeType> list = service.getDb().queryHqlForList(
				sql.toString());
		for (int i = 0; i < list.size(); i++) {
			KnowledgeType childNode = list.get(i);
			queryChildNodes(childNode, service, requestContext);
		}
		service.getDb().deleteList(list, requestContext);
	}
}
