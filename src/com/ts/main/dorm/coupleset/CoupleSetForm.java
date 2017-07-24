package com.ts.main.dorm.coupleset;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class CoupleSetForm extends BaseInfoForm {

	private CoupleSet bean = new CoupleSet();
	private List<CoupleSet> dtlList = new ArrayList<CoupleSet>();

	public CoupleSet getBean() {
		return bean;
	}

	public void setBean(CoupleSet bean) {
		this.bean = bean;
	}

	public List<CoupleSet> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<CoupleSet> dtlList) {
		this.dtlList = dtlList;
	}
	
}
