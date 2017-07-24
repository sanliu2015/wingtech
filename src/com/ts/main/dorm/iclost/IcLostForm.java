package com.ts.main.dorm.iclost;

import java.util.ArrayList;
import java.util.List;

public class IcLostForm {

	private IcLost bean = new IcLost();
	private List<IcLost> dtlList = new ArrayList<IcLost>();
	
	
	public IcLost getBean() {
		return bean;
	}
	public void setBean(IcLost bean) {
		this.bean = bean;
	}
	public List<IcLost> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<IcLost> dtlList) {
		this.dtlList = dtlList;
	}
	
	
}
