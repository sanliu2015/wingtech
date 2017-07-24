package com.ts.main.daily.meeting;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class MeetingForm extends BaseInfoForm {

	private MeetingReserve bean;
	private List<MeetingParticipant> dtlList;
	
	public MeetingReserve getBean() {
		return bean;
	}
	public void setBean(MeetingReserve bean) {
		this.bean = bean;
	}
	public List<MeetingParticipant> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<MeetingParticipant> dtlList) {
		this.dtlList = dtlList;
	}
	
}
