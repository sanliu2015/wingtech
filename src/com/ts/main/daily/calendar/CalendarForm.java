package com.ts.main.daily.calendar;

import java.util.List;
import java.util.Map;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class CalendarForm extends BaseInfoForm  {
	
	private Integer weekIndex;//第几周
	private Integer employeeId; 
	private String weekOperateType;//prev、next 、current
	private String calendarDate;//当前日期
	private Integer planId;
	private CalendarPlan plan=new CalendarPlan();
	private CalendarSummary summary=new CalendarSummary();
	private CalendarLog log=new CalendarLog();
	private List<Map> planList;
	private Integer pageNo;
	private Integer pageSize;
	
	
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public List<Map> getPlanList() {
		return planList;
	}
	public void setPlanList(List<Map> planList) {
		this.planList = planList;
	}
	public CalendarLog getLog() {
		return log;
	}
	public void setLog(CalendarLog log) {
		this.log = log;
	}
	public String getWeekOperateType() {
		return weekOperateType;
	}
	public void setWeekOperateType(String weekOperateType) {
		this.weekOperateType = weekOperateType;
	}
	 
	 
	public String getCalendarDate() {
		return calendarDate;
	}
	public void setCalendarDate(String calendarDate) {
		this.calendarDate = calendarDate;
	}
	public CalendarPlan getPlan() {
		return plan;
	}
	public void setPlan(CalendarPlan plan) {
		this.plan = plan;
	}
	public CalendarSummary getSummary() {
		return summary;
	}
	public void setSummary(CalendarSummary summary) {
		this.summary = summary;
	}
	public Integer getWeekIndex() {
		return weekIndex;
	}
	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	 
    
	
}
