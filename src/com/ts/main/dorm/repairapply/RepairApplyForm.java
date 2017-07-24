package com.ts.main.dorm.repairapply;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.main.dorm.damage.Damage;

public class RepairApplyForm extends BaseInfoForm {

	private RepairApply bean = new RepairApply();
	private List<RepairApplyDtl> dtlList = new ArrayList<RepairApplyDtl>();
	private List<Damage> damageList = new ArrayList<Damage>();
	
	public RepairApply getBean() {
		return bean;
	}
	public void setBean(RepairApply bean) {
		this.bean = bean;
	}
	public List<RepairApplyDtl> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<RepairApplyDtl> dtlList) {
		this.dtlList = dtlList;
	}
	public List<Damage> getDamageList() {
		return damageList;
	}
	public void setDamageList(List<Damage> damageList) {
		this.damageList = damageList;
	}
	
}
