package com.ts.main.dorm.things;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class ThingsForm extends BaseInfoForm {

	private Things bean = new Things();
	private List<Things> dtlList = new ArrayList<Things>();

	public Things getBean() {
		return bean;
	}

	public void setBean(Things bean) {
		this.bean = bean;
	}

	public List<Things> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<Things> dtlList) {
		this.dtlList = dtlList;
	}
}
