package com.ts.main.sys.bulletintopic;

import com.ts.core.common.form.BaseInfoForm;
import com.ts.core.common.form.IBaseModel;

public class BulletinTopicForm extends BaseInfoForm implements IBaseModel{
	private BulletinTopic bean=new BulletinTopic();

	public BulletinTopic getBean() {
		return bean;
	}

	public void setBean(BulletinTopic bean) {
		this.bean = bean;
	}

	 

	 
	
}
