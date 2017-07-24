package com.ts.main.hr.employee;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class EmployeeForm extends BaseInfoForm implements IBaseModel{
	private Employee bean=new Employee(); 
	public Employee getBean() {
		return bean;
	} 
	public void setBean(Employee bean) {
		this.bean = bean;
	} 
}
