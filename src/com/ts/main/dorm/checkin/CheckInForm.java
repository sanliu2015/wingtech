package com.ts.main.dorm.checkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ts.core.common.form.AutoArrayList;
import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;
import com.ts.main.dorm.damage.Damage;

public class CheckInForm extends BaseInfoForm {

	private CheckIn bean = new CheckIn();
	private Damage damage = new Damage();
	private List<Damage> dtlList = new ArrayList<Damage>();
	private String ids;

	public CheckIn getBean() {
		return bean;
	}

	public void setBean(CheckIn bean) {
		this.bean = bean;
	}

	public Damage getDamage() {
		return damage;
	}

	public void setDamage(Damage damage) {
		this.damage = damage;
	}

	public List<Damage> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<Damage> dtlList) {
		this.dtlList = dtlList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
