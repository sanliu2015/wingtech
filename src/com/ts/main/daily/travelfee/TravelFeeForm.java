package com.ts.main.daily.travelfee;

import java.util.List;

import com.ts.core.common.form.AutoArrayList;
import com.ts.core.common.form.BaseInfoForm;  

public class TravelFeeForm extends BaseInfoForm  {
	private TravelFee bean=new TravelFee();
	private List<TravelFeeDtl> dtlList = new AutoArrayList<TravelFeeDtl>(TravelFeeDtl.class);
	private List<TravelFeeFile> fileList = new AutoArrayList<TravelFeeFile>(TravelFeeFile.class);
	public TravelFee getBean() {
		return bean;
	}
	public void setBean(TravelFee bean) {
		this.bean = bean;
	}
	public List<TravelFeeDtl> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<TravelFeeDtl> dtlList) {
		this.dtlList = dtlList;
	}
	public List<TravelFeeFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<TravelFeeFile> fileList) {
		this.fileList = fileList;
	}
	
	
	  
	
}
