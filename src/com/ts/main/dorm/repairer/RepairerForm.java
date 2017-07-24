package com.ts.main.dorm.repairer;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class RepairerForm extends BaseInfoForm {

	private Repairer bean = new Repairer();
	private List<Repairer> dtlList = new ArrayList<Repairer>();

	
	public Repairer getBean() {
		return bean;
	}

	public void setBean(Repairer bean) {
		this.bean = bean;
	}

	public List<Repairer> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<Repairer> dtlList) {
		this.dtlList = dtlList;
	}
	
	
}
