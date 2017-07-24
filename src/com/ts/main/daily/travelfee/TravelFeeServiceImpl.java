package com.ts.main.daily.travelfee;
  
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;  

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger; 
import com.ts.core.context.AppContext;
import com.ts.core.context.RequestContext; 
import com.ts.core.system.user.UserForm;
import com.ts.core.util.StringUtils;
import com.ts.core.workflow.audit.WorkFlowAuditForm;
import com.ts.core.workflow.audit.WorkFlowAuditServiceImpl;
import com.ts.core.workflow.utils.WorkFlowBusiness;
import com.ts.main.daily.calendar.CalendarLog;
import com.ts.main.daily.feekind.FeeKindForm;
import com.ts.main.hr.department.Department;
import com.ts.main.sys.bulletin.BulletinFile;
@Service("travelFeeService")
public class TravelFeeServiceImpl  implements IAppService{
	 
	public void addTravelFee(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){
		TravelFee bean=form.getBean();
		if(StringUtils.isNoValue(bean.getCalendarLogId())==false){
			CalendarLog log=service.getDb().getObject(CalendarLog.class, bean.getCalendarLogId(), requestContext);
			if(log!=null){
				bean.setCalendarLogEvent(log.getSummaryEvent());
				StringBuffer sql=new StringBuffer();
				sql.append("select a from TravelFee as a where a.calendarLogId="+log.getId());
				List<TravelFee> lt=service.getDb().queryHqlForList(sql.toString());
				if(lt!=null && lt.size()>0){
					TravelFee fee=lt.get(0);
					form.setId(fee.getId());
					form.setBean(fee);
					editTravelFee(requestContext,service,form);
					requestContext.setAppUrl("/main/daily/travelfee/EditTravelFee");
				}
			}
		}
	}
	private void submitDtlList(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){
		if(form.getDtlList()!=null)
		for(int i=0;i<form.getDtlList().size();i++){
			TravelFeeDtl dtl=form.getDtlList().get(i);
			dtl.setHdr(form.getBean()); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
				   TravelFeeDtl orignBean=service.getDb().getObject(TravelFeeDtl.class, dtl.getId(), requestContext);
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
	
	private void submitFileList(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){
		if(form.getFileList()!=null)
		for(int i=0;i<form.getFileList().size();i++){
			TravelFeeFile dtl=form.getFileList().get(i);
			dtl.setHdr(form.getBean()); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())){
			   case update: 
				   TravelFeeFile orignBean=service.getDb().getObject(TravelFeeFile.class, dtl.getId(), requestContext);
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
	private void updateRefCalendarLog(TravelFee bean){
		if(!StringUtils.isNoValue(bean.getCalendarLogId())){
			StringBuffer sql=new StringBuffer();
			sql.append(" update CalendarLog set ");
			if(Globals.recordOperateStatus.delete.name().equals(bean.getRecordOperateStatus())){
				sql.append(" travelFeeId=0 " );
				sql.append(",travelFeeNo='' ");
			} else {
				sql.append(" travelFeeId="+bean.getId());
				sql.append(",travelFeeNo='"+bean.getNumber()+"' ");
			}
			sql.append(" where id="+bean.getCalendarLogId());
		}
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){
		TravelFee bean=form.getBean();
		bean.setAuditStatus("0");
		bean.setApplyDate(requestContext.getCurrentDate());
		Department dept=service.getDb().getObject(Department.class, bean.getDeptId(), requestContext);
		bean.setOrgId(dept.getCompany().getId()); 
		service.getDb().saveObjectAndId(bean, requestContext);
		submitDtlList(requestContext,service,form);
		submitFileList(requestContext,service,form);
		updateRefCalendarLog(bean);
		OperatePromptBean prompt=new OperatePromptBean();
		service.getWorkflowbusiness().startWorkFlow("TravelFeeServiceReport", "",null, bean, requestContext, service,prompt);
		prompt.setBillId(bean.getId());
		prompt.setNeedWriteResponse("1"); 
		return prompt;    
	}
	public void editTravelFee(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){ 
		TravelFee bean=service.getDb().getObject(TravelFee.class, form.getId(), requestContext);
		bean.setEmployeeName(bean.getEmployee().getName());
		form.setBean(bean);
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from TravelFeeDtl as a where a.hdr="+bean.getId());
		List<TravelFeeDtl> lt=service.getDb().queryHqlForList(sql.toString(),requestContext);
		form.setDtlList(lt);
		sql.setLength(0);
		sql.append(" select a from TravelFeeFile as a where a.hdr="+bean.getId());
		List<TravelFeeFile> fileList=service.getDb().queryHqlForList(sql.toString(),requestContext);
		form.setFileList(fileList);
	}
	public void printTravelFee(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){ 
		editTravelFee(requestContext,service,form);
	}
	public OperatePromptBean update(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){
		TravelFee bean=service.getDb().getObject(TravelFee.class, form.getBean().getId(), requestContext);
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		Department dept=service.getDb().getObject(Department.class, bean.getDeptId(), requestContext);
		bean.setOrgId(dept.getCompany().getId()); 
		service.getDb().updateObject(bean,requestContext); 
		form.setBean(bean);
		submitDtlList(requestContext,service,form);
		submitFileList(requestContext,service,form);
		updateRefCalendarLog(bean);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setBillId(bean.getId() );
		service.getWorkflowbusiness().startWorkFlow("TravelFeeServiceReport", "",null, bean, requestContext, service,prompt);
		return prompt;
	}
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,TravelFeeForm form){ 
		TravelFee bean=service.getDb().getObject(TravelFee.class, form.getId(), requestContext);
		bean.setRecordOperateStatus(Globals.recordOperateStatus.delete.name());
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from TravelFeeDtl as a where a.hdr="+bean.getId());
		List<TravelFeeDtl> lt=service.getDb().queryHqlForList(sql.toString());
		service.getDb().deleteList(lt, requestContext);
		sql.setLength(0);
		sql.append(" select a from TravelFeeFile as a where a.hdr="+bean.getId());
		List<TravelFeeFile> fileList=service.getDb().queryHqlForList(sql.toString());
		service.getDb().deleteList(fileList, requestContext);
		service.getDb().deleteObject(bean, requestContext);
		updateRefCalendarLog(bean);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setBillId(bean.getId() );
		if(!StringUtils.isNoValue(bean.getProcessInstanceId())){
			prompt.setDeleteProcessInstance("1");
			service.getWorkflowbusiness().deleteAuditNode(bean,requestContext, service, prompt);
			 
		}
		
		return prompt;
	}
}
