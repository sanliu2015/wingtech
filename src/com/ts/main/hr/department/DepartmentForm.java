package com.ts.main.hr.department;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class DepartmentForm extends BaseInfoForm implements IBaseModel{
	private Department bean=new Department();

	public Department getBean() {
		return bean;
	}

	public void setBean(Department bean) {
		this.bean = bean;
	}

	 

	 
	
}
