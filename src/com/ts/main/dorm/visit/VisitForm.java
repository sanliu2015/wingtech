package com.ts.main.dorm.visit;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class VisitForm extends BaseInfoForm {

	private Visit bean = new Visit();
	private List<VisitDtl> dtlList = new ArrayList<VisitDtl>();
	private List<VisitLeave> leaveList = new ArrayList<VisitLeave>();
	
	public Visit getBean() {
		return bean;
	}
	public void setBean(Visit bean) {
		this.bean = bean;
	}
	public List<VisitDtl> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<VisitDtl> dtlList) {
		this.dtlList = dtlList;
	}
	public List<VisitLeave> getLeaveList() {
		return leaveList;
	}
	public void setLeaveList(List<VisitLeave> leaveList) {
		this.leaveList = leaveList;
	}
	
	
}
