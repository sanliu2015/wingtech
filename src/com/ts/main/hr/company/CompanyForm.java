package com.ts.main.hr.company;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class CompanyForm extends BaseInfoForm implements IBaseModel{
	private Company bean=new Company();

	public Company getBean() {
		return bean;
	}

	public void setBean(Company bean) {
		this.bean = bean;
	}

	 
	
}
