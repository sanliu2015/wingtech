package com.ts.main.sys.schedulejob;

import com.ts.core.common.form.BaseInfoForm;

public class ScheduleJobForm extends BaseInfoForm { 

	private ScheduleJob bean = new ScheduleJob();

	public ScheduleJob getBean() {
		return bean;
	}

	public void setBean(ScheduleJob bean) {
		this.bean = bean;
	}
	
}
