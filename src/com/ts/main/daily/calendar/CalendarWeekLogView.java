package com.ts.main.daily.calendar;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ts.core.common.bean.BaseBean;

public class CalendarWeekLogView implements Serializable  {
	private String name;
	private Integer week;
	private String startDate;
	private String endDate;
	private List logList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
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
	public List getLogList() {
		return logList;
	}
	public void setLogList(List logList) {
		this.logList = logList;
	}
	
	 
	 
	
}
