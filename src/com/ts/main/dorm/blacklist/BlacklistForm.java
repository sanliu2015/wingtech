package com.ts.main.dorm.blacklist;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class BlacklistForm extends BaseInfoForm {

	private Blacklist bean = new Blacklist();
	private List<Blacklist> dtlList = new ArrayList<Blacklist>();

	
	public Blacklist getBean() {
		return bean;
	}

	public void setBean(Blacklist bean) {
		this.bean = bean;
	}

	public List<Blacklist> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<Blacklist> dtlList) {
		this.dtlList = dtlList;
	}
	
}
