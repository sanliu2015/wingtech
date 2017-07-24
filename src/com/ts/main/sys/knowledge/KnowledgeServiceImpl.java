package com.ts.main.sys.knowledge;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.service.IHibernateService;
import com.ts.core.system.syscode.SysCode;
import com.ts.main.crm.cust.Customer;
import com.ts.main.hr.company.Company;
import com.ts.main.hr.department.Department;
import com.ts.main.hr.employee.Employee;

@Service("mainknowledgeService")
public class KnowledgeServiceImpl implements IAppService {
	private     Log log = LogFactory.getLog(this.getClass());
	
	public String saveKnow(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		IHibernateService dbservice = service.getDbService();
		String hql = "select code,name from TS_SysCode where codekind=26";
		String hql2 = "select id,name from SYS_Knowledge";
		String hql3 = "select id,typeName from SYS_KnowledgeType";
		List<SysCode> peopleType = service.getDbService().executeSqlToList(hql);
		List<Knowledge> parentId = service.getDbService().executeSqlToList(hql2);
		List<KnowledgeType> knowType = service.getDbService().executeSqlToList(hql3);
		requestContext.getRequest().setAttribute("peopleType", peopleType);
		requestContext.getRequest().setAttribute("parentId", parentId);
		requestContext.getRequest().setAttribute("knowType", knowType);
		return "main/sys/knowledge/saveKnow";
	}
	
	public OperatePromptBean save(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Knowledge know = form.getKnow();
		service.getDbService().saveObjectAndId(know, requestContext);
		submitDetailList(requestContext,service,form);
		convertIds(form.getKnow(),form);
		OperatePromptBean opb = new OperatePromptBean();
		opb.setStatememt(opb.hint_success);
		return opb;
	}
	
	public String updateKnow(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Integer id = Integer.parseInt(requestContext.getRequest().getParameter("id"));
		String hql = "from Knowledge where id="+id;
		Knowledge know = (Knowledge)service.getDbService().queryHqlForList(hql, requestContext).get(0);
		form.setKnow(know);
		
		IHibernateService dbservice = service.getDbService();
		String hql4 = "select code,name from TS_SysCode where codekind=26";
		String hql2 = "select id,name from SYS_Knowledge where id !="+id;
		String hql3 = "select id,typeName from SYS_KnowledgeType";
		List<SysCode> peopleType = service.getDbService().executeSqlToList(hql4);
		List<Knowledge> parentId = service.getDbService().executeSqlToList(hql2);
		List<KnowledgeType> knowType = service.getDbService().executeSqlToList(hql3);
		requestContext.getRequest().setAttribute("peopleType", peopleType);
		requestContext.getRequest().setAttribute("parentId", parentId);
		requestContext.getRequest().setAttribute("knowType", knowType);
		
		getDetailList( requestContext, service, form);
		
		return "main/sys/knowledge/updateKnow";
	}
	
	public OperatePromptBean update(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Knowledge oldKnow = service.getDbService().getObject(Knowledge.class, form.getKnow().getId(), requestContext);
		Knowledge nowKnow = form.getKnow();
		BeanUtils.copyNoNullProperties(nowKnow,oldKnow);
		service.getDb().updateObject(oldKnow, requestContext);
		submitDetailList(requestContext,service,form);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(form.getKnow().getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
	
	public OperatePromptBean delete(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Integer id = Integer.parseInt(requestContext.getRequest().getParameter("id"));
		Knowledge know = service.getDbService().getObject(Knowledge.class, id, requestContext);
		form.setKnow(know);
		getDetailList( requestContext, service, form);
		service.getDb().deleteList(form.getKnowledgeFileList(), requestContext);
		service.getDb().deleteList(form.getEmployeeReceiverList(), requestContext);
		if(!"1".equals(form.getNeedValidate())){
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(id) from SYS_Knowledge where knowledgeId="
					+ id);
			int rec = service.getDb().queryForInt(sql.toString());
			if (rec > 0) {
				requestContext.setConfirm(new StringBuffer("存在下级目录，确认要删除吗？"));
				OperatePromptBean prompt = new OperatePromptBean();
				prompt.setId(know.getId().toString());
				prompt.setStatememt(prompt.hint_failure);
				return prompt;
			}
		}
		queryChildNodes(know, service, requestContext);
		service.getDbService().deleteObject(know, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(know.getId().toString());
		prompt.setStatememt(prompt.hint_success);
		know.setRecordOperateStatus(Globals.recordOperateStatus.delete.name());
		return prompt;
	}
	
	public void queryChildNodes(Knowledge know,IBaseServiceManger service,
			RequestContext requestContext){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a from Knowledge as a where a.knowledgeId="
				+ know.getId());
		List<Knowledge> list = service.getDb().queryHqlForList(
				sql.toString());
		for (int i = 0; i < list.size(); i++) {
			Knowledge childNode = list.get(i);
			queryChildNodes(childNode, service, requestContext);
		}
		service.getDb().deleteList(list, requestContext);
	}
	
	private void submitFileList(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		if(form.getKnowledgeFileList()==null || form.getKnowledgeFileList().size()<=0) 
			return;
		for(int i=0;i<form.getKnowledgeFileList().size();i++){
			KnowledgeFile knowFile = form.getKnowledgeFileList().get(i);
			switch(Globals.recordOperateStatus.getStatus(knowFile.getRecordOperateStatus())){
				case update:
					 KnowledgeFile oldFile = service.getDb().getObject(KnowledgeFile.class, knowFile.getId(), requestContext);
					 BeanUtils.copyNoNullProperties(knowFile, oldFile);
					 knowFile = oldFile;
					 service.getDb().updateObject(knowFile, requestContext);
					 break;
				case delete:
					service.getDb().deleteObject(knowFile, requestContext);
					 break;
				default:
				knowFile.setKnowledge(form.getKnow());
				service.getDb().saveObjectAndId(knowFile, requestContext);
			}
		}
	}
	
	public static void getDetailList(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		Knowledge know = form.getKnow();
		StringBuffer sql = new StringBuffer();
		sql.append("select a from KnowledgeFile as a where a.knowledge.id ="+know.getId());
		List<KnowledgeFile> fileList = service.getDb().queryHqlForList(sql.toString());
		form.setKnowledgeFileList(fileList);
		sql.setLength(0);
		sql.append("select a from KnowledgeReceiver as a where a.know.id="+know.getId());
		List<KnowledgeReceiver> receiverList = service.getDb().queryHqlForList(sql.toString());
		for(int i=0;i<receiverList.size();i++){
			KnowledgeReceiver receiver=receiverList.get(i);
			if(receiver.getReceiverType()!=null)
			switch (Globals.receiverType.valueOf(receiver.getReceiverType())){
				case company:Company company=service.getDb().getObject(Company.class, receiver.getReceiverId(), requestContext);
						receiver.setCompany(company);
						break;
				case employee:Employee employee=service.getDb().getObject(Employee.class, receiver.getReceiverId(), requestContext);
						receiver.setEmployee(employee);
						break;
				case dept:Department dept=service.getDb().getObject(Department.class, receiver.getReceiverId(), requestContext);
						receiver.setDept(dept);
				        break;
				case cust:Customer cust=service.getDb().getObject(Customer.class, receiver.getReceiverId(), requestContext);
						receiver.setCustomer(cust);
						break;
			}
		}
		form.setEmployeeReceiverList(receiverList);
	}
	
	private void convertIds(Knowledge know,KnowledgeForm form){
		StringBuffer sb=new StringBuffer("");
		if(form.getEmployeeReceiverList()!=null && form.getEmployeeReceiverList().size()>0){
			int i=0;
			for(KnowledgeReceiver rec:form.getEmployeeReceiverList()){
				if(i>0) sb.append(",");
				sb.append(rec.getEmployeeId().toString());
				i++;
			}
			know.setEmployeeIds(sb.toString());
		}
	}
	
	private void submitEmployeeReceiverList(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		if(form.getEmployeeReceiverList()==null || form.getEmployeeReceiverList().size()<=0) return;
		for(int i=0;i<form.getEmployeeReceiverList().size();i++){
			KnowledgeReceiver dtl=form.getEmployeeReceiverList().get(i); 
			dtl.setReceiverType(form.getKnow().getReceiverType());
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
				   KnowledgeReceiver orignBean=service.getDb().getObject(KnowledgeReceiver.class, dtl.getId(), requestContext);
					BeanUtils.copyNoNullProperties(dtl, orignBean);
					dtl=orignBean;
					service.getDb().updateObject(dtl, requestContext);
					break; 
			    case delete: 
					service.getDb().deleteObject(dtl, requestContext);
					break; 
				default:  
					dtl.setKnow(form.getKnow()); 
					service.getDb().saveObjectAndId(dtl, requestContext); 
			} 
		}
	}
	
	private void submitDetailList(RequestContext requestContext,IBaseServiceManger service,KnowledgeForm form){
		submitFileList(requestContext,service,form);
		submitEmployeeReceiverList(requestContext,service,form);
		 
		 StringBuffer sql=new StringBuffer();
		 sql.append(" delete from KnowledgeReceiver where know.id="+form.getKnow().getId());
		 sql.append(" and receiverType!='"+form.getKnow().getReceiverType()+"' ");
		 service.getDb().executeUpdateHql(sql.toString(), requestContext);
		 
	}
}
