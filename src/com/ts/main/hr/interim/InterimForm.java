package com.ts.main.hr.interim;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class InterimForm extends BaseInfoForm implements IBaseModel{
  private Interim bean=new Interim();

public Interim getBean() {
	return bean;
}

public void setBean(Interim bean) {
	this.bean = bean;
}
  
  
}
