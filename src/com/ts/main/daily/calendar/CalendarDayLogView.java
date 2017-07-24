package com.ts.main.daily.calendar;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ts.core.common.bean.BaseBean;

public class CalendarDayLogView implements Serializable  {
	private Integer day;
	private String currentDate; 
	private List logList;
	
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public List getLogList() {
		return logList;
	}
	public void setLogList(List logList) {
		this.logList = logList;
	}
	 
	 
	
}
