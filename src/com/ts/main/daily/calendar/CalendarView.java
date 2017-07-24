package com.ts.main.daily.calendar;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ts.core.common.bean.BaseBean;

public class CalendarView implements Serializable  {
	private String startDate;
	private String endDate;
	private String currentDate;
	private List<BaseBean> weekTitleList;
	private List planAmList;
	private List planPmList;
	private List logAmList;
	private List logPmList;
	private List logList;
	private Map summary;
	private Map weekPlan;
	
	
	public List getLogList() {
		return logList;
	}
	public void setLogList(List logList) {
		this.logList = logList;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Map getWeekPlan() {
		return weekPlan;
	}
	public void setWeekPlan(Map weekPlan) {
		this.weekPlan = weekPlan;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public List<BaseBean> getWeekTitleList() {
		return weekTitleList;
	}
	public void setWeekTitleList(List<BaseBean> weekTitleList) {
		this.weekTitleList = weekTitleList;
	}
	public List getPlanAmList() {
		return planAmList;
	}
	public void setPlanAmList(List planAmList) {
		this.planAmList = planAmList;
	}
	public List getPlanPmList() {
		return planPmList;
	}
	public void setPlanPmList(List planPmList) {
		this.planPmList = planPmList;
	}
	public List getLogAmList() {
		return logAmList;
	}
	public void setLogAmList(List logAmList) {
		this.logAmList = logAmList;
	}
	public List getLogPmList() {
		return logPmList;
	}
	public void setLogPmList(List logPmList) {
		this.logPmList = logPmList;
	}
	public Map getSummary() {
		return summary;
	}
	public void setSummary(Map summary) {
		this.summary = summary;
	}
	 
	 
	
}
