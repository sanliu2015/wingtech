package com.ts.main.dorm.room;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class RoomForm extends BaseInfoForm {
	
	private Room bean = new Room(); 
	private List<RoomThings> dtlList = new ArrayList<RoomThings>();
	
	public Room getBean() {
		return bean;
	}
	public void setBean(Room bean) {
		this.bean = bean;
	}
	public List<RoomThings> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<RoomThings> dtlList) {
		this.dtlList = dtlList;
	}

}
