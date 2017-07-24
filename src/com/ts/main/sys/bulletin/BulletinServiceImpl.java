package com.ts.main.sys.bulletin;
    
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.loader.SessionListener;
import com.ts.core.system.user.UserBean;
import com.ts.core.util.StringUtils;
import com.ts.main.crm.cust.Customer;
import com.ts.main.hr.company.Company;
import com.ts.main.hr.department.Department;
import com.ts.main.hr.employee.Employee;

@Service("bulletinService")
public class BulletinServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass()); 
	public void addBulletin(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		 
	}
	private void submitFileList(RequestContext requestContext,IBaseServiceManger service,BulletinForm form){
		if(form.getBulletinFileList()==null || form.getBulletinFileList().size()<=0) return;
		for(int i=0;i<form.getBulletinFileList().size();i++){
			BulletinFile dtl=form.getBulletinFileList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
					BulletinFile orignBean=service.getDb().getObject(BulletinFile.class, dtl.getId(), requestContext);
					BeanUtils.copyNoNullProperties(dtl, orignBean);
					dtl=orignBean;
					service.getDb().updateObject(dtl, requestContext);
					break; 
			    case delete: 
					service.getDb().deleteObject(dtl, requestContext);
					break; 
				default:  
					dtl.setBulletin(form.getBean()); 
					service.getDb().saveObjectAndId(dtl, requestContext); 
			} 
		}
	}
	private void submitEmployeeReceiverList(RequestContext requestContext,IBaseServiceManger service,BulletinForm form){
		if(form.getEmployeeReceiverList()==null || form.getEmployeeReceiverList().size()<=0) return;
		for(int i=0;i<form.getEmployeeReceiverList().size();i++){
			BulletinReceiver dtl=form.getEmployeeReceiverList().get(i); 
			dtl.setReceiverType(form.getBean().getReceiverType());
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
				   BulletinReceiver orignBean=service.getDb().getObject(BulletinReceiver.class, dtl.getId(), requestContext);
					BeanUtils.copyNoNullProperties(dtl, orignBean);
					dtl=orignBean;
					service.getDb().updateObject(dtl, requestContext);
					break; 
			    case delete: 
					service.getDb().deleteObject(dtl, requestContext);
					break; 
				default:  
					dtl.setBulletin(form.getBean()); 
					service.getDb().saveObjectAndId(dtl, requestContext); 
			} 
		}
	}
	private void submitCustReceiverList(RequestContext requestContext,IBaseServiceManger service,BulletinForm form){
		if(form.getCustReceiverList()==null || form.getCustReceiverList().size()<=0) return;
		for(int i=0;i<form.getCustReceiverList().size();i++){
			BulletinReceiver dtl=form.getCustReceiverList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
				   BulletinReceiver orignBean=service.getDb().getObject(BulletinReceiver.class, dtl.getId(), requestContext);
					BeanUtils.copyNoNullProperties(dtl, orignBean);
					dtl=orignBean;
					service.getDb().updateObject(dtl, requestContext);
					break; 
			    case delete: 
					service.getDb().deleteObject(dtl, requestContext);
					break; 
				default:  
					dtl.setBulletin(form.getBean()); 
					service.getDb().saveObjectAndId(dtl, requestContext); 
			} 
		}
	}
	private void submitDetailList(RequestContext requestContext,IBaseServiceManger service,BulletinForm form){
		 submitFileList(requestContext,service,form);
		 submitEmployeeReceiverList(requestContext,service,form);
		 submitCustReceiverList(requestContext,service,form);
		 
		 StringBuffer sql=new StringBuffer();
		 sql.append(" delete from BulletinReceiver where bulletin.id="+form.getBean().getId());
		 sql.append(" and receiverType!='"+form.getBean().getReceiverType()+"' ");
		 service.getDb().executeUpdateHql(sql.toString(), requestContext);
		 
	}
	private static boolean validateHintEmplolyee(UserBean user,BulletinForm form,RequestContext requestContext , IBaseServiceManger service){
		Bulletin bean=form.getBean();
		if(Globals.receiverType.all.name().equals(bean.getReceiverType())){
			return true;
		}
		if(!StringUtils.isNoValue(user.getCustId()) && Globals.receiverType.cust.name().equals(bean.getReceiverType())){
			if(!StringUtils.isNoValue(bean.getCustIds())){
				String[] orgIds=bean.getCustIds().split(","); 
				for(String id:orgIds){ 
					 if(id.equals(user.getCustId().toString())){
						 return true;
					 } 
				}
			} else {
				return true;
			} 
		} else {
			if(Globals.receiverType.group.name().equals(bean.getReceiverType())){
				return true;
			}
			switch (Globals.receiverType.valueOf(bean.getReceiverType())){
				case company:
					if(!StringUtils.isNoValue(bean.getCompanyIds())){
						String[] orgIds=bean.getCompanyIds().split(",");
						String[] compIds=user.getCompanyIds().split(",");
						for(String id:orgIds){
							 for(String org:compIds){
								 if(id.equals(org)){
									 return true;
								 }
							 }
						}
					} 
					break;
				case employee:
					if(!StringUtils.isNoValue(bean.getEmployeeIds())){
						String[] orgIds=bean.getEmployeeIds().split(",");
						String[] compIds=user.getEmployeeIds().split(",");
						for(String id:orgIds){
							 for(String org:compIds){
								 if(id.equals(org)){
									 return true;
								 }
							 }
						}
					} 
					break;
				case dept: 
					if(!StringUtils.isNoValue(bean.getDeptIds())){
						String[] orgIds=bean.getDeptIds().split(",");
						String[] compIds=user.getDeptIds().split(",");
						for(String id:orgIds){
							 for(String org:compIds){
								 if(id.equals(org)){
									 return true;
								 }
							 }
						}
					} 
				    break; 
			}
		} 
		return false;
	}
	private static String getLoginIds(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		StringBuffer sb=new StringBuffer(",");
		Object[] users=SessionListener.loginMap.values().toArray();
		int n=0;
		for(int i=0;i<users.length;i++){
			UserBean user=(UserBean)users[i];
			boolean b=false;
			if(user.isAdmin()){
				b=true;
			} else{
			    b=validateHintEmplolyee(user,form,requestContext,service);
			}
			if(b){ 
				sb.append(user.getId().toString());
				if(n>0)
					sb.append(",");
				n++;
			}
		}
		return sb.toString();
	}
	private void convertIds(Bulletin bean,BulletinForm form){
		StringBuffer sb=new StringBuffer("");
		if(form.getEmployeeReceiverList()!=null && form.getEmployeeReceiverList().size()>0){
			int i=0;
			for(BulletinReceiver rec:form.getEmployeeReceiverList()){
				if(i>0) sb.append(",");
				sb.append(rec.getEmployeeId().toString());
				i++;
			}
			bean.setEmployeeIds(sb.toString());
		}
		sb.setLength(0);
		if(form.getCustReceiverList()!=null && form.getCustReceiverList().size()>0){
			int i=0;
			for(BulletinReceiver rec:form.getCustReceiverList()){
				if(i>0) sb.append(",");
				sb.append(rec.getReceiverId().toString()); 
				i++;
			}
			bean.setCustIds(sb.toString());
		}
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		Bulletin bean=form.getBean();
		service.getDb().saveObjectAndId(bean,requestContext); 
		 submitDetailList(requestContext,service,form);
		 convertIds(form.getBean(),form);
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 pushMsg(requestContext,service,form);
		 return prompt;
	}
	public static void pushMsg(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		 Bulletin bean=form.getBean();
		 List topicKinds=(List)requestContext.getMessageResource().getSqlResultMap("topicKindList");			 
		 String ids=getLoginIds(requestContext,service,form);
		 Map eventParam=new HashMap();
		 eventParam.put("userIds", ids);
		 Map<String,Object> msgMap=new HashMap(); 
		 msgMap.put("id", bean.getId()); 
		 msgMap.put("name",bean.getName()); 	
		 String topicKindName=service.getRuleService().getDicCodeToName(topicKinds, bean.getTopicKind());
		 msgMap.put("topicKindName", topicKindName);
		 msgMap.put("createDate",bean.getCreateDate());
		 if("1".equals(bean.getEmergentLevel())){
			 msgMap.put("remindMode", "dialog");
			 msgMap.put("emergentLevel", "1");
			 msgMap.put("fontColor", "0");
		 } else {
			 msgMap.put("emergentLevel", "0");
			 msgMap.put("fontColor", "1");
			 msgMap.put("remindMode", "message");
		 }
		 if(Globals.recordOperateStatus.delete.name().equals(bean.getRecordOperateStatus())){
			 msgMap.put("records",-1);
		 } else {
			 msgMap.put("records",1);
		 }
		 msgMap.put("image","1");
		 eventParam.put("msg",msgMap); 
		 service.getPushlet().publishMsg("/ts/bulletin", Globals.PushEvent.multicast, eventParam, requestContext, service);			
	}
	public void editBulletin(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		 Bulletin bean=service.getDb().getObject(Bulletin.class, form.getId(),requestContext);
		 form.setBean(bean);
		 getDetailList(requestContext,service,form);
		 
		 /*String sql=" select a.* from SYS_Bulletin as a where a.id="+form.getId();
		 List lt=service.getDb().executeSqlToList(sql);
		 Map row=(Map)lt.get(0);
		  ClobImpl  cl=(ClobImpl )row.get("description");
		 String desc= DataBaseUtil.getClobString(cl);
		 System.out.println(desc);*/
	}
	public void lookupBulletin(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		editBulletin(requestContext,service,form);
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		 Bulletin bean=service.getDb().getObject(Bulletin.class, form.getBean().getId(),requestContext); 
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().updateObject(bean,requestContext); 
		 submitDetailList(requestContext,service,form);
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(form.getBean().getId().toString());
		 prompt.setStatememt(prompt.hint_success); 
		 return prompt;
	}
	public static void getDetailList(RequestContext requestContext,IBaseServiceManger service,BulletinForm form){
		Bulletin bean=form.getBean();
		StringBuffer sql=new StringBuffer();
		sql.append("select a from BulletinFile as a where a.bulletin.id="+bean.getId());
		List<BulletinFile> bulletinFileList=service.getDb().queryHqlForList(sql.toString());
		form.setBulletinFileList(bulletinFileList);
		sql.setLength(0);
		sql.append("select a from BulletinReceiver as a where a.bulletin.id="+bean.getId());
		List<BulletinReceiver> receiverList=service.getDb().queryHqlForList(sql.toString());
		for(int i=0;i<receiverList.size();i++){
			BulletinReceiver receiver=receiverList.get(i);
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
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){ 
		Bulletin bean=service.getDb().getObject(Bulletin.class, form.getId(),requestContext); 
		form.setBean(bean);
		getDetailList(requestContext,service,form);
		service.getDb().deleteList(form.getBulletinFileList(), requestContext);
		service.getDb().deleteList(form.getEmployeeReceiverList(), requestContext);
		StringBuffer sql=new StringBuffer();
		sql.append(" delete from BulletinViewer where bulletin="+bean.getId());
		service.getDb().executeUpdateHql(sql.toString(), requestContext);
		service.getDbService().deleteObject(bean,requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		bean.setRecordOperateStatus(Globals.recordOperateStatus.delete.name());
		pushMsg(requestContext,service,form);
		return prompt;
	}
	 
	 
}
