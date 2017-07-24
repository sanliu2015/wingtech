package com.ts.main.daily.calendar;
   
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;  
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;  
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;    

import com.ts.core.common.bean.BaseBean; 
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;   
import com.ts.core.util.DateTimeUtil;
import com.ts.core.util.StringUtils;
import com.ts.main.hr.employee.Employee;
import com.ts.main.sys.bulletin.BulletinForm;

@Service("calendarService")
public class CalendarServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass());
	 
	public void addMyCalendarPlan(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarPlan plan=form.getPlan();
		if(StringUtils.isNoValue(plan.getPlanDate())){
			plan.setPlanDate(DateTimeUtil.formatDate());
		    plan.getEmployee().setId(requestContext.getEmployeeId());
		}
		if(StringUtils.isNoValue(plan.getDayType())){
			String time=DateTimeUtil.formatTime();
			if(new Integer(time.substring(0, 2)).intValue()>12)
				plan.setDayType("pm");
			else
				plan.setDayType("am");
		}
		String date=form.getPlan().getPlanDate(); 
		String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
		String dateType=plan.getDayType();
		if("am".equals(dateType))
			dateType=" 上午";
		else
			dateType=" 下午";
		String name=date+" "+weekName+dateType;
		plan.setName(name);
		plan.setPlanWeek(weekName);
	} 
	public void editMyCalendarPlan(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarPlan plan=service.getDb().getObject(CalendarPlan.class,form.getId(), requestContext);
		form.setPlan(plan);
		String date=plan.getPlanDate();
		String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
		String dateType=plan.getDayType();
		if("am".equals(dateType))
			dateType=" 上午";
		else
			dateType=" 下午";
		String name=date+" "+weekName+dateType; 
		plan.setName(name);
	} 
	public void addMyCalendarLog(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarLog log=form.getLog();
		
		if(StringUtils.isNoValue(log.getSummaryDate())){
			log.setSummaryDate(DateTimeUtil.formatDate());
			log.getEmployee().setId(requestContext.getEmployeeId());
		}
		String date=log.getSummaryDate();
		if(StringUtils.isNoValue(log.getDayType())){
			String time=DateTimeUtil.formatTime();
			if(new Integer(time.substring(0, 2)).intValue()>12)
				log.setDayType("pm");
			else
				log.setDayType("am");
		}
		String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
		String dateType=log.getDayType();
		if("am".equals(dateType))
			dateType=" 上午";
		else
			dateType=" 下午";
		String name=date+" "+weekName+dateType;
		log.setName(name);
		log.setSummaryWeek(weekName);
		queryCalendarPlan(form,service);
	}
	public  static void setCalendarTitle(CalendarLog log){
		String date=log.getSummaryDate();
		if(StringUtils.isNoValue(log.getDayType())){
			String time=DateTimeUtil.formatTime();
			if(new Integer(time.substring(0, 2)).intValue()>12)
				log.setDayType("pm");
			else
				log.setDayType("am");
		}
		String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
		String dateType=log.getDayType();
		if("am".equals(dateType))
			dateType=" 上午";
		else
			dateType=" 下午";
		String name=date+" "+weekName+dateType;
		log.setName(name);
		log.setSummaryWeek(weekName);
		int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		log.setWeekIndex(weekIndex); 
	}
	public void editMyCalendarLog(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarLog clog=null;
		if(!StringUtils.isNoValue(form.getPlanId())){
			CalendarPlan plan=service.getDb().getObject(CalendarPlan.class,form.getPlanId(), requestContext);
			form.setId(plan.getLogId());
			if(StringUtils.isNoValue(plan.getLogId())){
				clog=form.getLog();
				clog.setSummaryDate(plan.getPlanDate());
				clog.setCustName(plan.getCustName());
				clog.setEmployee(plan.getEmployee());
				clog.setSummaryEvent(plan.getEventExplain());
				clog.setDayType(plan.getDayType());
				clog.setSummaryWeek(plan.getPlanWeek());
				clog.setPlanId(plan.getId());
			} else {
				clog=service.getDb().getObject(CalendarLog.class, form.getId(),requestContext);
			}
		} else {
			clog=service.getDb().getObject(CalendarLog.class, form.getId(),requestContext);
		}
		if(clog!=null){
			form.setLog(clog);
			String date=clog.getSummaryDate();
			String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
			String dateType=clog.getDayType();
			if("am".equals(dateType))
				dateType=" 上午";
			else
				dateType="  下午";
			String name=date+" "+weekName+dateType;
			clog.setName(name);
		}
		queryCalendarPlan(form,service);
	}
	private void queryCalendarPlan(CalendarForm form,IBaseServiceManger service){
		CalendarLog log=form.getLog();
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.* from DAILY_CalendarPlan as a where a.employeeId="+log.getEmployee().getId());
		sql.append(" and a.planDate='"+log.getSummaryDate()+"' "); 
		List<Map> lt=  service.getDb().queryForList(sql.toString(), null);
		form.setPlanList(lt);
	}
	public void addMyCalendarSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=form.getSummary();
		String date=summary.getStartDate();
		String[] weeks=DateTimeUtil.getDateRange(Globals.dateRange.week.name(), date);
		summary.setStartDate(weeks[0]);
		 
		summary.setEndDate(weeks[1]);
		String name=weeks[0]+"至"+weeks[1];
		int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		summary.setEventExplain("第"+weekIndex+"周("+name+")的工作总结");
		summary.setName(name);
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from CalendarSummary as a where a.employee.id="+summary.getEmployee().getId());
		sql.append(" and a.startDate='"+date+"' and a.endDate='"+weeks[1]+"' ");
		sql.append(" and a.eventType='summary' ");
		sql.append(" and a.dateRangeType='week' ");
		List<CalendarSummary> lt=service.getDb().queryHqlForList(sql.toString(),requestContext);
		if(lt!=null && lt.size()>0){
			CalendarSummary bean=lt.get(0);
			summary.setId(bean.getId());
			summary.setDescription(bean.getDescription());
			summary.setEventExplain(bean.getEventExplain());
			summary.setFileName(bean.getFileName());
			summary.setSaveToFileName(bean.getSaveToFileName());
			summary.setFilePath(bean.getFilePath());
		}
	}
	public void addMyMonthSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=form.getSummary();
		String date=summary.getStartDate();
		String[] weeks=DateTimeUtil.getDateRange(Globals.dateRange.month.name(), date);
		summary.setStartDate(weeks[0]); 
		summary.setEndDate(weeks[1]);
		String name=weeks[0]+"至"+weeks[1]; 
		String title=DateTimeUtil.formatDate(weeks[0], "yyyy年MM月");
		summary.setEventExplain(title+"的工作总结");
		summary.setName(name);
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from CalendarSummary as a where a.employee.id="+summary.getEmployee().getId());
		sql.append(" and a.startDate='"+date+"' and a.endDate='"+weeks[1]+"' ");
		sql.append(" and a.eventType='summary' ");
		sql.append(" and a.dateRangeType='month' ");
		List<CalendarSummary> lt=service.getDb().queryHqlForList(sql.toString(),requestContext);
		if(lt!=null && lt.size()>0){
			CalendarSummary bean=lt.get(0);
			summary.setId(bean.getId());
			summary.setDescription(bean.getDescription());
			summary.setEventExplain(bean.getEventExplain());
			summary.setFileName(bean.getFileName());
			summary.setSaveToFileName(bean.getSaveToFileName());
			summary.setFilePath(bean.getFilePath());
		}
	}
	public void addMyWeekPlan(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=form.getSummary();
		String date=summary.getStartDate();
		String[] weeks=DateTimeUtil.getDateRange(Globals.dateRange.week.name(), date);
		summary.setStartDate(weeks[0]);
		summary.setEndDate(weeks[1]);
		String name=weeks[0]+"至"+weeks[1];
		int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		summary.setEventExplain("第"+weekIndex+"周("+name+")的工作计划"); 
		summary.setName(name);
		StringBuffer sql=new StringBuffer();
		sql.append(" select a from CalendarSummary as a where a.employee.id="+summary.getEmployee().getId());
		sql.append(" and a.startDate='"+date+"' and a.endDate='"+weeks[1]+"' ");
		sql.append(" and a.eventType='plan' ");
		sql.append(" and a.dateRangeType='week' ");
		List<CalendarSummary> lt=service.getDb().queryHqlForList(sql.toString(),requestContext);
		if(lt!=null && lt.size()>0){
			CalendarSummary bean=lt.get(0);
			summary.setId(bean.getId());
			summary.setDescription(bean.getDescription());
			summary.setEventExplain(bean.getEventExplain());
			summary.setFileName(bean.getFileName());
			summary.setSaveToFileName(bean.getSaveToFileName());
			summary.setFilePath(bean.getFilePath());
			summary.setVisitCustNames(bean.getVisitCustNames());
			summary.setDevelopCustNames(bean.getDevelopCustNames());
		}
	}
	public OperatePromptBean save(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarPlan plan=form.getPlan(); 
		Employee employee=service.getDb().getObject(Employee.class, plan.getEmployee().getId(), requestContext);
		plan.setEmployee(employee);
		plan.setDeptId(employee.getDept().getId());
		 String date=plan.getPlanDate();
		 int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		 plan.setWeekIndex(weekIndex);
		 String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
		 plan.setPlanWeek(weekName);
		 if(!StringUtils.isNoValue(plan.getId())){
			 CalendarPlan targetBean=service.getDb().getObject(CalendarPlan.class,plan.getId(), requestContext);
			 BeanUtils.copyNoNullProperties(plan, targetBean);
			 service.getDb().updateObject(targetBean,requestContext);  
		 } else {
			 service.getDb().saveObjectAndId(plan,requestContext);
		 }
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(plan.getId().toString()); 
		 return prompt;
	}
	 
	public OperatePromptBean saveLog(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		 CalendarLog clog=form.getLog();
		 Employee employee=service.getDb().getObject(Employee.class, clog.getEmployee().getId(), requestContext);
		 clog.setEmployee(employee);
		 clog.setDeptId(employee.getDept().getId());
		 String date=clog.getSummaryDate();
		 int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		 clog.setWeekIndex(weekIndex);
		 String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
		 clog.setSummaryWeek(weekName);
		 if(!StringUtils.isNoValue(clog.getId())){
			 CalendarLog targetBean=service.getDb().getObject(CalendarLog.class,clog.getId(), requestContext);
			 Integer orignRefPlanId=targetBean.getPlanId();
			 BeanUtils.copyNoNullProperties(clog, targetBean);
			 service.getDb().updateObject(targetBean,requestContext);  
			 Integer newPlanId=clog.getPlanId();
			 if(!StringUtils.isNoValue(clog.getPlanId())){
				 StringBuffer sql=new StringBuffer();
				 sql.append(" update CalendarPlan set logId="+clog.getId());
				 sql.append(" where id="+clog.getPlanId());
				 service.getDb().executeUpdateHql(sql.toString(), requestContext);
			 }
			 if(orignRefPlanId!=null && newPlanId!=null){
				 if(orignRefPlanId.intValue()!=newPlanId.intValue()){
					 StringBuffer sql=new StringBuffer(); 
					 sql.append(" update CalendarPlan set logId=0 ");
					 sql.append(" where id="+orignRefPlanId);
					 service.getDb().executeUpdateHql(sql.toString(), requestContext);
				 }
			 }
		 } else {
			 service.getDb().saveObjectAndId(clog,requestContext);  
			 if(!StringUtils.isNoValue(clog.getPlanId())){
				 StringBuffer sql=new StringBuffer();
				 sql.append(" update CalendarPlan set logId="+clog.getId());
				 sql.append(" where id="+clog.getPlanId());
				 service.getDb().executeUpdateHql(sql.toString(), requestContext);
				 
			 }
		 } 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(clog.getId().toString()); 
		 return prompt;
	}
	 
	public OperatePromptBean saveSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=form.getSummary();
		Employee employee=service.getDb().getObject(Employee.class, summary.getEmployee().getId(), requestContext);
		summary.setEmployee(employee);
		summary.setDeptId(employee.getDept().getId());
		 String date=summary.getStartDate();
		 int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		 summary.setWeekIndex(weekIndex);
		 summary.setMonthNo(DateTimeUtil.formatDate(summary.getStartDate(),"MM"));
		 summary.setYearNo(DateTimeUtil.formatDate(summary.getStartDate(),"yyyy"));
		 if(StringUtils.isNoValue(summary.getId()))
			 service.getDb().saveObjectAndId(summary,requestContext);
		 else {
			 CalendarSummary targetBean=service.getDb().getObject(CalendarSummary.class,summary.getId(), requestContext);
			 BeanUtils.copyNoNullProperties(summary, targetBean);
			 service.getDb().updateObject(targetBean,requestContext);
		 }
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(summary.getId().toString()); 
		 return prompt;
	}
	public OperatePromptBean saveMonthSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=form.getSummary();
		Employee employee=service.getDb().getObject(Employee.class, summary.getEmployee().getId(), requestContext);
		summary.setEmployee(employee);
		summary.setDeptId(employee.getDept().getId());
		 String date=summary.getStartDate();
		 int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		 summary.setWeekIndex(weekIndex);
		 summary.setMonthNo(DateTimeUtil.formatDate(summary.getStartDate(),"MM"));
		 summary.setYearNo(DateTimeUtil.formatDate(summary.getStartDate(),"yyyy"));
		 if(StringUtils.isNoValue(summary.getId()))
			 service.getDb().saveObjectAndId(summary,requestContext);
		 else {
			 CalendarSummary targetBean=service.getDb().getObject(CalendarSummary.class,summary.getId(), requestContext);
			 BeanUtils.copyNoNullProperties(summary, targetBean);
			 service.getDb().updateObject(targetBean,requestContext);
		 }
		 OperatePromptBean prompt=new OperatePromptBean(); 
		 prompt.setId(summary.getId().toString()); 
		 return prompt;
	}
	public OperatePromptBean saveWeekPlan(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=form.getSummary();
		Employee employee=service.getDb().getObject(Employee.class, summary.getEmployee().getId(), requestContext);
		summary.setEmployee(employee);
		summary.setDeptId(employee.getDept().getId());
		 String date=summary.getStartDate();
		 int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(date),null);
		 summary.setWeekIndex(weekIndex);
		 summary.setMonthNo(DateTimeUtil.formatDate(summary.getStartDate(),"MM"));
		 summary.setYearNo(DateTimeUtil.formatDate(summary.getStartDate(),"yyyy"));
		 if(StringUtils.isNoValue(summary.getId()))
			 service.getDb().saveObjectAndId(summary,requestContext);
		 else {
			 CalendarSummary targetBean=service.getDb().getObject(CalendarSummary.class,summary.getId(), requestContext);
			 BeanUtils.copyNoNullProperties(summary, targetBean);
			 service.getDb().updateObject(targetBean,requestContext);
		 }
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(summary.getId().toString()); 
		 return prompt;
	}
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		 String status=form.getStatus(); 
		 CalendarPlan plan=service.getDb().getObject(CalendarPlan.class, form.getId(), requestContext);
		 service.getDb().deleteObject(plan, requestContext);
		 Integer id=plan.getId(); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(id.toString()); 
		 return prompt;
	}
	public OperatePromptBean deleteLog(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		 String status=form.getStatus(); 
		 CalendarLog log=service.getDb().getObject(CalendarLog.class, form.getId(), requestContext);
		 service.getDb().deleteObject(log, requestContext);
		 Integer id=log.getId(); 
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(id.toString()); 
		 return prompt;
	}
	public OperatePromptBean deleteSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		   
		 CalendarSummary summary=service.getDb().getObject(CalendarSummary.class, form.getId(), requestContext);
		 service.getDb().deleteObject(summary, requestContext);
		 Integer id=summary.getId();
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(id.toString()); 
		 return prompt;
	}
	public OperatePromptBean deleteMonthSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		   
		 CalendarSummary summary=service.getDb().getObject(CalendarSummary.class, form.getId(), requestContext);
		 service.getDb().deleteObject(summary, requestContext);
		 Integer id=summary.getId();
		 OperatePromptBean prompt=new OperatePromptBean();
		 prompt.setId(id.toString()); 
		 return prompt;
	}
	public void showMyCalendar(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		form.setCalendarDate(DateTimeUtil.formatDate());
	}
	public void editMyCalendarSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=service.getDb().getObject(CalendarSummary.class, form.getId(), requestContext);
        form.setSummary(summary); 
	}
	public void editMyWeekPlan(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=service.getDb().getObject(CalendarSummary.class, form.getId(), requestContext);
        form.setSummary(summary);
		 
	}
	public void editMyMonthSummary(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		CalendarSummary summary=service.getDb().getObject(CalendarSummary.class, form.getId(), requestContext);
        form.setSummary(summary); 
	}
	public CalendarView getWeekList(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		String currentDate=form.getCalendarDate(); 
		String startDate="",endDate="" ; 
		String weekOperateType=form.getWeekOperateType(); 
		String[] weeks=DateTimeUtil.getDateRange(Globals.dateRange.week.name(), currentDate);
		if("prev".equals(weekOperateType)){ 
			endDate=DateTimeUtil.dateIncreaseDay(weeks[0],-1); 
		} else if("next".equals(weekOperateType)){ 
			endDate=DateTimeUtil.dateIncreaseDay(weeks[1],7); 
		} else if("current".equals(weekOperateType)){ 
			currentDate=DateTimeUtil.formatDate();
			weeks=DateTimeUtil.getDateRange(currentDate, "2",0);
			endDate=weeks[1] ; 
		}  else {
			endDate=weeks[1];
		}
		startDate=DateTimeUtil.dateIncreaseDay(endDate,-6); 
		if(StringUtils.isNoValue(weekOperateType)==false && !"current".equals(weekOperateType) ){ 
			currentDate=startDate;
		}
		
		Integer weekNo=DateTimeUtil.getDateOfWeekDay(DateTimeUtil.parseStringToDate(currentDate));
		form.setWeekIndex(weekNo);
		Integer employeeId=form.getEmployeeId();
		CalendarView view=queryDateRangeEvent(requestContext,service,employeeId,startDate,endDate,currentDate);
		view.setCurrentDate(currentDate);
		return view;
	} 
	private   CalendarView queryDateRangeEvent(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate,String currentDate){
		CalendarView view=new CalendarView();
		List<BaseBean> weekTitleList=new ArrayList<BaseBean>();
		String start=startDate;
		for(int i=0;i<7;i++){
			BaseBean bean=new BaseBean();
			String date=DateTimeUtil.dateIncreaseDay(start, i);
			bean.setCreateDate(date);
			if(date.equals(currentDate)){
				bean.setAuditedBy("currentDateTitle");
			} else {
				bean.setAuditedBy("");
			}
			String weekName= DateTimeUtil.getDateOfWeekName(DateTimeUtil.parseStringToDate(date));
			bean.setAuditDate(weekName);
			bean.setName(date+" "+weekName);
			weekTitleList.add(bean);
		}
		view.setWeekTitleList(weekTitleList);
		queryWeekPlanLogEvent(requestContext,service,employeeId,startDate,endDate,currentDate,weekTitleList,view);
		queryWeekLogsEvent(requestContext,service,employeeId,startDate,endDate,currentDate,weekTitleList,view);
		
		queryWeekSummaryEvent(requestContext,service,employeeId,startDate,endDate,currentDate,weekTitleList,view);
		queryWeekPlanEvent(requestContext,service,employeeId,startDate,endDate,currentDate,weekTitleList,view);
		return view; 
	}
	private void queryWeekPlanLogEvent(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate,String currentDate,List<BaseBean> weekList,CalendarView view){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.id,a.planDate,a.eventExplain,a.dayType,a.custName,a.logId");
		sql.append(" from DAILY_CalendarPlan as a where a.employeeId="+employeeId);
		sql.append(" and a.planDate between '"+startDate+"' and '"+endDate+"' ");
		sql.append(" order by a.planDate,a.dayType ");
		List<Map> list=service.getDb().queryForList(sql.toString(), requestContext);
		List amList=new ArrayList();//上午的
		List pmList=new ArrayList();//下午的
		for(int i=0;i<weekList.size();i++){
			BaseBean bean=weekList.get(i); 
			List dayAmRecord=new ArrayList();
			List dayPmRecord=new ArrayList();
			for(int k=0;k<list.size();k++){
				Map row=list.get(k);
				String planDate=(String)row.get("planDate");
				String dayType=(String)row.get("dayType");
				if(bean.getCreateDate().equals(planDate) ){
					if("am".equals(dayType)){
						dayAmRecord.add(row);
					} else {
						dayPmRecord.add(row);
					}
				}
			}
			amList.add(dayAmRecord);
			pmList.add(dayPmRecord);
		}
		view.setPlanAmList(amList);
		view.setPlanPmList(pmList);
	}
	private void queryWeekSummaryEvent(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate,String currentDate,List<BaseBean> weekList,CalendarView view){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from DAILY_CalendarSummary as a where a.employeeId="+employeeId);
		//sql.append(" left join DAILY_CalendarPlan as b on b.id=a.planId ");
		sql.append(" and a.startDate='"+startDate+"' and a.endDate='"+endDate+"' "); 
		sql.append(" and a.eventType='summary' ");
		sql.append(" and a.dateRangeType='week' ");
		List<Map> list=service.getDb().queryForList(sql.toString(), requestContext);  
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				view.setSummary(list.get(i)); 
			}  
		} else {
			Map map=new HashMap();
			map.put("startDate", startDate);
			map.put("description", "");
			map.put("eventExplain", "");
			map.put("id", "0");
			view.setSummary(map); 
		}
	}
	private void queryWeekPlanEvent(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate,String currentDate,List<BaseBean> weekList,CalendarView view){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from DAILY_CalendarSummary as a where a.employeeId="+employeeId); 
		sql.append(" and a.startDate='"+startDate+"' and a.endDate='"+endDate+"' "); 
		sql.append(" and a.eventType='plan' ");
		sql.append(" and a.dateRangeType='week' ");
		List<Map> list=service.getDb().queryForList(sql.toString(), requestContext);  
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				view.setWeekPlan(list.get(i)); 
			}  
		} else {
			Map map=new HashMap();
			map.put("startDate", startDate);
			map.put("description", "");
			map.put("eventExplain", "");
			map.put("visitCustNames", "");
			map.put("developCustNames", "");
			map.put("id", "0");
			view.setWeekPlan(map); 
		}
	}
	private void queryWeekLogsEvent(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate,String currentDate,List<BaseBean> weekList,CalendarView view){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.id,a.planId,a.summaryDate,a.summaryEvent,a.dayType,a.custName");
		sql.append(" from DAILY_CalendarLog as a where a.employeeId="+employeeId);
		//sql.append(" left join DAILY_CalendarPlan as b on b.id=a.planId ");
		sql.append(" and a.summaryDate between '"+startDate+"' and '"+endDate+"' ");
		sql.append(" order by a.summaryDate,a.dayType ");
		List<Map> list=service.getDb().queryForList(sql.toString(), requestContext);
		List amList=new ArrayList();//上午的
		List pmList=new ArrayList();//下午的
		for(int i=0;i<weekList.size();i++){
			BaseBean bean=weekList.get(i); 
			List dayAmRecord=new ArrayList();
			List dayPmRecord=new ArrayList();
			for(int k=0;k<list.size();k++){
				Map row=list.get(k);
				String planDate=(String)row.get("summaryDate");
				String dayType=(String)row.get("dayType");
				if(bean.getCreateDate().equals(planDate) ){
					if("am".equals(dayType)){
						dayAmRecord.add(row);
					} else {
						dayPmRecord.add(row);
					}
				}
			}
			amList.add(dayAmRecord);
			pmList.add(dayPmRecord);
		} 
		view.setLogAmList(amList);
		view.setLogPmList(pmList);
	}
	public CalendarView getMonthList(RequestContext requestContext , IBaseServiceManger service,CalendarForm form){
		String currentDate=form.getCalendarDate(); 
		CalendarView calendarView=new CalendarView();
		String weekOperateType=form.getWeekOperateType(); 
		String[] dates=DateTimeUtil.getDateRange(Globals.dateRange.month.name(), currentDate);
		  
		if("prev".equals(weekOperateType)){ 
			dates=DateTimeUtil.getDateRange(Globals.dateRange.prevMonth.name(), currentDate);
			currentDate=dates[0]; 
		} else if("next".equals(weekOperateType)){ 
			dates=DateTimeUtil.getDateRange(Globals.dateRange.nextMonth.name(), currentDate);
			currentDate=dates[0]; 
		} else if("current".equals(weekOperateType)){ 
			currentDate=DateTimeUtil.formatDate();
			dates=DateTimeUtil.getDateRange(Globals.dateRange.month.name(), currentDate); 
		}  else {
			currentDate=DateTimeUtil.formatDate();
			dates=DateTimeUtil.getDateRange(Globals.dateRange.month.name(), currentDate); 
		} 
		String startDate=dates[0];
		String endDate=dates[1];
		calendarView.setCurrentDate(currentDate);
		calendarView.setStartDate(startDate);
		calendarView.setEndDate(endDate);
		String[] startWeeks=DateTimeUtil.getWeekScope(startDate);
		String[] endWeeks=DateTimeUtil.getWeekScope(endDate);
		int weekDays= DateTimeUtil.getTwoDateIntervalDays(startWeeks[0], endWeeks[1])+1;
		int weeks=weekDays/7;
		if(weekDays%7!=0){
			weeks++;
		}
		String weekStartDate=startWeeks[0];
		List weekViewList=new ArrayList();
		int days=0;
		Integer employeeId=form.getEmployeeId();
		List<Map> monthLogs=queryMonthLogs(requestContext,service,employeeId,startWeeks[0],endWeeks[1]);
		for(int i=0;i<weeks;i++){
			CalendarWeekLogView weekView=new CalendarWeekLogView();
			weekView.setStartDate(DateTimeUtil.dateIncreaseDay(weekStartDate, days));
			int weekIndex=DateTimeUtil.getWeekOfYearWithCPlan(DateTimeUtil.parseStringToDate(weekView.getStartDate()),null);
			weekView.setName("第"+weekIndex+"周");
			List dayViewList=new ArrayList();
			for(int n=0;n<7;n++){
				String date=DateTimeUtil.dateIncreaseDay(weekStartDate, days);
				CalendarDayLogView logView=new CalendarDayLogView();
				logView.setDay(new Integer(DateTimeUtil.formatDate(date, "dd")));
				logView.setCurrentDate(date);
				queryDayLogs(logView,monthLogs); 
				dayViewList.add(logView);
				weekView.setEndDate(DateTimeUtil.dateIncreaseDay(weekStartDate, days));
				days++;
			} 
			weekView.setLogList(dayViewList);
			weekViewList.add(weekView);
		}
		calendarView.setLogList(weekViewList);
		queryMonthSummary(requestContext,service,employeeId,startDate,endDate,calendarView);
		return calendarView;
	}
	private void queryDayLogs(CalendarDayLogView logView,List<Map> monthLogs){
		String date=logView.getCurrentDate();
		for(int k=0;k<monthLogs.size();k++){
			Map log=monthLogs.get(k);
			String logDate=(String)log.get("summaryDate");
			if(date.equals(logDate)){
				if(logView.getLogList()==null) logView.setLogList(new ArrayList());
				logView.getLogList().add(log);
			}
		}
	}
	private List<Map> queryMonthLogs(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.id,a.planId,a.summaryDate,a.summaryEvent,a.dayType,a.custName");
		sql.append(" from DAILY_CalendarLog as a where a.employeeId="+employeeId);
		//sql.append(" left join DAILY_CalendarPlan as b on b.id=a.planId ");
		sql.append(" and a.summaryDate between '"+startDate+"' and '"+endDate+"' ");
		sql.append(" order by a.summaryDate,a.dayType ");
		List<Map> list=service.getDb().queryForList(sql.toString(), requestContext);
		return list;
		 
	} 
	private void queryMonthSummary(RequestContext requestContext , IBaseServiceManger service,
			Integer employeeId,String startDate,String endDate, CalendarView view){
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from DAILY_CalendarSummary as a where a.employeeId="+employeeId); 
		sql.append(" and a.startDate='"+startDate+"' and a.endDate='"+endDate+"' "); 
		sql.append(" and a.eventType='summary' ");
		sql.append(" and a.dateRangeType='month' ");
		List<Map> list=service.getDb().queryForList(sql.toString(), requestContext);  
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				view.setSummary(list.get(i)); 
			}  
		} else {
			Map map=new HashMap();
			map.put("startDate", startDate);
			map.put("description", "");
			map.put("eventExplain", "");
			map.put("id", "0");
			view.setSummary(map); 
		}
	}
}
