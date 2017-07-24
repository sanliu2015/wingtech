package com.ts.main.hr.position;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class PositionForm extends BaseInfoForm implements IBaseModel{
	private Position bean=new Position();

	public Position getBean() {
		return bean;
	}

	public void setBean(Position bean) {
		this.bean = bean;
	}

	 

	 
	
}
