package com.ts.main.sys.sendsms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 

import com.ts.core.common.bean.BaseBean;
import com.ts.main.crm.cust.Customer;
import com.ts.main.hr.employee.Employee;

/**
 * 短信接收者
 * @author tgp
 *
 */
public class SendSmsReceiver extends BaseBean {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column 
    private Integer id; //表主键id
	private Integer custId;//客户id
	private Integer employeeId;//员工id
	private Integer contactId;//联系人id
	private String receiveStatus;//接收状态，0-不成功，1-成功,2-待发送
	private String responseCode;//发送状态码
	private String replyStatus;//回复状态
	private String mobileNo;//手机号码
	private String sendTime;//发送时间
	private String receiverType;//接收者类型,0-客户，1-本公司员工,2-其它(直接录入手机号码的）
	private String receiver;//接收者姓名
	private String birthday;//接收者生日
	private String custName;//公司名称
	private String saleMan;//负责业务员姓名
	private String saleMobile;//业务员联系方式
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}

	public String getSaleMobile() {
		return saleMobile;
	}

	public void setSaleMobile(String saleMobile) {
		this.saleMobile = saleMobile;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	private SendSmsContent sendSmsContent;
	
	//temp variant
	private Customer customer;
	private Employee employee; 
	
	
	
	
	 public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	 

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public SendSmsContent getSendSmsContent() {
		return sendSmsContent;
	}

	public void setSendSmsContent(SendSmsContent sendSmsContent) {
		this.sendSmsContent = sendSmsContent;
	}

	 

	public boolean equals(Object obj){
		if (obj instanceof SendSmsReceiver){
			SendSmsReceiver object = (SendSmsReceiver) obj;
			return object.getId()==this.id;
		}
		return false;
	}
}
