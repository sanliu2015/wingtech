package com.ts.main.dorm.passbill;


import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class PassBillForm extends BaseInfoForm {

	private PassBill bean = new PassBill();
	private List<PassBillDtl> dtlList = new ArrayList<PassBillDtl>();
	
	public PassBill getBean() {
		return bean;
	}
	public void setBean(PassBill bean) {
		this.bean = bean;
	}
	public List<PassBillDtl> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<PassBillDtl> dtlList) {
		this.dtlList = dtlList;
	}
	
	
}
