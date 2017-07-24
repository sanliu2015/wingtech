package com.ts.main.sys.subscribe;

import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class SubscribeForm extends BaseInfoForm  {
	
	private String topicKind;  
	
	private SubscribeReceiver bean=new SubscribeReceiver();
	private List<SubscribeReceiver> employeeReceiverList;
	
	
	public SubscribeReceiver getBean() {
		return bean;
	}
	public void setBean(SubscribeReceiver bean) {
		this.bean = bean;
	}
	public String getTopicKind() {
		return topicKind;
	}
	public void setTopicKind(String topicKind) {
		this.topicKind = topicKind;
	}
	 
	public List<SubscribeReceiver> getEmployeeReceiverList() {
		return employeeReceiverList;
	}
	public void setEmployeeReceiverList(List<SubscribeReceiver> employeeReceiverList) {
		this.employeeReceiverList = employeeReceiverList;
	}
	 
	
	 
	
	 
	
}
