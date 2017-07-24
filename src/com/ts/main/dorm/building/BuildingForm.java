package com.ts.main.dorm.building;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class BuildingForm extends BaseInfoForm {

	private Building bean = new Building();

	public Building getBean() {
		return bean;
	}

	public void setBean(Building bean) {
		this.bean = bean;
	}
	
	
}
