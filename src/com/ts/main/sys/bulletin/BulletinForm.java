package com.ts.main.sys.bulletin;

import java.util.List;
import java.util.Map;

import com.ts.core.common.form.AutoArrayList;
import com.ts.core.common.form.BaseInfoForm;

public class BulletinForm extends BaseInfoForm  {
	
	private String topicKind; 
	private Integer pageNo;
	private Integer pageSize;
	private Integer records;
	
	private Bulletin bean=new Bulletin();
	private List<BulletinReceiver> employeeReceiverList = new AutoArrayList<BulletinReceiver>(BulletinReceiver.class);
	private List<BulletinReceiver> custReceiverList = new AutoArrayList<BulletinReceiver>(BulletinReceiver.class);
	
	private List<BulletinFile> bulletinFileList = new AutoArrayList<BulletinFile>(BulletinFile.class);
	private List<BulletinViewer> viewerList;
	public List<Map> bulletinList;
	
	
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<BulletinViewer> getViewerList() {
		return viewerList;
	}

	public void setViewerList(List<BulletinViewer> viewerList) {
		this.viewerList = viewerList;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public String getTopicKind() {
		return topicKind;
	}

	public void setTopicKind(String topicKind) {
		this.topicKind = topicKind;
	}

	public List<Map> getBulletinList() {
		return bulletinList;
	}

	public void setBulletinList(List<Map> bulletinList) {
		this.bulletinList = bulletinList;
	}

	public List<BulletinReceiver> getEmployeeReceiverList() {
		return employeeReceiverList;
	}

	public void setEmployeeReceiverList(List<BulletinReceiver> employeeReceiverList) {
		this.employeeReceiverList = employeeReceiverList;
	}

	public List<BulletinReceiver> getCustReceiverList() {
		return custReceiverList;
	}

	public void setCustReceiverList(List<BulletinReceiver> custReceiverList) {
		this.custReceiverList = custReceiverList;
	}

	public Bulletin getBean() {
		return bean;
	}

	public void setBean(Bulletin bean) {
		this.bean = bean;
	}

	public List<BulletinFile> getBulletinFileList() {
		return bulletinFileList;
	}

	public void setBulletinFileList(List<BulletinFile> bulletinFileList) {
		this.bulletinFileList = bulletinFileList;
	}

	 
	
	 
	
}
