package com.ts.main.dorm.passbill;


import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.main.dorm.damage.Damage;

public class PassBillForm extends BaseInfoForm {

	private Damage damage = new Damage();
	private PassBill bean = new PassBill();
	private List<PassBillDtl> dtlList = new ArrayList<PassBillDtl>();
	
	
	public Damage getDamage() {
		return damage;
	}
	public void setDamage(Damage damage) {
		this.damage = damage;
	}
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
